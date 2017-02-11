package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import texgen.vue.Graph;

/**
 * Classe contrôleur du menu contextuel par défaut du graph
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class ControleurGraphContextMenu extends MouseAdapter implements ActionListener {
    /** le menu contextuel */
    private JPopupMenu popup;
    /** le graph */
    private Graph      graph;
    /** sauvegarde des coordonnées du clic droit */
    private int        x, y;

    /**
     * Constructeur de la classe
     * 
     * @param popup
     *            le menu contextuel
     * @param graph
     *            le graph
     */
    public ControleurGraphContextMenu(JPopupMenu popup, Graph graph) {
        this.popup = popup;
        this.graph = graph;
        x = 0;
        y = 0;
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
            if (graph.mouseTargetingLink(e.getPoint()) == null && graph.mouseTargetingNode(e.getPoint()) == null) {
                // On sauvegarde les coordonnées pour les utilisés su l'on souhaite créer un noeud
                x = e.getX();
                y = e.getY();
                popup.show(e.getComponent(), x, y);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        if (source.getText().equals("Créer un noeud")) {
            graph.creerNoeud(graph.getNombreNoeuds() + "", x, y);
            x = 0;
            y = 0;
        }
    }
}