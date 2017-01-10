package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import texgen.vue.Tableau;

public class ControleurTableau implements ActionListener, TableModelListener {
    private Tableau tableau;
    private boolean active;

    public ControleurTableau(Tableau tableau) {
        this.tableau = tableau;
        active = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (active) {
            JMenuItem source = (JMenuItem) e.getSource();
            if (source.getText().equals("Ajouter colonne")) {
                tableau.ajouterColonne();
            } else if (source.getText().equals("Supprimer colonne")) {
                tableau.supprimerColonne();
            } else if (source.getText().equals("Ajouter ligne")) {
                tableau.ajouterLigne();
            } else if (source.getText().equals("Supprimer ligne")) {
                tableau.supprimerLigne();
            }
        }
    }

    public void setActive(boolean b) {
        active = b;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (active) {
            // System.out.println("value changed at (" + e.getFirstRow() + "," + e.getColumn() + ")");
            if (e.getFirstRow() == 0) {
                tableau.repercuterModifTitre(e.getColumn(), tableau.getDiapoCourante());
            } else {
                tableau.repercuterModif(tableau.getDiapoCourante(), e.getFirstRow(), e.getColumn());
            }
        }
    }
}
