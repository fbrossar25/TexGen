package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButtonMenuItem;

import texgen.modele.Lien;
import texgen.vue.Graph;
import texgen.vue.Graph.EtatParcours;

/**
 * Classe contrôleur du choix de l'état pour les noeuds
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class ControleurChoixEtatLien implements ActionListener {
    /** le graph */
    private Graph graph;

    /**
     * Constructeur de la classe
     * 
     * @param graph
     *            le graph
     */
    public ControleurChoixEtatLien(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        JRadioButtonMenuItem source = (JRadioButtonMenuItem) arg0.getSource();
        Lien l = graph.mouseTargetingLink(graph.getLastClick());
        if (l == null) {
            return;
        }
        if (source.getText().equals("Inactif")) {
            graph.changerEtatLienDiapoCourante(l, EtatParcours.Inactif);
        } else if (source.getText().equals("Parcourus")) {
            graph.changerEtatLienDiapoCourante(l, EtatParcours.Parcourus);
        } else if (source.getText().equals("Actif")) {
            graph.changerEtatLienDiapoCourante(l, EtatParcours.Actif);
        } else if (source.getText().equals("Solution")) {
            graph.changerEtatLienDiapoCourante(l, EtatParcours.Solution);
        } else if (source.getText().equals("Non solution")) {
            graph.changerEtatLienDiapoCourante(l, EtatParcours.NonSolution);
        }
        graph.refresh();
    }

}
