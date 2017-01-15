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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
        res += "\t\t<text>\n";
        for (String l : p.getLignes()) {
            if (l.equals("\n")) {
                res += "\t\t\t<ligne></ligne>\n";
            } else {
                res += "\t\t\t<ligne>" + l + "</ligne>\n";
            }
        }
        res += "\t\t</text>\n";

        // Sauvegarde des marqueurs
        res += "\t\t<marqueurs>\n";
        TreeSet<Integer> marqueurs;
        for (int i = 1; i <= p.getNombreDiapos(); i++) {
            marqueurs = p.getMarqueursDiapo(i);
            res += "\t\t\t<diapo numero=\"" + i + "\">\n";
            for (Integer m : marqueurs) {
                res += "\t\t\t\t<marqueur>" + m + "</marqueur>\n";
            }
            res += "\t\t\t</diapo>\n";
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
            res += "\t\t\t<colonne numero=\"" + count + "\">" + nom + "</colonne>\n";
            count++;
        }
        res += "\t\t</colonnes>\n";

        // Sauvegarde du contenus des diapos
        for (int i = 1; i <= t.getNombreDiapos(); i++) {
            DefaultTableModel model = t.getDiapo(i);
            res += "\t\t<diapo numero=\"" + i + "\">\n";
            for (int j = 1; j < model.getRowCount(); j++) {
                res += "\t\t\t<ligne numero=\"" + j + "\">\n";
                for (int k = 0; k < model.getColumnCount(); k++) {
                    String value = (String) model.getValueAt(j, k);
                    if ((value != null) && !value.equals("")) {
                        res += "\t\t\t\t<case numero=\"" + (k + 1) + "\">" + value + "</case>\n";
                    }
                }
                res += "\t\t\t</ligne>\n";
            }
            res += "\t\t</diapo>\n";
        }
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
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File(fullPath));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la création du parser XML");
            return;
        }
        if (document != null) {
            // On vérifie le prologue du XML
            if (document.getXmlStandalone() && document.getXmlEncoding().equals("UTF-8") && document.getXmlVersion().equals("1.0")) {
                // Element racine
                final Element projet = document.getDocumentElement();
                if (!projet.getNodeName().equals("projet")) {
                    System.out.println("Erreur : nom du noeud racine incorrect");
                    return;
                }

                final NodeList projetNoeuds = projet.getChildNodes();
                int nombreItems = projetNoeuds.getLength();
                // item 1 = pseudocode, item 3 = tableau
                if ((nombreItems == 5) || (projetNoeuds.item(1).getNodeName() == "pseudocode") || (projetNoeuds.item(3).getNodeName() == "tableau")) {
                    Element elmPseudoCode = (Element) projetNoeuds.item(1);
                    chargerPseudoCodeXML(f.getPseudoCode(), elmPseudoCode);
                    Element elmTableau = (Element) projetNoeuds.item(3);
                    chargerTableauXML(f.getTableau(), elmTableau);
                } else {
                    System.out.println("Erreur : format du fichier XML incorrect");
                }
            } else {
                System.out.println("Erreur : prologue XML incorrect");
                return;
            }
        } else {
            System.out.println("Erreur lors de la création du parser XML");
            return;
        }
    }

    public static void chargerPseudoCodeXML(PseudoCode p, Element elm) {
        // TODO
    }

    public static void chargerTableauXML(Tableau t, Element elm) {
        // TODO
    }
}
