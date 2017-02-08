package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import texgen.vue.Tableau;

/**
 * Classe controleur du tableau
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class ControleurTableau implements ActionListener, TableModelListener {

    /** Tableau */
    private Tableau tableau;

    /** Active ou désactive le controleur */
    private boolean active;

    /**
     * Constructeur de la classe
     * 
     * @param tableau
     *            tableau
     */
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

    /**
     * Fonction qui permet de définir l'état (actif / inactif) du controleur.
     * 
     * @param b
     *            Activation ou désactivation d'un composant
     */
    public void setActive(boolean b) {
        active = b;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        // Si le controleur est actif on répercute les modifications des noms des colonne (la première ligne)
        // sur toutes les diapos, et on répercute les modifications des autres case sur les diapos
        // suivant la diapos modifiées
        if (active) {
            if (e.getFirstRow() == 0) {
                tableau.repercuterModifTitre(e.getColumn(), tableau.getDiapoCourante());
            } else {
                tableau.repercuterModif(tableau.getDiapoCourante(), e.getFirstRow(), e.getColumn());
            }
        }
    }
}
