package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import texgen.utilities.FileUtilities;
import texgen.utilities.GenerateurLatex;
import texgen.utilities.GestionnaireSauvegarde;
import texgen.utilities.ParserCode;
import texgen.vue.BarreMenu;

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
        } else
            if (source.getText().equals("Générer")) {
                String fullPath = System.getProperty("user.dir") + "/generated.tex";
                FileUtilities.writeStringInFile(GenerateurLatex.generer(barreMenu.getFenetre()), fullPath, true);
                fullPath = System.getProperty("user.dir") + "/parsed.txt";
                FileUtilities.writeStringInFile(ParserCode.parseText(barreMenu.getFenetre().getPseudoCode().getTextArea().getText()), fullPath, true);
            } else
                if (source.getText().equals("Diapo suivante")) {
                    barreMenu.getFenetre().diapoSuivante();
                } else
                    if (source.getText().equals("Diapo precedente")) {
                        barreMenu.getFenetre().diapoPrecedente();
                    } else
                        if (source.getText().equals("Créer diapo")) {
                            barreMenu.getFenetre().ajouterDiapo();
                        } else
                            if (source.getText().equals("Sauvegarder sous...")) {
                                String fullPath = System.getProperty("user.dir") + "/save.xml";
                                GestionnaireSauvegarde.sauvegarder(barreMenu.getFenetre().getPseudoCode(), barreMenu.getFenetre().getTableau(), fullPath);
                            } else
                                if (source.getText().equals("Ouvrir")) {
                                    String fullPath = System.getProperty("user.dir") + "/save.xml";
                                    GestionnaireSauvegarde.charger(barreMenu.getFenetre(), fullPath);
                                }
    }

}
