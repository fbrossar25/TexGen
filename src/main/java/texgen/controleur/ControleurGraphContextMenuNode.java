package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import texgen.modele.Noeud;
import texgen.vue.Graph;

/**
 * Classe contrôleur du menu contextuel apparaissant sur les noeud du graph
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class ControleurGraphContextMenuNode extends MouseAdapter implements ActionListener {
    /** le menu contextuel */
    private JPopupMenu popup;
    /** le graph */
    private Graph      graph;
    /** le noeud cible */
    private Noeud      n;

    /**
     * Constructeur de la classe
     * 
     * @param popup
     *            le menu contextuel
     * @param graph
     *            le graph
     */
    public ControleurGraphContextMenuNode(JPopupMenu popup, Graph graph) {
        this.popup = popup;
        this.graph = graph;
        n = null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        popupEventHandler(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        popupEventHandler(e);
    }

    private void popupEventHandler(MouseEvent e) {
        if (e.isPopupTrigger()) {
            // On sauvegarde le noeud cible pour y avoir accès si suppression ou création de lien
            n = graph.mouseTargetingNode(e.getPoint());
            if (n != null) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        if (source.getText().equals("Supprimer ce noeud")) {
            graph.supprimerNoeud(n);
            n = null;
        } else if (source.getText().equals("Créer lien")) {
            graph.setNodeCreatingLink(n);
        }
    }
}