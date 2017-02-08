package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import texgen.modele.Noeud;
import texgen.vue.Graph;

public class ControleurGraphContextMenuNode extends MouseAdapter implements ActionListener {
    private JPopupMenu popup;
    private Graph      graph;
    private Noeud      n;

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
            n = graph.mouseTargetingNode(e.getPoint());
            if (n != null) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        if (source.getText().equals("Supprimer")) {
            graph.supprimerNoeud(n);
            n = null;
        } else if (source.getText().equals("Créer lien")) {
            graph.setNodeCreatingLink(n);
        }
    }
}