package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import texgen.vue.BarreMenu;
import utilities.FileUtilities;
import utilities.GenerateurLatex;

public class ControleurBarreMenu implements ActionListener {

    private BarreMenu barreMenu;

    public ControleurBarreMenu(BarreMenu barreMenu) {
        this.barreMenu = barreMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        if (source.getText().equals("Quitter")) {
            System.exit(0);
        } else if (source.getText().equals("Générer")) {
            String fullPath = System.getProperty("user.dir") + "/generated.tex";
            FileUtilities.writeStringInFile(GenerateurLatex.generer(barreMenu.getFenetre()), fullPath);
        } else if (source.getText().equals("Diapo suivante")) {
            barreMenu.getFenetre().diapoSuivante();
        } else if (source.getText().equals("Diapo precedente")) {
            barreMenu.getFenetre().diapoPrecedente();
        } else if (source.getText().equals("Créer diapo")) {
            barreMenu.getFenetre().ajouterDiapo();
        }
    }

}