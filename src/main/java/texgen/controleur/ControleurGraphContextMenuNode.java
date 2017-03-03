package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
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

    /**
     * Définis l'élément sélectionné en fonction de l'état du noeud
     */
    private void setSelectedState() {
        JMenu choixEtat = (JMenu) popup.getComponent(0);
        switch (graph.getEtatCourantNoeud(n)) {
            case Inactif: {
                choixEtat.getItem(0).setSelected(true);
            }
                break;
            case Parcourus: {
                choixEtat.getItem(1).setSelected(true);
            }
                break;
            case Actif: {
                choixEtat.getItem(2).setSelected(true);
            }
                break;
            case Solution: {
                choixEtat.getItem(3).setSelected(true);
            }
                break;

            case NonSolution: {
                choixEtat.getItem(4).setSelected(true);
            }
                break;
            default: {
                for (int i = 0; i < choixEtat.getItemCount(); i++)
                    choixEtat.getItem(i).setSelected(false);
            }
        }
    }

    /**
     * Définis l'élément sélectionné en fonction de la forme du noeud
     */
    private void setSelectedShape() {
        JMenu choixForme = (JMenu) popup.getComponent(1);
        switch (n.getForme()) {
            case Simple: {
                choixForme.getItem(0).setSelected(true);
            }
                break;
            case Double: {
                choixForme.getItem(1).setSelected(true);
            }
                break;
            case Initial: {
                choixForme.getItem(2).setSelected(true);
            }
            default: {
                for (int i = 0; i < choixForme.getItemCount(); i++)
                    choixForme.getItem(i).setSelected(false);
            }
        }
    }

    /**
     * Gère les événements
     *
     * @param e
     *            l'évenement
     */
    private void popupEventHandler(MouseEvent e) {
        if (e.isPopupTrigger()) {
            // On sauvegarde le noeud cible pour y avoir accès si suppression ou création de lien
            n = graph.mouseTargetingNode(e.getPoint());
            if (n != null) {
                setSelectedState();
                setSelectedShape();
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