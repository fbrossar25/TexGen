package texgen.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import texgen.vue.FenetrePrincipale;
import texgen.vue.PseudoCode;
import texgen.vue.Tableau;

/**
 * Classe gérant la génération d'un code LaTeX
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class GenerateurLatex {

    /**
     * Fonction générant le code LaTeX
     * 
     * @param fenetrePrincipale
     *            fenêtre principale de l'aplication
     * @return code généré
     */
    public static String generer(FenetrePrincipale fenetrePrincipale) {
        String string = genererEntete() + "\n\n";
        string += genererColorCode() + "\n\n";
        string += "\\begin{document}\n";
        string += "\\begin{frame}\n";
        string += genererPseudoCode(fenetrePrincipale.getPseudoCode()) + "\n";
        string += genererTableau(fenetrePrincipale.getTableau());
        string += "\\end{frame}\n";
        string += "\\end{document}\n";
        return string;
    }

    /**
     * Fonction générant l'entête du code
     * 
     * @return entête du code
     */
    public static String genererEntete() {
        return "\\documentclass[hyperref={pdfpagemode=FullScreen,colorlinks=true}]{beamer}\n" + "\\usepackage{kpfonts}\n" + "\\usepackage[utf8]{inputenc}\n" + "\\usepackage[T1]{fontenc}\n"
                + "\\usepackage{pdfpages}\n" + "\\usetheme{Boadilla}\n" + "\\usepackage{tikz}\n" + "\\usepackage[frenchb]{babel}\n"
                + "\\usepackage[ruled,vlined,linesnumberedhidden,french,slide]{algorithm2e}";
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
        char[] protectedChar = { '&', '\\', ';', '{', '}' };
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
        String markedString = "{";
        int markedSize = marked.size();
        if (markedSize > 0) {
            for (int i = 0; i < markedSize; i++) {
                markedString += marked.get(i) + ((i == (markedSize - 1)) ? "" : ",");
            }
        }
        markedString += "}";

        // Chaine représentant les numéros des diapos non marquées
        String unmarkedString = "{";
        int unmarkedSize = unmarked.size();
        if (unmarkedSize > 0) {
            for (int i = 0; i < unmarkedSize; i++) {
                unmarkedString += unmarked.get(i) + ((i == (unmarkedSize - 1)) ? "" : ",");
            }
        }
        unmarkedString += "}";

        // Chaine de la ligne
        String ligneString = "{";
        for (char c : ligne.toCharArray()) {
            ligneString += (c == '\n') ? "" : (isProtectedChar(c)) ? "\\" + c : c;
        }
        ligneString += "}\n";

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

        res += "\\begin{minipage}[t]{6.2cm}\n" + "\\begin{tiny}\n" + "\\begin{algorithm*}[H]\n" + "\\NoCaptionOfAlgo\n\n";

        ArrayList<String> lignes = separerLignes(pseudoCode.getTextArea().getText());
        for (int i = 1; i <= lignes.size(); i++) {
            res += genererColorCode(pseudoCode, lignes.get(i - 1), i);
        }

        res += "\n" + "\\end{algorithm*}\n" + "\\end{tiny}\n" + "\\end{minipage}\\hspace*{0.2cm}\n";
        return res;
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
        res += "\\begin{minipage}[t]{3.5cm}\n" + "\\begin{scriptsize}\n";
        res += genererEnteteTableau(tableau) + "\n";
        res += genererCorpsTableau(tableau) + "\n";
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
            TreeSet<Integer> diapos = cell.get(val);
            int i = 0;
            for (int diapo : diapos) {
                res += "" + diapo + ((i == diapos.size() - 1) ? "" : ",");
                i++;
            }
            res += ">{" + ((val == null || val.equals("")) ? "" : genererColorCodeTableau(tableau, ligne, colonne, val)) + "}";
        }
        return res;
    }

    public static String genererColorCodeTableau(Tableau tableau, int ligne, int colonne, String s) {
        String res = "\\colorCode";
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
        String markedString = "{";
        int markedSize = marked.size();
        if (markedSize > 0) {
            for (int i = 0; i < markedSize; i++) {
                markedString += marked.get(i) + ((i == (markedSize - 1)) ? "" : ",");
            }
        }
        markedString += "}";

        // Chaine représentant les numéros des diapos non marquées
        String unmarkedString = "{";
        int unmarkedSize = unmarked.size();
        if (unmarkedSize > 0) {
            for (int i = 0; i < unmarkedSize; i++) {
                unmarkedString += unmarked.get(i) + ((i == (unmarkedSize - 1)) ? "" : ",");
            }
        }
        unmarkedString += "}";

        // Chaine de la ligne
        String ligneString = "{" + protegerCaracteres(s) + "}";

        return res + unmarkedString + markedString + ligneString;
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
    public static String genererColorCode() {
        return "\\newcommand{    \\colorCode}[3]{%\n\\only<#1>{\\textcolor{black}{#3}}\\only<#2>{\\textcolor{red}{#3}}\n}";
    }
}
