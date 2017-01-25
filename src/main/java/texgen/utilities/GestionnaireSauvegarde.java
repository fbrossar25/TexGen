package texgen.utilities;

import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import texgen.vue.FenetrePrincipale;
import texgen.vue.PseudoCode;
import texgen.vue.Tableau;

/**
 * Classe gérant la sauvegarde d'un projet dans un fichier xml
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class GestionnaireSauvegarde {
    /**
     * Méthode générant un fichier de sauvegarde au format XML 1.0
     * 
     * @param p
     *            le pseudoCode
     * @param t
     *            le tableau
     * @param fullPath
     *            le chemin du fichier de sauvegarde
     */
    public static void sauvegarder(int nombreDiapos, PseudoCode p, Tableau t, String fullPath) {
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n";
        s += "<!DOCTYPE projet [\n";
        s += "\t<!ELEMENT projet (pseudocode, tableau) >\n";
        s += "\t<!ATTLIST projet diapos CDATA #REQUIRED >\n\n";
        s += "\t<!ELEMENT pseudocode (texte, marqueurs) >\n";
        s += "\t<!ELEMENT texte (ligne_p)* >\n";
        s += "\t<!ELEMENT ligne_p (#PCDATA) >\n";
        s += "\t<!ELEMENT marqueurs (diapo_p)+ >\n";
        s += "\t<!ELEMENT diapo_p (marqueur)* >\n";
        s += "\t<!ATTLIST diapo_p numero CDATA #REQUIRED >\n";
        s += "\t<!ELEMENT marqueur (#PCDATA) >\n\n";
        s += "\t<!ELEMENT tableau (colonnes, diapos_t) >\n";
        s += "\t<!ATTLIST tableau lignes CDATA #REQUIRED >\n";
        s += "\t<!ATTLIST tableau colonnes CDATA #REQUIRED >\n";
        s += "\t<!ELEMENT colonnes (colonne)+ >\n";
        s += "\t<!ELEMENT colonne (#PCDATA) >\n";
        s += "\t<!ATTLIST colonne numero CDATA #REQUIRED >\n";
        s += "\t<!ELEMENT diapos_t (diapo_t)+ >\n";
        s += "\t<!ELEMENT diapo_t (ligne_t)+ >\n";
        s += "\t<!ATTLIST diapo_t numero CDATA #REQUIRED >\n";
        s += "\t<!ELEMENT ligne_t (case)* >\n";
        s += "\t<!ATTLIST ligne_t numero CDATA #REQUIRED >\n";
        s += "\t<!ELEMENT case (#PCDATA) >\n";
        s += "\t<!ATTLIST case numero CDATA #REQUIRED >\n";
        s += "]>\n";
        s += "<projet diapos=\"" + nombreDiapos + "\">\n";
        s += sauvegarderPseudoCode(p) + "\n\n" + sauvegarderTableau(t);
        s += "\n</projet>";
        FileUtilities.writeStringInFile(s, fullPath, false);
    }

    /**
     * Fonction retournant la chaine de caracteres XML caracterisant le pseudoCode
     * 
     * @param p
     *            le pseudoCode
     * @return la chaine XML du pseudoCode
     */
    public static String sauvegarderPseudoCode(PseudoCode p) {
        String res = "\t<pseudocode>\n";

        // Sauvegarde du texte
        res += "\t\t<texte>\n";
        for (String l : p.getLignes()) {
            if (l.equals("\n")) {
                res += "\t\t\t<ligne_p></ligne_p>\n";
            } else {
                res += "\t\t\t<ligne_p><![CDATA[" + l + "]]></ligne_p>\n";
            }
        }
        res += "\t\t</texte>\n";

        // Sauvegarde des marqueurs
        res += "\t\t<marqueurs>\n";
        TreeSet<Integer> marqueurs;
        for (int i = 1; i <= p.getNombreDiapos(); i++) {
            marqueurs = p.getMarqueursDiapo(i);
            res += "\t\t\t<diapo_p numero=\"" + i + "\">\n";
            for (Integer m : marqueurs) {
                res += "\t\t\t\t<marqueur>" + m + "</marqueur>\n";
            }
            res += "\t\t\t</diapo_p>\n";
        }
        res += "\t\t</marqueurs>\n";
        return res + "\t</pseudocode>";
    }

    /**
     * Fonction retournant la chaine de caracteres XML caracterisant le tableau
     * 
     * @param t
     *            le tableau
     * @return la chaine XML du tableau
     */
    public static String sauvegarderTableau(Tableau t) {
        String res = "\t<tableau lignes=\"" + t.getNombreLignes() + "\" colonnes=\"" + t.getNombreColonnes() + "\">\n";

        // Sauvegarde des noms des colonnes
        res += "\t\t<colonnes>\n";
        int count = 0;
        for (String nom : t.getNomsColonnes()) {
            res += "\t\t\t<colonne numero=\"" + count + "\"><![CDATA[" + nom + "]]></colonne>\n";
            count++;
        }
        res += "\t\t</colonnes>\n";

        // Sauvegarde du contenus des diapos
        res += "\t\t<diapos_t>\n";
        for (int i = 1; i <= t.getNombreDiapos(); i++) {
            DefaultTableModel model = t.getDiapo(i);
            res += "\t\t\t<diapo_t numero=\"" + i + "\">\n";
            for (int j = 1; j < model.getRowCount(); j++) {
                res += "\t\t\t\t<ligne_t numero=\"" + j + "\">\n";
                for (int k = 0; k < model.getColumnCount(); k++) {
                    String value = (String) model.getValueAt(j, k);
                    if ((value != null) && !value.equals("")) {
                        res += "\t\t\t\t\t<case numero=\"" + k + "\"><![CDATA[" + value + "]]></case>\n";
                    }
                }
                res += "\t\t\t\t</ligne_t>\n";
            }
            res += "\t\t\t</diapo_t>\n";
        }
        res += "\t\t</diapos_t>\n";
        return res + "\t</tableau>";
    }

    /**
     * Fonction permettant de charger une sauvegarde dans la fenetre
     * 
     * @param f
     *            la fenetre
     * @param fullPath
     *            le chemin complet de la sauvegarde
     */
    public static void charger(FenetrePrincipale f, String fullPath) {
        if ((f == null) || (fullPath == null) || fullPath.equals("")) {
            return;
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        factory.setValidating(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new XMLErrorHandler());
            try {
                Document xml = builder.parse(new File(fullPath));
                Element root = xml.getDocumentElement();
                XPathFactory xpf = XPathFactory.newInstance();
                XPath path = xpf.newXPath();
                chargerReset(root, f, path);
                chargerTextePseudoCode(root, f.getPseudoCode(), path);
                chargerMarqueursPseudoCode(root, f.getPseudoCode(), path);
                chargerTableau(root, f.getTableau(), path);
                f.refresh();
            } catch (SAXParseException e) {
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction permettant de charger les données du tableau d'un fichier de sauvegarde
     * 
     * @param root
     *            l'element racine de la sauvegarde
     * @param t
     *            le tableau
     * @param path
     *            une instance de XPath
     */
    public static void chargerTableau(Element root, Tableau t, XPath path) {
        chargerColonnesTableau(root, t, path);
        chargerDonneesTableau(root, t, path);
    }

    /**
     * Fonction permettant de charger les données du tableau d'un fichier de sauvegarde
     * 
     * @param root
     *            l'element racine de la sauvegarde
     * @param t
     *            le tableau
     * @param path
     *            une instance de XPath
     */
    public static void chargerDonneesTableau(Element root, Tableau t, XPath path) {
        try {
            String expression = "/projet/tableau/diapos_t/*";
            // liste des diapos
            NodeList listDiapos = (NodeList) path.evaluate(expression, root, XPathConstants.NODESET);
            int nombreDiapos = listDiapos.getLength();
            // System.out.println(nombreDiapos + " diapos : ");
            if (nombreDiapos > 0) {
                // Parcours des diapos
                for (int i = 0; i < nombreDiapos; i++) {
                    // liste des lignes de la diapo
                    NodeList listLignes = listDiapos.item(i).getChildNodes();
                    int nombreLignes = listLignes.getLength();
                    expression = "/projet/tableau/diapos_t/diapo_t[" + (i + 1) + "]/@numero";
                    int diapo = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
                    // System.out.println("Diapos " + diapo + " : " + nombreLignes + " lignes");
                    // parcours des lignes
                    for (int j = 0; j < nombreLignes; j++) {
                        NodeList listCases = listLignes.item(j).getChildNodes();
                        int nombreCases = listCases.getLength();
                        int numeroLigne = 0;
                        try {
                            numeroLigne = Integer.parseInt(listLignes.item(j).getAttributes().getNamedItem("numero").getTextContent());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            System.out.println("diapo " + (i + 1) + ", ligne " + (j + 1) + " : l'attribut 'numero' n'existe pas ou n'est pas un entier");
                            continue;
                        }
                        // System.out.println("\tLigne " + numeroLigne + " : " + nombreCases + " cases");
                        // Parcours des cases
                        for (int k = 0; k < nombreCases; k++) {
                            expression = "/projet/tableau/diapos_t/diapo_t[@numero='" + diapo + "']/ligne_t[@numero='" + numeroLigne + "']/case[" + (k + 1) + "]/@numero";
                            int numCase = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
                            String value = listCases.item(k).getTextContent();
                            // System.out.println("\t\tCase " + numCase + " : valeur = '" + value + "'");
                            t.setValueAt(value, diapo, numeroLigne, numCase);
                        }
                    }
                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction permettant de charger les noms des colonnes du tableau d'un fichier de sauvegarde
     * 
     * @param root
     *            l'element racine de la sauvegarde
     * @param t
     *            le tableau
     * @param path
     *            une instance de XPath
     */
    public static void chargerColonnesTableau(Element root, Tableau t, XPath path) {
        try {
            String expression = "count(/projet/tableau/colonnes/colonne)";
            int nombreColonnes = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
            expression = "/projet/tableau/colonnes/*";
            NodeList listColonnes = (NodeList) path.evaluate(expression, root, XPathConstants.NODESET);
            for (int i = 0; i < nombreColonnes; i++) {
                int numeroColonne = 0;
                try {
                    numeroColonne = Integer.parseInt(listColonnes.item(i).getAttributes().getNamedItem("numero").getTextContent());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    System.out.println("colonne " + (i + 1) + " : l'attribut 'numero' n'existe pas ou n'est pas un entier");
                    continue;
                }
                String value = listColonnes.item(i).getTextContent();
                t.setValueAt(value, 1, 0, numeroColonne);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction permettant d'appeler la fonction reset sur la fenetre f avec les informations d'un fichier de sauvegarde
     * 
     * @param root
     *            l'element racine de la sauvegarde
     * @param f
     *            la fenetrePrincipale
     * @param path
     *            une instance de XPath
     */
    public static void chargerReset(Element root, FenetrePrincipale f, XPath path) {
        try {
            String expression = "/projet/@diapos";
            int nombreDiapos = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
            expression = "/projet/tableau/@lignes";
            int nombreLignes = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
            expression = "/projet/tableau/@colonnes";
            int nombreColonnes = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
            // System.out.println("Reset fenetre with (" + nombreDiapos + ',' + nombreLignes + ',' + nombreColonnes + ")");
            f.reset(nombreDiapos, nombreLignes, nombreColonnes);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction permettant de charger les marqueurs du pseudoCode d'un fichier de sauvegarde
     * 
     * @param root
     *            l'element racine de la sauvegarde
     * @param p
     *            le pseudoCode
     * @param path
     *            une instance de XPath
     */
    public static void chargerMarqueursPseudoCode(Element root, PseudoCode p, XPath path) {
        try {
            String expression = "/projet/pseudocode/marqueurs/*";
            // liste des diapos
            NodeList listDiapos = (NodeList) path.evaluate(expression, root, XPathConstants.NODESET);
            int nombreDiapos = listDiapos.getLength();
            if (nombreDiapos > 0) {
                for (int i = 0; i < nombreDiapos; i++) {
                    // liste des marqueurs de la diapo
                    NodeList listMarqueurs = listDiapos.item(i).getChildNodes();
                    int nombreMarqueurs = listMarqueurs.getLength();
                    expression = "/projet/pseudocode/marqueurs/diapo_p[" + (i + 1) + "]/@numero";
                    int diapo = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
                    for (int j = 0; j < nombreMarqueurs; j++) {
                        int marqueur = 0;
                        try {
                            marqueur = Integer.parseInt(listMarqueurs.item(j).getTextContent());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            System.out.println("diapos " + (i + 1) + " marqueur " + (j + 1) + " is not an integer");
                            continue;
                        }
                        if (marqueur > 0 && diapo > 0) {
                            p.marquage(diapo, marqueur);
                        }
                    }
                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction permettant de charger le texte du pseudoCode d'un fichier de sauvegarde
     * 
     * @param root
     *            l'element racine de la sauvegarde
     * @param p
     *            le pseudoCode
     * @param path
     *            une instance de XPath
     */
    public static void chargerTextePseudoCode(Element root, PseudoCode p, XPath path) {
        try {
            String expression = "/projet/pseudocode/texte/*";
            NodeList lignes = (NodeList) path.evaluate(expression, root, XPathConstants.NODESET);
            int nombreLignes = lignes.getLength();
            if (nombreLignes > 0) {
                for (int i = 0; i < nombreLignes; i++) {
                    p.getTextArea().append(lignes.item(i).getTextContent() + ((i == nombreLignes - 1) ? "" : "\n"));
                }
                p.refreshNombreDeLignes();
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui va parser le contenu d'un nœud (Crédit à openclassrooms.com, cours JAVA et XML)
     * 
     * @param n
     *            le noeud à parser
     * @param tab
     *            la tabulation pour l'affichage
     * @return la chaine décrivant le noeud
     */
    public static String description(Node n, String tab) {
        String str = new String();
        if (n instanceof Element) {
            // Nous sommes donc bien sur un élément de notre document
            // Nous castons l'objet de type Node en type Element
            // Element element = (Element) n;
            // Nous pouvons récupérer le nom du nœud actuellement parcouru
            // grâce à cette méthode, nous ouvrons donc notre balise
            str += "<" + n.getNodeName();
            // nous contrôlons la liste des attributs présents
            if ((n.getAttributes() != null) && (n.getAttributes().getLength() > 0)) {
                // nous pouvons récupérer la liste des attributs d'un élément
                NamedNodeMap att = n.getAttributes();
                int nbAtt = att.getLength();
                // nous parcourons tous les nœuds pour les afficher
                for (int j = 0; j < nbAtt; j++) {
                    Node noeud = att.item(j);
                    // On récupère le nom de l'attribut et sa valeur grâce à ces deux méthodes
                    str += " " + noeud.getNodeName() + "=\"" + noeud.getNodeValue() + "\" ";
                }
            }
            // nous refermons notre balise car nous avons traité les différents attributs
            str += ">";
            // La méthode getChildNodes retournant le contenu du nœud + les nœuds enfants
            // Nous récupérons le contenu texte uniquement lorsqu'il n'y a que du texte, donc un seul enfant
            if (n.getChildNodes().getLength() == 1) {
                str += n.getTextContent();
            }
            // Nous allons maintenant traiter les nœuds enfants du nœud en cours de traitement
            int nbChild = n.getChildNodes().getLength();
            // Nous récupérons la liste des nœuds enfants
            NodeList list = n.getChildNodes();
            String tab2 = tab + "\t";
            // nous parcourons la liste des nœuds
            for (int i = 0; i < nbChild; i++) {
                Node n2 = list.item(i);
                // si le nœud enfant est un Element, nous le traitons
                if (n2 instanceof Element) {
                    // appel récursif à la méthode pour le traitement du nœud et de ses enfants
                    str += "\n " + tab2 + description(n2, tab2);
                }
            }
            // Nous fermons maintenant la balise
            if (n.getChildNodes().getLength() < 2) {
                str += "</" + n.getNodeName() + ">";
            } else {
                str += "\n" + tab + "</" + n.getNodeName() + ">";
            }
        }
        return str;
    }
}
