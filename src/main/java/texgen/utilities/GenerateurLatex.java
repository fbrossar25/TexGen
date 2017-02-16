package texgen.utilities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import texgen.modele.Lien;
import texgen.modele.Noeud;
import texgen.modele.Noeud.TypeForme;
import texgen.vue.FenetrePrincipale;
import texgen.vue.Graph;
import texgen.vue.Graph.EtatParcours;
import texgen.vue.PseudoCode;
import texgen.vue.Tableau;

/**
 * Classe gérant la génération d'un code LaTeX
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class GenerateurLatex {
    /** la liste des caractères à protéger dans le LaTeX */
    public static char[] protectedChar = { '&', '\\', '{', '}' };

    /**
     * Fonction générant le code LaTeX
     * 
     * @param fenetrePrincipale
     *            fenêtre principale de l'aplication
     * @return code généré
     */
    public static String generer(FenetrePrincipale fenetrePrincipale) {
        String string = genererEntete() + "\n\n";
        string += genererCouleurs(fenetrePrincipale.getGraph()) + "\n\n";
        string += genererTikz(fenetrePrincipale.getGraph().isArrow() ? "->" : "-") + "\n\n";
        string += genererBeamer() + "\n\n";
        string += genererPiedDePage() + "\n\n";
        string += genererInfosAuteur() + "\n\n";
        string += genererMacroColorCode() + "\n\n";
        string += "\\begin{document}\n";
        string += "\\begin{frame}\n";
        string += genererPseudoCode(fenetrePrincipale.getPseudoCode()) + "\n";
        string += genererTableau(fenetrePrincipale.getTableau());
        string += genererGraph(fenetrePrincipale.getGraph());
        string += "\\end{frame}\n";
        string += "\\end{document}\n";
        return string;
    }

    public static String genererCouleurs(Graph graph) {
        String res = "";
        for (EtatParcours etat : EtatParcours.values()) {
            Color c = graph.getColorForEtat(etat);
            int r = c.getRed();
            int g = c.getGreen();
            int b = c.getBlue();
            res += "\\definecolor{" + etat.toString() + "}{RGB}{" + r + "," + g + "," + b + "}\n";
        }
        return res;
    }

    public static String genererInfosAuteur() {
        return "%about author not implemented yet";
    }

    public static String genererPiedDePage() {
        return "%footnote not implemented yet";
    }

    public static String genererTikz(String tikzLinkStyle) {
        String res = "\\tikzset{\n";
        res += "\tlien/.style={" + tikzLinkStyle + ",thick,color=#1},\n";
        res += "\tlien/.default={black!21},\n";
        res += "\tetat/.style={draw,thick,circle,color=#1},\n";
        res += "\tetat/.default={black!21},\n";
        res += "\tetatFinal/.style={draw,double,circle,color=#1},\n";
        res += "\tetatFinal/.default={blue!21}\n";
        return res + "}";
    }

    /**
     * Generer l'initialisation de beamer
     * 
     * @return le code généré
     */
    public static String genererBeamer() {
        return "\\setbeamertemplate{navigation symbols}{}";
    }

    /**
     * Fonction générant la partie du code correspondant au graph
     * 
     * @param g
     *            le graph
     * @return code généré
     */
    public static String genererGraph(Graph g) {
        String res = "\\begin{center}\n";
        res += "\\begin{tikzpicture}[remember picture]\n";
        res += "\\begin{scope}[yscale=-1]\n\n";
        res += genererNoeudGraph(g);
        res += genererLienGraph(g);
        res += "\\end{scope}\n";
        res += "\\end{tikzpicture}\n";
        res += "\\end{center}\n";
        return res;
    }

    /**
     * Normalise un label (de lien ou de noeud) pour être utilisé avec le graphe au format LaTeX (retire certains caractères)
     * 
     * @param label
     *            le label
     * @return le label normalisée (sans caractère à protégé)
     * @see GenerateurLatex#isProtectedChar(char)
     */
    public static String normalizeLabelForGraph(String label) {
        String res = "";
        for (char c : label.toCharArray()) {
            res += (isProtectedChar(c)) ? "" : c;
        }
        return res;
    }

    /**
     * Génère la liste des diapos pour un couple de noeud et d'état
     * 
     * @param g
     *            le graph
     * @param n
     *            le noeud
     * @param etat
     *            l'état
     * @return la liste des diapos (ex : "<1,2,4>")
     */
    public static String getDiapoNoeudString(Graph g, Noeud n, EtatParcours etat) {
        ArrayList<EtatParcours> list = g.getEtatsNoeud(n);
        int size = list.size();
        ArrayList<Integer> diapos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (list.get(i) == etat) {
                diapos.add(i + 1);
            }
        }
        return "<" + convertIntArrayToRangesSet(diapos) + ">";
    }

    /**
     * Retourne la chaîne correspondant aux coordonnées du noeud n pour le graphe au format LaTeX
     * 
     * @param g
     *            le graph
     * @param n
     *            le noeud
     * @return les coordonnées (ex : "(0,3)")
     */
    public static String getCoordonneeString(Graph g, Noeud n) {
        double unit = 100.0;
        int x = n.getCentre().x - (g.getWidth() / 2);
        int y = n.getCentre().y;
        return "(" + (x / unit) + "," + (y / unit) + ")";
    }

    /**
     * Génère tout les noeuds du graph au format LaTeX
     * 
     * @param g
     *            le graph
     * @return la chaîne contenant tout les noeuds du graph
     */
    public static String genererNoeudGraph(Graph g) {
        String res = "";
        for (Noeud n : g.getNoeuds()) {
            String normalizedLabel = normalizeLabelForGraph(n.getText());
            String typeNoeud = (n.getForme() == TypeForme.Simple) ? "etat" : "etatFinal";
            String coordonnees = getCoordonneeString(g, n);
            for (EtatParcours etat : g.getEtatsNoeudDistinct(n)) {
                String listeDiapos = getDiapoNoeudString(g, n, etat);
                String couleur = etat.toString();
                res += "\t\\node" + listeDiapos + "[" + typeNoeud + ((couleur.equals("")) ? "" : "=") + couleur + "] (" + normalizedLabel + ") at " + coordonnees + "{\\scriptsize " + normalizedLabel
                        + "};\n";
            }
        }
        return res;

    }

    /**
     * Génère la liste des diapos pour un couple de lien et d'état
     * 
     * @param g
     *            le graph
     * @param l
     *            le lien
     * @param etat
     *            l'état
     * @return la liste des diapos (ex : "<1,2,4>")
     */
    public static String getDiapoLienString(Graph g, Lien l, EtatParcours etat) {
        ArrayList<EtatParcours> list = g.getEtatsLien(l);
        int size = list.size();
        ArrayList<Integer> diapos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (list.get(i) == etat) {
                diapos.add(i + 1);
            }
        }
        return "<" + convertIntArrayToRangesSet(diapos) + ">";
    }

    /**
     * Génèrer tout les liens du graphe au format LaTeX
     * 
     * @param g
     *            le graph
     * @return la chaîne contenant tout les liens
     */
    public static String genererLienGraph(Graph g) {
        String res = "";
        for (Lien l : g.getLiens()) {
            String depart = "(" + normalizeLabelForGraph(l.getDepart().getText()) + ")";
            String arrive = "(" + normalizeLabelForGraph(l.getArrive().getText()) + ")";
            for (EtatParcours etat : g.getEtatsLienDistinct(l)) {
                String couleur = etat.toString();
                res += "\t\\draw" + getDiapoLienString(g, l, etat) + "[lien" + ((couleur.equals("")) ? "" : "=") + couleur + "] " + depart + " -> " + arrive + ";\n";
            }
        }
        return res;
    }

    /**
     * Fonction générant l'entête du code
     * 
     * @return entête du code
     */
    public static String genererEntete() {
        String res = "\\documentclass[hyperref={colorlinks=true}]{beamer}\n";
        res += "\\usepackage{kpfonts}\n";
        res += "\\usepackage[utf8]{inputenc}\n";
        res += "\\usepackage[T1]{fontenc}\n";
        res += "\\usepackage{pdfpages}\n";
        res += "\\usetheme{Boadilla}\n";
        res += "\\usepackage{tikz}\n";
        res += "\\usepackage[frenchb]{babel}\n";
        res += "\\usepackage[ruled,vlined,linesnumberedhidden,french,slide]{algorithm2e}\n\n";
        res += "\\newcounter{MyAlgoStep}\n";
        return res;
    }

    /**
     * Fonction permettant de séparer les lignes du pseudo code
     * 
     * @param txt
     *            Pseudo code
     * @return liste des lignes
     */
    private static ArrayList<String> separerLignes(String txt) {
        if ((txt == null) || (txt.length() <= 0)) {
            return null;
        } else {
            ArrayList<String> list = new ArrayList<>();
            int i = 0;
            String currentLine = "";
            char c;
            while (i < txt.length()) {
                c = txt.charAt(i);
                currentLine += c;
                if ((i == (txt.length() - 1)) || (c == '\n')) {
                    // Fin du pseudoCode ou ligne suivante
                    list.add(currentLine);
                    currentLine = "";
                }
                i++;
            }
            return list;
        }
    }

    /**
     * Fonction qui renvoie true si le caractère c doit être protégé en LaTeX, false sinon
     * 
     * @param c
     *            le caractère
     * @return true si le caractère doit être protégé, false sinon
     */
    public static boolean isProtectedChar(char c) {
        for (char o : protectedChar) {
            if (o == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * Fonction générant la ligne utilisant la macro colorCode correspondant à la ligne du pseudoCode
     * 
     * @param p
     *            Pseudo code
     * @param ligne
     *            Ligne du pseudo code
     * @param ligneNum
     *            Numéro de ligne
     * @return la ligne du pseudoCode avec le colorCode correspondant
     */
    public static String genererColorCode(PseudoCode p, String ligne, int ligneNum) {
        String res = "\\colorCode";
        ArrayList<Integer> marked = new ArrayList<>();
        ArrayList<Integer> unmarked = new ArrayList<>();
        int nbDiapo = p.getNombreDiapos();
        for (int diapo = 1; diapo <= nbDiapo; diapo++) {
            if (p.estMarquee(diapo, ligneNum)) {
                marked.add(diapo);
            } else {
                unmarked.add(diapo);
            }
        }

        // Chaine représentant les numéros des diapos marquées
        String markedString = "{" + convertIntArrayToRangesSet(marked) + "}";

        // Chaine représentant les numéros des diapos non marquées
        String unmarkedString = "{" + convertIntArrayToRangesSet(unmarked) + "}";

        // Chaine de la ligne
        // On protège les caractères nécessaires et on supprime tout les caractères séparateurs en fin de chaînes
        // On replace également les '\' par des '/' pour éviter des erreurs de compilation LaTeX
        // Un remplacement possible serait '\' -> "$\backslahs$"
        // Mais en JAVA l'utilisation des caracteres $ et \ nécessite l'utilisation de Matcher.quoteReplacement()
        // Ce qui donne, une fois générée, une chaîne de remplacement type $\\backslash$
        // ce qui provoque aussi une erreur de compilation LaTeX
        String protectedLine = protegerCaracteres(ligne).replaceFirst("\\s++$", "");
        String ligneString = "{" + protectedLine + "\\newline" + "}";

        return res + unmarkedString + markedString + ligneString;
    }

    /**
     * Fonction générant la partie du code correspondant au pseudo code
     * 
     * @param pseudoCode
     *            Pseudo code
     * @return code généré
     */
    public static String genererPseudoCode(PseudoCode pseudoCode) {
        String res = "";

        res += "\\begin{minipage}[t]{0.48\\textwidth}\n" + "\\begin{tiny}\n" + "\\begin{algorithm*}[H]\n" + "\\NoCaptionOfAlgo\n\n";

        ArrayList<String> lignes = separerLignes(pseudoCode.getTextArea().getText());
        for (int i = 1; i <= lignes.size(); i++) {
            res += genererColorCode(pseudoCode, lignes.get(i - 1), i);
        }

        res += "\n" + "\\end{algorithm*}\n" + "\\end{tiny}\n" + "\\end{minipage}\n";
        return res.replaceAll("ﬁ", "fi");
    }

    /**
     * Fonction générant la partie du code correspondant au tableau
     * 
     * @param tableau
     *            le tableau
     * @return le code LaTeX du tableau
     */
    public static String genererTableau(Tableau tableau) {
        String res = "";
        res += "\\begin{minipage}[t]{0.48\\textwidth}\n" + "\\begin{scriptsize}\n";
        res += genererEnteteTableau(tableau) + "\n";
        res += genererCorpsTableau(tableau) + "\\hline\n";
        res += "\\end{tabular}\\\\\n" + "\\end{scriptsize}\n" + "\\end{minipage}\n";
        return res;
    }

    /**
     * Fonction générant l'entete dela partie du code LaTeX du tableau
     * 
     * @param tableau
     *            le tableau
     * @return le code LaTeX de l'entete du tableau
     */
    public static String genererEnteteTableau(Tableau tableau) {
        String res = "\\begin{tabular}{";
        for (int i = 0; i < tableau.getNombreColonnes(); i++) {
            res += "|c";
        }
        res += "|}\n";
        res += "\\hline ";
        res += tableau.getNomsColonnes().get(0);
        for (int i = 1; i < tableau.getNombreColonnes(); i++) {
            res += " & " + tableau.getNomsColonnes().get(i);
        }
        res += "\\\\ \\hline";
        return res;
    }

    /**
     * Fonction générant le code LaTeX correspondant au corps du tableau
     * 
     * @param tableau
     *            le tableau
     * @return le code du corps du tableau
     */
    public static String genererCorpsTableau(Tableau tableau) {
        String res = "";
        for (int i = 1; i < tableau.getNombreLignes(); i++) {
            res += genererLigneTableau(tableau, i);
        }
        return res;
    }

    /**
     * Fonction permettant de générer le code LaTeX correspondant à la ligne donnée du tableau
     * 
     * @param tableau
     *            le tableau
     * @param ligne
     *            la ligne
     * @return le code LaTeX de la ligne
     */
    public static String genererLigneTableau(Tableau tableau, int ligne) {
        String res = "%Ligne " + ligne + "\n";
        for (int i = 0; i < tableau.getNombreColonnes(); i++) {
            res += genererCaseTableau(tableau, ligne, i) + ((i == tableau.getNombreColonnes() - 1) ? "\\\\\n\n" : "\t&\n");
        }
        return res;
    }

    /**
     * Fonction permettant de générer le code LaTeX de la case du tableau aux coordonnées données
     * 
     * @param tableau
     *            le tableau
     * @param ligne
     *            la ligne de la case
     * @param colonne
     *            la colonne de la case
     * @return le code LaTeX correspondant à la case
     */
    public static String genererCaseTableau(Tableau tableau, int ligne, int colonne) {
        String res = "";
        // HashMap avec pour clé la valeur de la cellule (son texte)
        // et pour valeur la liste sans doublons des diapos de cette valeur
        HashMap<String, TreeSet<Integer>> cell = new HashMap<>();
        ArrayList<String> values = tableau.getCellValues(ligne, colonne);
        String value;
        for (int i = 1; i <= values.size(); i++) {
            value = values.get(i - 1);
            if (cell.containsKey(value)) {
                cell.get(value).add(i);
            } else {
                TreeSet<Integer> newSet = new TreeSet<>();
                newSet.add(i);
                cell.put(value, newSet);
            }
        }

        for (String val : cell.keySet()) {
            res += "\\only<";
            ArrayList<Integer> diapos = new ArrayList<>();
            diapos.addAll(cell.get(val));
            res += convertIntArrayToRangesSet(diapos);
            res += ">{" + ((val == null || val.equals("")) ? "" : genererColorCodeTableau(tableau, ligne, colonne, val)) + "}";
        }
        return res;
    }

    /**
     * Transforme une liste d'entier en une chaine de représentant les plages d'entiers correspondantes
     * 
     * @param list
     *            la liste des entiers
     * @return la chaînes des plages d'entiers (ex : {1,3,4,5,7} -> "1,3-5,7")
     */
    public static String convertIntArrayToRangesSet(ArrayList<Integer> list) {
        int size = list.size();
        if (size < 1) {
            return "";
        } else if (size == 1) {
            return "" + list.get(0);
        }
        StringBuilder sb = new StringBuilder();
        int rangeStart = list.get(0);
        int previous = rangeStart;
        int current, expected;
        for (int i = 1; i < size; i++) {
            current = list.get(i);
            expected = previous + 1;
            if (current != expected) {
                addRange(sb, rangeStart, previous);
                rangeStart = current;
            }
            previous = current;
        }
        addRange(sb, rangeStart, previous);
        return sb.toString();
    }

    private static void addRange(StringBuilder sb, int from, int to) {
        if (sb.length() > 0) {
            sb.append(",");
        }
        if (from == to) {
            sb.append(from);
        } else {
            sb.append(from + "-" + to);
        }
    }

    public static String genererColorCodeTableau(Tableau tableau, int ligne, int colonne, String s) {
        String res = "\n\\colorCode";
        ArrayList<Integer> marked = new ArrayList<>();
        ArrayList<Integer> unmarked = new ArrayList<>();
        int nbDiapo = tableau.getNombreDiapos();
        for (int diapo = 1; diapo <= nbDiapo; diapo++) {
            if (tableau.estMarquee(diapo, ligne, colonne)) {
                marked.add(diapo);
            } else {
                unmarked.add(diapo);
            }
        }

        // Chaine représentant les numéros des diapos marquées
        String markedString = "{" + convertIntArrayToRangesSet(marked) + "}";

        // Chaine représentant les numéros des diapos non marquées
        String unmarkedString = "{" + convertIntArrayToRangesSet(unmarked) + "}";

        // Chaine de la ligne
        String ligneString = "{" + protegerCaracteresTableau(s) + "}";

        return res + unmarkedString + markedString + ligneString;
    }

    /**
     * Protège les caractères si besoin pour le tableau
     * 
     * @param s
     *            la chaîne à traiter
     * @return la chaîne traitée
     */
    public static String protegerCaracteresTableau(String s) {
        String res = "";
        for (char c : s.toCharArray()) {
            if (isProtectedChar(c) && c != '\\' || c == '&') {
                res += "\\" + c;
            } else {
                res += c;
            }
        }
        return res;
    }

    /**
     * Fonction servant à protéger les caractères de la chaine s en utilisant isProtectedChar(c)
     * 
     * @param s
     *            la chaine à protéger
     * @return la chaine avec tout ses caracteres protéger si nécessaire
     */
    public static String protegerCaracteres(String s) {
        String res = "";
        // La protection du caractere \ donne des erreur à la compilation du fichier .tex
        for (char c : s.toCharArray()) {
            res += (isProtectedChar(c)) ? "\\" + c : c;
        }
        return res;
    }

    /**
     * Fonction générant la macro colorCode
     * 
     * @return la macro colorCode
     */
    public static String genererMacroColorCode() {
        return "\\newcommand{\\colorCode}[3]{\\only<#1>{\\textcolor{black}{#3}}\\only<#2>{\\textcolor{red}{#3}}}";
    }
}
