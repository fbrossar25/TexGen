package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import texgen.modele.Lien;
import texgen.vue.Graph;

/**
 * Classe contrôleur du menu contextuel apparaissant sur les liens du graph
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class ControleurGraphContextMenuLink extends MouseAdapter implements ActionListener {
    /** le menu contextuel */
    private JPopupMenu popup;
    /** le graph */
    private Graph      graph;
    /** le lien cible */
    private Lien       l;

    /**
     * Constructeur de la classe
     * 
     * @param popup
     *            le menu contextuel
     * @param graph
     *            le graph
     */
    public ControleurGraphContextMenuLink(JPopupMenu popup, Graph graph) {
        this.popup = popup;
        this.graph = graph;
        l = null;
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
            // On sauvegarde le lien pour y avoir accès si suppression
            l = graph.mouseTargetingLink(e.getPoint());
            if (l != null) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        if (source.getText().equals("Supprimer ce lien")) {
            graph.supprimerLien(l);
            l = null;
        }
    }
}