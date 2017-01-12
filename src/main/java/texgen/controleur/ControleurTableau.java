package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import texgen.vue.Tableau;

/**
 * Classe gèrant les controleurs du tableau
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 * 
 */
public class ControleurTableau implements ActionListener, TableModelListener {
	
	/** Tableau */
	private Tableau tableau;
	
	/** Active ou désactive un composant */
    private boolean active;

    /**
     * Constructeur de la classe
     * 
     * @param tableau
     * 	tableau
     */
    public ControleurTableau(Tableau tableau) {
        this.tableau = tableau;
        active = true;
    }

    /**
     * Fonction gérant les actions liées au tableau
     * 
     * @param e 
     * 	Evénement lié à l'action
     * 
     */
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
     * Fonction qui permet de changer la valeur de la variable active
     * 
     * @param b
     * 	Activation ou désactivation d'un composant
     */
    public void setActive(boolean b) {
        active = b;
    }

    /**
     * Fonction qui gére la modification des informations dans le tableau
     * 
     * @param e
     * 	Evénement lié au model du tableau
     */
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
