package texgen.utilities;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.JOptionPane;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import texgen.modele.InfosPresentation;
import texgen.modele.Lien;
import texgen.modele.Noeud;
import texgen.modele.Noeud.TypeForme;
import texgen.vue.FenetrePrincipale;
import texgen.vue.Graph;
import texgen.vue.Graph.EtatParcours;
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
     * Retourne l'enete DTD du fichier XML
     * 
     * @return l'entete DTD
     */
    public static String enteteDTD() {
        String s = "<!DOCTYPE projet [\n";
        s += "\t<!ELEMENT projet (infos, pseudocode, tableau, graph) >\n";
        s += "\t<!ATTLIST projet diapos CDATA #REQUIRED >\n\n";
        s += "\t<!ELEMENT infos (auteur, titre, titreCourt, sousTitre, algo, institut, date, sizes, couleurs) >\n";
        s += "\t<!ELEMENT auteur (#PCDATA) >\n";
        s += "\t<!ELEMENT titre (#PCDATA) >\n";
        s += "\t<!ELEMENT titreCourt (#PCDATA) >\n";
        s += "\t<!ELEMENT sousTitre (#PCDATA) >\n";
        s += "\t<!ELEMENT algo (#PCDATA) >\n";
        s += "\t<!ELEMENT institut (#PCDATA) >\n";
        s += "\t<!ELEMENT date (#PCDATA) >\n";
        s += "\t<!ELEMENT sizes (hspaceCodeTab, codeSize, tabSize) >\n";
        s += "\t<!ELEMENT hspaceCodeTab EMPTY >\n";
        s += "\t<!ATTLIST hspaceCodeTab value CDATA #REQUIRED >\n";
        s += "\t<!ELEMENT codeSize EMPTY >\n";
        s += "\t<!ATTLIST codeSize value CDATA #REQUIRED >\n";
        s += "\t<!ELEMENT tabSize EMPTY >\n";
        s += "\t<!ATTLIST tabSize value CDATA #REQUIRED >\n";
        s += "\t<!ELEMENT couleurs (couleurNoeudInitial, couleursNoeuds, couleursLiens) >\n";
        s += "\t<!ELEMENT couleurNoeudInitial EMPTY >\n";
        s += "\t<!ATTLIST couleurNoeudInitial r CDATA #REQUIRED >\n";
        s += "\t<!ATTLIST couleurNoeudInitial g CDATA #REQUIRED >\n";
        s += "\t<!ATTLIST couleurNoeudInitial b CDATA #REQUIRED >\n";
        s += "\t<!ELEMENT couleursNoeuds (couleur)* >\n";
        s += "\t<!ELEMENT couleursLiens (couleur)* >\n";
        s += "\t<!ELEMENT couleur EMPTY >\n";
        s += "\t<!ATTLIST couleur etat CDATA #REQUIRED >\n";
        s += "\t<!ATTLIST couleur r CDATA #REQUIRED >\n";
        s += "\t<!ATTLIST couleur g CDATA #REQUIRED >\n";
        s += "\t<!ATTLIST couleur b CDATA #REQUIRED >\n\n";
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
        s += "\t<!ATTLIST case numero CDATA #REQUIRED >\n\n";
        s += "\t<!ELEMENT graph (noeuds, liens) >\n";
        s += "\t<!ATTLIST graph styleLien (0|1) \"0\" >\n";
        s += "\t<!ATTLIST graph noeudInitial CDATA #REQUIRED >\n";
        s += "\t<!ELEMENT noeuds (noeud)* >\n";
        s += "\t<!ELEMENT noeud (label,etats) >\n";
        s += "\t<!ELEMENT label (#PCDATA) >\n";
        s += "\t<!ELEMENT etats (diapo_g)+ >\n";
        s += "\t<!ELEMENT diapo_g (#PCDATA) >\n";
        s += "\t<!ATTLIST diapo_g numero CDATA #REQUIRED >\n";
        s += "\t<!ATTLIST noeud x CDATA #REQUIRED >\n";
        s += "\t<!ATTLIST noeud y CDATA #REQUIRED >\n";
        s += "\t<!ATTLIST noeud id CDATA #REQUIRED >\n";
        s += "\t<!ATTLIST noeud forme (0|1) \"0\" >\n";
        s += "\t<!ELEMENT liens (lien)* >\n";
        s += "\t<!ELEMENT lien (label,etats) >\n";
        s += "\t<!ATTLIST lien depart CDATA #REQUIRED >\n";
        s += "\t<!ATTLIST lien arrive CDATA #REQUIRED >\n";
        s += "]>\n";
        return s;
    }

    /**
     * Méthode générant un fichier de sauvegarde au format XML 1.0
     * 
     * @param nombreDiapos
     *            le nombre de diapos
     * @param i
     *            les infos de la présentation
     * @param p
     *            le pseudoCode
     * @param t
     *            le tableau
     * @param g
     *            le graph
     * @param fullPath
     *            le chemin du fichier de sauvegarde
     */
    public static void sauvegarder(int nombreDiapos, InfosPresentation i, PseudoCode p, Tableau t, Graph g, String fullPath) {
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n";
        // TODO utiliser un schema plutôt qu'un DTD dans l'entête
        s += enteteDTD();
        s += "<projet diapos=\"" + nombreDiapos + "\">\n\n";
        s += sauvegarderInfos(i) + "\n\n" + sauvegarderPseudoCode(p) + "\n\n" + sauvegarderTableau(t) + "\n\n" + sauvegarderGraph(g);
        s += "\n</projet>";
        FileUtilities.writeStringInFile(s, fullPath, false);
    }

    /**
     * Sauvegarde les informations (nom de l'auteur, de la présentation,...) et les couleurs utilisés dans le graph
     * 
     * @param i
     *            le modèles correspondant aux informations
     * @return la chaîne XML correspondant aux informations
     */
    public static String sauvegarderInfos(InfosPresentation i) {
        String res = "\t<infos>\n";
        res += "\t\t<auteur>" + i.getNomAuteur() + "</auteur>\n";
        res += "\t\t<titre>" + i.getTitrePresentation() + "</titre>\n";
        res += "\t\t<titreCourt>" + i.getTitrePresentationCourt() + "</titreCourt>\n";
        res += "\t\t<sousTitre>" + i.getSousTitrePresentation() + "</sousTitre>\n";
        res += "\t\t<algo>" + i.getTitreAlgo() + "</algo>\n";
        res += "\t\t<institut>" + i.getInstitut() + "</institut>\n";
        res += "\t\t<date>" + i.getDate() + "</date>\n";
        res += "\t\t<sizes>\n";
        res += "\t\t\t<hspaceCodeTab value=\"" + i.getHSpaceCodeTab() + "\" />\n";
        res += "\t\t\t<codeSize value=\"" + i.getCodeSize() + "\" />\n";
        res += "\t\t\t<tabSize value=\"" + i.getTabSize() + "\" />\n";
        res += "\t\t</sizes>\n";
        res += "\t\t<couleurs>\n";
        Color c = i.getCouleurNoeudInitial();
        res += "\t\t\t<couleurNoeudInitial r=\"" + c.getRed() + "\" g=\"" + c.getGreen() + "\" b=\"" + c.getBlue() + "\" />\n";
        res += "\t\t\t<couleursNoeuds>\n";
        for (EtatParcours etat : EtatParcours.values()) {
            c = i.getCouleursNoeuds().get(etat);
            res += "\t\t\t\t<couleur etat=\"" + i.getIntForEtat(etat) + "\" r=\"" + c.getRed() + "\" g=\"" + c.getGreen() + "\" b=\"" + c.getBlue() + "\"/>\n";
        }
        res += "\t\t\t</couleursNoeuds>\n";
        res += "\t\t\t<couleursLiens>\n";
        for (EtatParcours etat : EtatParcours.values()) {
            c = i.getCouleursLiens().get(etat);
            res += "\t\t\t\t<couleur etat=\"" + i.getIntForEtat(etat) + "\" r=\"" + c.getRed() + "\" g=\"" + c.getGreen() + "\" b=\"" + c.getBlue() + "\"/>\n";
        }
        res += "\t\t\t</couleursLiens>\n";
        res += "\t\t</couleurs>\n";
        return res + "\t</infos>";
    }

    /**
     * Fonction retournant la chaine de caractere XML caracterisant le graph
     * 
     * @param g
     *            le graph
     * @return la chaine XML du graph
     */
    public static String sauvegarderGraph(Graph g) {
        String res = "";
        res += "\t\t<noeuds>\n";
        int i = 0;
        int idInit = -1;
        for (Noeud n : g.getNoeuds()) {
            if (n == g.getNoeudInitial()) {
                idInit = i;
            }
            String forme = (n.getForme() == TypeForme.Double) ? " forme = \"1\"" : "";
            res += "\t\t\t<noeud id=\"" + i + "\" x=\"" + n.getCentre().x + "\" y=\"" + n.getCentre().y + "\"" + forme + ">\n";
            res += "\t\t\t\t<label><![CDATA[" + n.getText() + "]]></label>\n";
            res += "\t\t\t\t<etats>\n";
            ArrayList<EtatParcours> etats = g.getEtatsNoeud(n);
            int sz = etats.size();
            EtatParcours etat, prev = null;
            for (int j = 0; j < sz; j++) {
                etat = etats.get(j);
                if (prev == null || etat != prev) {
                    res += "\t\t\t\t\t<diapo_g numero=\"" + (j + 1) + "\">" + g.getIntForEtat(etat) + "</diapo_g>\n";
                }
                prev = etat;
            }
            res += "\t\t\t\t</etats>\n";
            res += "\t\t\t</noeud>\n";
            i++;
        }
        res = "\t<graph" + ((g.isArrow()) ? " styleLien=\"1\"" : "") + ((idInit != -1) ? " noeudInitial=\"" + idInit + "\"" : "") + " >\n" + res;
        res += "\t\t</noeuds>\n";

        res += "\t\t<liens>\n";

        int depart, arrive;
        for (Lien l : g.getLiens()) {
            depart = g.getNoeuds().indexOf(l.getDepart());
            arrive = g.getNoeuds().indexOf(l.getArrive());
            res += "\t\t\t<lien depart=\"" + depart + "\" arrive=\"" + arrive + "\">\n";
            res += "\t\t\t\t<label><![CDATA[" + l.getText() + "]]></label>\n";
            res += "\t\t\t\t<etats>\n";
            ArrayList<EtatParcours> etats = g.getEtatsLien(l);
            int sz = etats.size();
            EtatParcours etat, prev = null;
            for (int j = 0; j < sz; j++) {
                etat = etats.get(j);
                if (prev == null || etat != prev) {
                    res += "\t\t\t\t\t<diapo_g numero=\"" + (j + 1) + "\">" + g.getIntForEtat(etat) + "</diapo_g>\n";
                }
                prev = etat;
            }
            res += "\t\t\t\t</etats>\n";
            res += "\t\t\t</lien>\n";
        }
        res += "\t\t</liens>\n";
        return res + "\t</graph>";
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
        for (int i = 1; i <= t.getNombreDiapos(); i++) {// diapo
            DefaultTableModel model = t.getDiapo(i);
            res += "\t\t\t<diapo_t numero=\"" + i + "\">\n";
            for (int j = 1; j < model.getRowCount(); j++) {// ligne
                res += "\t\t\t\t<ligne_t numero=\"" + j + "\">\n";
                for (int k = 0; k < model.getColumnCount(); k++) {// case
                    String value = (String) model.getValueAt(j, k);
                    // TODO optimisation
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
        // On utilise la validation pour éviter un mouvais fichier XML
        factory.setValidating(true);
        try {
            // System.out.println("Loading...");
            // long begin = System.nanoTime();
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new XMLErrorHandler());
            try {
                Document xml = builder.parse(new File(fullPath));
                // long parseTime = System.nanoTime() - begin;
                Element root = xml.getDocumentElement();
                XPathFactory xpf = XPathFactory.newInstance();
                XPath path = xpf.newXPath();
                chargerReset(root, f, path);
                chargerInfos(root, f.getInfos(), path);
                chargerTextePseudoCode(root, f.getPseudoCode(), path);
                chargerMarqueursPseudoCode(root, f.getPseudoCode(), path);
                chargerTableau(root, f.getTableau(), path);
                chargerGraph(root, f.getGraph(), path);
                // long loadTime = System.nanoTime() - (parseTime + begin);
                // long totalTime = parseTime + loadTime;
                // System.out.println("parse time : " + parseTime / 1000000 + "ms");
                // System.out.println("load time : " + loadTime / 1000000 + "ms");
                // System.out.println("total time : " + totalTime / 1000000 + "ms");
                f.refresh();
            } catch (SAXParseException e) {
                JOptionPane.showMessageDialog(f, "Fichier invalide, introuvable ou illisible", "Erreur", JOptionPane.ERROR_MESSAGE);
                f.reset();
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(f, "Fichier invalide, introuvable ou illisible", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Fonction permettant de charger les informations de la présentation d'un fichier de sauvegarde
     * 
     * @param root
     *            l'element racine de la sauvegarde
     * @param infos
     *            les informations de la présentation
     * @param path
     *            une instance de XPath
     */
    public static void chargerInfos(Element root, InfosPresentation infos, XPath path) {
        String infosPath = "/projet/infos";
        try {
            // On récupère les informations de la présentation
            infos.setNomAuteur((String) path.evaluate(infosPath + "/auteur/text()", root, XPathConstants.STRING));
            infos.setTitrePresentation((String) path.evaluate(infosPath + "/titre/text()", root, XPathConstants.STRING));
            infos.setTitrePresentationCourt((String) path.evaluate(infosPath + "/titreCourt/text()", root, XPathConstants.STRING));
            infos.setSousTitrePresentation((String) path.evaluate(infosPath + "/sousTitre/text()", root, XPathConstants.STRING));
            infos.setTitreAlgo((String) path.evaluate(infosPath + "/algo/text()", root, XPathConstants.STRING));
            infos.setInstitut((String) path.evaluate(infosPath + "/institut/text()", root, XPathConstants.STRING));
            infos.setDate((String) path.evaluate(infosPath + "/date/text()", root, XPathConstants.STRING));
            infos.setHSpaceCodeTab((Double) path.evaluate(infosPath + "/sizes/hspaceCodeTab/@value", root, XPathConstants.NUMBER));
            infos.setCodeSize((Double) path.evaluate(infosPath + "/sizes/codeSize/@value", root, XPathConstants.NUMBER));
            infos.setTabSize((Double) path.evaluate(infosPath + "/sizes/tabSize/@value", root, XPathConstants.NUMBER));
            int r, g, b, intEtat;
            String infosColorPath = infosPath + "/couleurs/couleurNoeudInitial";
            r = ((Double) path.evaluate(infosColorPath + "/@r", root, XPathConstants.NUMBER)).intValue();
            g = ((Double) path.evaluate(infosColorPath + "/@g", root, XPathConstants.NUMBER)).intValue();
            b = ((Double) path.evaluate(infosColorPath + "/@b", root, XPathConstants.NUMBER)).intValue();
            infos.setCouleurNoeudInitial(new Color(r, g, b));
            // On récupère les couleurs des états
            for (EtatParcours etat : EtatParcours.values()) {
                intEtat = infos.getIntForEtat(etat);
                infosColorPath = infosPath + "/couleurs/couleursNoeuds/couleur[@etat=\"" + intEtat + "\"]";
                r = ((Double) path.evaluate(infosColorPath + "/@r", root, XPathConstants.NUMBER)).intValue();
                g = ((Double) path.evaluate(infosColorPath + "/@g", root, XPathConstants.NUMBER)).intValue();
                b = ((Double) path.evaluate(infosColorPath + "/@b", root, XPathConstants.NUMBER)).intValue();
                infos.getCouleursNoeuds().put(etat, new Color(r, g, b));
            }
            // On récupère les couleurs des liens
            for (EtatParcours etat : EtatParcours.values()) {
                intEtat = infos.getIntForEtat(etat);
                infosColorPath = infosPath + "/couleurs/couleursLiens/couleur[@etat=\"" + intEtat + "\"]";
                r = ((Double) path.evaluate(infosColorPath + "/@r", root, XPathConstants.NUMBER)).intValue();
                g = ((Double) path.evaluate(infosColorPath + "/@g", root, XPathConstants.NUMBER)).intValue();
                b = ((Double) path.evaluate(infosColorPath + "/@b", root, XPathConstants.NUMBER)).intValue();
                infos.getCouleursLiens().put(etat, new Color(r, g, b));
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(infos.getFenetre(), "Fichier illisible", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Fonction permettant de charger les données du graph d'un fichier de sauvegarde
     * 
     * @param root
     *            l'element racine de la sauvegarde
     * @param g
     *            le graph
     * @param path
     *            une instance de XPath
     */
    public static void chargerGraph(Element root, Graph g, XPath path) {
        String expression = "count(/projet/graph/noeuds/*)";
        try {
            // Chargement des noeuds
            int nombreNoeuds = ((Double) path.evaluate(expression, root, XPathConstants.NUMBER)).intValue();
            expression = "/projet/@diapos";
            int nombreDiapos = ((Double) path.evaluate(expression, root, XPathConstants.NUMBER)).intValue();
            HashMap<String, Noeud> noeuds = new HashMap<>();
            for (int i = 1; i <= nombreNoeuds; i++) {
                String xpathNode = "/projet/graph/noeuds/noeud[" + i + "]";
                expression = xpathNode + "/label/text()";
                String value = (String) path.evaluate(expression, root, XPathConstants.STRING);
                expression = xpathNode + "/@x";
                int x = ((Double) path.evaluate(expression, root, XPathConstants.NUMBER)).intValue();
                expression = xpathNode + "/@y";
                int y = ((Double) path.evaluate(expression, root, XPathConstants.NUMBER)).intValue();
                expression = xpathNode + "/@id";
                String id = (String) path.evaluate(expression, root, XPathConstants.STRING);
                noeuds.put(id, g.creerNoeud(value, x, y));
                expression = xpathNode + "/@forme";
                String forme = (String) path.evaluate(expression, root, XPathConstants.STRING);
                if (forme.equals("1")) {
                    noeuds.get(id).changerForme(TypeForme.Double);
                }
                EtatParcours prev = null;
                for (int j = 1; j <= nombreDiapos; j++) {
                    expression = xpathNode + "/etats/diapo_g[@numero='" + j + "']";
                    Node node = (Node) path.evaluate(expression, root, XPathConstants.NODE);
                    // Si le noeud est présent
                    if (node != null) {
                        int etat = ((Double) path.evaluate(expression, root, XPathConstants.NUMBER)).intValue();
                        // on change le dernier etat connus
                        prev = g.getEtatForInt(etat);
                    }
                    if (prev != null) {
                        g.changerEtatNoeud(noeuds.get(id), j, prev);
                    }
                }
                String idInitial = (String) path.evaluate("/projet/graph/@noeudInitial", root, XPathConstants.STRING);
                g.setNoeudInitial(noeuds.get(idInitial));
            }

            // chargement des liens
            expression = "count(/projet/graph/liens/*)";
            int nombreLiens = ((Double) path.evaluate(expression, root, XPathConstants.NUMBER)).intValue();
            for (int i = 1; i <= nombreLiens; i++) {
                String xpathLink = "/projet/graph/liens/lien[" + i + "]";
                expression = xpathLink + "/label/text()";
                String value = (String) path.evaluate(expression, root, XPathConstants.STRING);
                expression = xpathLink + "/@depart";
                String idDepart = (String) path.evaluate(expression, root, XPathConstants.STRING);
                // On récupère un noeud via son id pour éviter de confondre les noeuds avec les mêmes labels
                Noeud depart = noeuds.get(idDepart);
                expression = xpathLink + "/@arrive";
                String idArrive = (String) path.evaluate(expression, root, XPathConstants.STRING);
                Noeud arrive = noeuds.get(idArrive);
                // System.out.println("Restauration avec ('" + value + "', '" + depart.getText() + "' (" + indexDepart + "), '" + arrive.getText() + "' (" + indexArrive + "))");
                Lien l = g.creerLien(value, depart, arrive);
                EtatParcours prev = null;
                for (int j = 1; j <= nombreDiapos; j++) {
                    expression = xpathLink + "/etats/diapo_g[@numero='" + j + "']";
                    Node node = (Node) path.evaluate(expression, root, XPathConstants.NODE);
                    // Si le lien est présent
                    if (node != null) {
                        int etat = ((Double) path.evaluate(expression, root, XPathConstants.NUMBER)).intValue();
                        g.changerEtatLien(l, j, g.getEtatForInt(etat));
                        // on change le dernier etat connus
                        prev = g.getEtatForInt(etat);
                    }
                    if (prev != null) {
                        g.changerEtatLien(l, j, prev);
                    }
                }
            }

            expression = "/projet/graph/@styleLien";
            int style = ((Double) path.evaluate(expression, root, XPathConstants.NUMBER)).intValue();
            g.setArrow(style == 1);
            g.getFenetre().refreshLinkStyleSelection();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(g.getFenetre(), "Fichier illisible", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.err.println("\n\n expression was " + expression);
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
        // On désactive le modelListener du tableau pour éviter les répercussions d'informations non désirées,
        // on répercute manuelement les informations qui le nécessitent (les noms des colonnes notament)
        t.disableAllModelListener();
        chargerColonnesTableau(root, t, path);
        chargerDonneesTableau(root, t, path);
        t.enableAllModelListener();
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
        final String xpath_diapos_t = "/projet/tableau/diapos_t";
        try {
            String expression = "count(" + xpath_diapos_t + "/diapo_t)";
            // liste des diapos
            int nombreDiapos = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
            // System.out.println(nombreDiapos + " diapos : ");
            if (nombreDiapos > 0) {
                // Parcours des diapos
                for (int i = 1; i <= nombreDiapos; i++) {
                    expression = "count(" + xpath_diapos_t + "/diapo_t[" + i + "]/ligne_t)";
                    int nombreLignes = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
                    expression = xpath_diapos_t + "/diapo_t[" + i + "]/@numero";
                    int numeroDiapo = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
                    // System.out.println("Diapos " + numeroDiapo + " : " + nombreLignes + " lignes");
                    // parcours des lignes
                    for (int j = 1; j <= nombreLignes; j++) {
                        expression = "count(" + xpath_diapos_t + "/diapo_t[" + i + "]/ligne_t[" + j + "]/case)";
                        int nombreCases = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
                        expression = xpath_diapos_t + "/diapo_t[" + i + "]/ligne_t[" + j + "]/@numero";
                        int numeroLigne = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
                        // System.out.println("\tLigne " + numeroLigne + " : " + nombreCases + " cases");
                        // Parcours des cases
                        for (int k = 1; k <= nombreCases; k++) {
                            expression = xpath_diapos_t + "/diapo_t[@numero='" + numeroDiapo + "']/ligne_t[@numero='" + numeroLigne + "']/case[" + k + "]/@numero";
                            int numeroCase = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
                            expression = xpath_diapos_t + "/diapo_t[@numero='" + numeroDiapo + "']/ligne_t[@numero='" + numeroLigne + "']/case[@numero='" + numeroCase + "']";
                            String value = (String) path.evaluate(expression, root, XPathConstants.STRING);
                            // System.out.println("\t\tCase " + numeroCase + " : valeur = '" + value + "'");
                            t.setValueAt(value, numeroDiapo, numeroLigne, numeroCase);
                        }
                    }
                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(t.getFenetre(), "Fichier illisible", "Erreur", JOptionPane.ERROR_MESSAGE);
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
            String expression = "count(/projet/tableau/diapos_t/diapo_t)";
            int nombreDiapos = ((Double) (path.evaluate(expression, root, XPathConstants.NUMBER))).intValue();
            expression = "count(/projet/tableau/colonnes/colonne)";
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
                for (int j = 1; j <= nombreDiapos; j++) {
                    t.setValueAt(value, j, 0, numeroColonne);
                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(t.getFenetre(), "Fichier illisible", "Erreur", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(f, "Fichier illisible", "Erreur", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(p.getFenetre(), "Fichier illisible", "Erreur", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(p.getFenetre(), "Fichier illisible", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
