package texgen.utilities;

import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
    public static void sauvegarder(PseudoCode p, Tableau t, String fullPath) {
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n";
        s += "<!DOCTYPE projet [\n";
        s += "\t<!ELEMENT projet (pseudocode, tableau) >\n\n";
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
        s += "<projet>\n";
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
                        res += "\t\t\t\t\t<case numero=\"" + (k + 1) + "\"><![CDATA[" + value + "]]></case>\n";
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
                // System.out.println(description(root, ""));
                for (int i = 0; i < root.getChildNodes().getLength(); i++) {
                    if (root.getChildNodes().item(i).getNodeName().equals("pseudocode")) {
                        chargerPseudoCodeXML(f.getPseudoCode(), root.getChildNodes().item(i));
                    } else if (root.getChildNodes().item(i).getNodeName().equals("tableau")) {
                        chargerTableauXML(f.getTableau(), root.getChildNodes().item(i));
                    }
                }
            } catch (SAXParseException e) {
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui va parser le contenu d'un nœud (Crédit à openclassrooms.com, cours JAVA et XML)
     * 
     * @param n
     * @param tab
     * @return
     */
    public static String description(Node n, String tab) {
        String str = new String();
        if (n instanceof Element) {
            // Nous sommes donc bien sur un élément de notre document
            // Nous castons l'objet de type Node en type Element
            Element element = (Element) n;
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

    public static void chargerPseudoCodeXML(PseudoCode p, Node node) {
        if (!node.getNodeName().equals("pseudocode")) {
            System.out.println("Erreur : nom du noeuds différent de 'pseudocode'");
            return;
        }
        p.reset();
        int nbChild = node.getChildNodes().getLength();
        NodeList list = node.getChildNodes();
        for (int i = 0; i < nbChild; i++) {
            Node n = list.item(i);
            if ((n instanceof Element) && (n.getNodeType() == Node.ELEMENT_NODE)) {
                Element elm = (Element) n;
                if (elm.getNodeName().equals("texte")) {
                    chargerPseudoCodeXMLTexte(p, elm);
                } else if (elm.getNodeName().equals("marqueurs")) {
                    chargerPseudoCodeXMLMarqueurs(p, elm);
                } else {
                    System.out.println("Erreur : balise '" + elm.getNodeName() + "' invalide");
                }
            }
        }
    }

    public static void chargerPseudoCodeXMLTexte(PseudoCode p, Element e) {
        p.getTextArea().setText("");
        NodeList list = e.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals("ligne_p")) {
                p.getTextArea().append(list.item(i).getTextContent());
            }
        }
    }

    public static void chargerPseudoCodeXMLMarqueurs(PseudoCode p, Element e) {

    }

    public static void chargerTableauXML(Tableau t, Node node) {
        // TODO
    }
}
