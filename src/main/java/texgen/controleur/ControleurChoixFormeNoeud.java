package texgen.controleur;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButtonMenuItem;

import texgen.modele.Noeud;
import texgen.modele.Noeud.TypeForme;
import texgen.vue.Graph;

/**
 * Classe contrôleur du choix de la forme pour les noeuds
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class ControleurChoixFormeNoeud implements ActionListener {
    /** le graph */
    private Graph graph;

    /**
     * Constructeur de la classe
     * 
     * @param graph
     *            le graph
     */
    public ControleurChoixFormeNoeud(Graph graph) {
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
        if (source.getText().equals("Simple")) {
            n.changerForme(TypeForme.Simple);
        } else if (source.getText().equals("Double")) {
            n.changerForme(TypeForme.Double);
        }
        graph.refresh();
    }

}
