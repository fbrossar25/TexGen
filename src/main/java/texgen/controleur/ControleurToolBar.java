package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import texgen.vue.ToolBar;

public class ControleurToolBar implements ActionListener {
    private ToolBar toolBar;

    public ControleurToolBar(ToolBar toolBar) {
        this.toolBar = toolBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source == toolBar.getBoutonSuivant()) {
            toolBar.getFenetre().diapoSuivante();
        } else if (source == toolBar.getBoutonPrecedent()) {
            toolBar.getFenetre().diapoPrecedente();
        } else if (source == toolBar.getBoutonCreer()) {
            toolBar.getFenetre().ajouterDiapo();
        }
    }
}
