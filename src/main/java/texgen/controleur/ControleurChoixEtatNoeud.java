package texgen.controleur;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButtonMenuItem;

import texgen.modele.Noeud;
import texgen.vue.Graph;
import texgen.vue.Graph.EtatParcours;

/**
 * Classe contrôleur du choix de l'état pour les noeuds
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class ControleurChoixEtatNoeud implements ActionListener {
    /** le graph */
    private Graph graph;

    /**
     * Constructeur de la classe
     * 
     * @param graph
     *            le graph
     */
    public ControleurChoixEtatNoeud(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        JRadioButtonMenuItem source = (JRadioButtonMenuItem) arg0.getSource();
        Point p = graph.getLastClick();
        Noeud n = graph.mouseTargetingNode(p);
        if (n == null || p == null) {
            return;
        }
        if (source.getText().equals("Inactif")) {
            graph.changerEtatNoeudDiapoCourante(n, EtatParcours.Inactif);
        } else if (source.getText().equals("Parcourus")) {
            graph.changerEtatNoeudDiapoCourante(n, EtatParcours.Parcourus);
        } else if (source.getText().equals("Actif")) {
            graph.changerEtatNoeudDiapoCourante(n, EtatParcours.Actif);
        } else if (source.getText().equals("Solution")) {
            graph.changerEtatNoeudDiapoCourante(n, EtatParcours.Solution);
        } else if (source.getText().equals("Non solution")) {
            graph.changerEtatNoeudDiapoCourante(n, EtatParcours.NonSolution);
        }
        graph.refresh();
    }

}
