package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import texgen.modele.Lien;
import texgen.vue.Graph;

public class ControleurGraphContextMenuLink extends MouseAdapter implements ActionListener {
    private JPopupMenu popup;
    private Graph      graph;
    private Lien       l;

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
            l = graph.mouseTargetingLink(e.getPoint());
            if (l != null) {
                System.out.println("plop");
                popup.show(e.getComponent(), e.getX(), e.getY());
            } else {
                System.out.println("ballo");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        if (source.getText().equals("Supprimer")) {
            graph.supprimerLien(l);
            l = null;
        }
    }
}