package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import texgen.utilities.FileUtilities;
import texgen.utilities.GenerateurLatex;
import texgen.utilities.GestionnaireSauvegarde;
import texgen.vue.BarreMenu;
import texgen.vue.Graph;

/**
 * Classe controleur de la barre de menu
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */

public class ControleurBarreMenu implements ActionListener {

    /** La barre de menu. */
    private BarreMenu barreMenu;

    /**
     * Constructeur de la classe
     * 
     * @param barreMenu
     *            Barre de menu
     */
    public ControleurBarreMenu(BarreMenu barreMenu) {
        this.barreMenu = barreMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        if (source.getText().equals("Quitter")) {
            System.exit(0);
        } else if (source.getText().equals("Fermer")) {
            barreMenu.getFenetre().reset();
            barreMenu.getFenetre().refresh();
        } else if (source.getText().equals("Générer")) {
            String fullPath = FileUtilities.selectFileWithFilter(barreMenu.getFenetre(), "Fichier LaTeX .tex", "tex");
            FileUtilities.writeStringInFile(GenerateurLatex.generer(barreMenu.getFenetre()), fullPath, true);
        } else if (source.getText().equals("Diapo suivante")) {
            barreMenu.getFenetre().diapoSuivante();
        } else if (source.getText().equals("Diapo precedente")) {
            barreMenu.getFenetre().diapoPrecedente();
        } else if (source.getText().equals("Créer diapo")) {
            barreMenu.getFenetre().ajouterDiapo();
        } else if (source.getText().equals("Sauvegarder sous...")) {
            String fullPath = FileUtilities.selectFileWithFilter(barreMenu.getFenetre(), "Fichier XML .xml", "xml");
            GestionnaireSauvegarde.sauvegarder(barreMenu.getFenetre().getNombreDiapos(), barreMenu.getFenetre().getPseudoCode(), barreMenu.getFenetre().getTableau(), fullPath);
        } else if (source.getText().equals("Ouvrir")) {
            String fullPath = FileUtilities.selectFileWithFilter(barreMenu.getFenetre(), "Fichier XML .xml", "xml");
            GestionnaireSauvegarde.charger(barreMenu.getFenetre(), fullPath);
        } else if (source.getText().equals("Créer noeud")) {
            Graph graph = barreMenu.getFenetre().getGraph();
            graph.creerNoeud("" + (char) ('0' + (graph.getNombreNoeuds() % 10)));
        } else if (source.getText().equals("Supprimer noeud")) {
            Graph graph = barreMenu.getFenetre().getGraph();
            graph.supprimerNoeudSelectionne();
        } else if (source.getText().equals("Créer lien")) {

        } else if (source.getText().equals("Supprimer lien")) {

        }
    }

}
