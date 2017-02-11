package texgen.controleur;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

import texgen.modele.Lien;
import texgen.vue.Graph;

/**
 * Classe controleur du graph
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class ControleurGraph implements MouseListener, MouseMotionListener {
    /** le graph */
    private Graph      graph;
    /** le menu contextuel qui apparait sur un noeud */
    private JPopupMenu contextMenuNode;
    /** le menu contextuel qui apparait sur un lien */
    private JPopupMenu contextMenuLink;
    /** le menu contextuel qui apparait par défaut */
    private JPopupMenu contextMenuGraph;
    /** sauvegarde d'un point, utilisé pour le déplacecement des noeuds */
    private Point      previousPoint;
    /** sauvegarde d'un décalage en x, utilisé pour le déplacement des noeuds */
    private int        offsetX;
    /** sauvegarde d'un décalage en y, utilisé pour le déplacement des noeuds */
    private int        offsetY;

    /**
     * Constructeur de la classe
     * 
     * @param graph
     *            le graph contrôlé
     */
    public ControleurGraph(Graph graph) {
        this.graph = graph;
        initContextMenuLink();
        initContextMenuNode();
        initContextMenuGraph();
    }

    /**
     * Retourne le menu de sélection d'état pour les noeuds (si type = 0) ou les liens (si type = 1)
     * 
     * @param type
     *            le type du controleur (0 : noeud, 1 : lien, autre : renvois null)
     * @return le menu de sélection d'état
     */
    private JMenu getStateChoiceMenu(int type) {
        if (type < 0 || type > 1) {
            return null;
        }
        ActionListener ctrl = (type == 0) ? new ControleurChoixEtatNoeud(graph) : new ControleurChoixEtatLien(graph);
        ButtonGroup groupe = new ButtonGroup();
        JMenu choixEtat = new JMenu("Etat");
        JRadioButtonMenuItem inactif = new JRadioButtonMenuItem("Inactif");
        choixEtat.add(inactif);
        groupe.add(inactif);
        inactif.addActionListener(ctrl);
        JRadioButtonMenuItem parcourus = new JRadioButtonMenuItem("Parcourus");
        choixEtat.add(parcourus);
        groupe.add(parcourus);
        JRadioButtonMenuItem actif = new JRadioButtonMenuItem("Actif");
        choixEtat.add(actif);
        groupe.add(actif);
        JRadioButtonMenuItem solution = new JRadioButtonMenuItem("Solution");
        choixEtat.add(solution);
        groupe.add(solution);
        JRadioButtonMenuItem nonSolution = new JRadioButtonMenuItem("Non solution");
        choixEtat.add(nonSolution);
        groupe.add(nonSolution);
        for (int i = 0; i < choixEtat.getItemCount(); i++) {
            choixEtat.getItem(i).addActionListener(ctrl);
        }
        return choixEtat;
    }

    /**
     * Initialise le menu contextuel par défaut
     */
    private void initContextMenuGraph() {
        contextMenuGraph = new JPopupMenu();
        ControleurGraphContextMenu ctrl = new ControleurGraphContextMenu(contextMenuGraph, graph);
        JMenuItem creer = new JMenuItem("Créer un noeud");
        creer.addActionListener(ctrl);
        contextMenuGraph.add(creer);
        graph.addMouseListener(ctrl);
    }

    /**
     * Initialise le menu contextuel des liens
     */
    private void initContextMenuLink() {
        contextMenuLink = new JPopupMenu();
        contextMenuLink.add(getStateChoiceMenu(1));
        ControleurGraphContextMenuLink ctrl = new ControleurGraphContextMenuLink(contextMenuLink, graph);
        JMenuItem supprimer = new JMenuItem("Supprimer ce lien");
        supprimer.addActionListener(ctrl);
        contextMenuLink.add(supprimer);
        graph.addMouseListener(ctrl);
    }

    /**
     * Initialise le menu contextuel des noeuds
     */
    private void initContextMenuNode() {
        contextMenuNode = new JPopupMenu();
        contextMenuNode.add(getStateChoiceMenu(0));
        ControleurGraphContextMenuNode ctrl = new ControleurGraphContextMenuNode(contextMenuNode, graph);
        JMenuItem supprimer = new JMenuItem("Supprimer ce noeud");
        supprimer.addActionListener(ctrl);
        contextMenuNode.add(supprimer);
        JMenuItem lier = new JMenuItem("Créer lien");
        lier.addActionListener(ctrl);
        contextMenuNode.add(lier);
        graph.addMouseListener(ctrl);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        graph.setLastClick(arg0.getPoint());
        if (graph.updateTargetedLink(arg0.getPoint()) != null) {
            graph.updateSelectedLink(arg0.getPoint());
            graph.resetSelectedNode();
        } else {
            graph.resetSelectedLink();

            if (graph.updateTargetedNode(arg0.getPoint()) != null) {
                graph.updateSelectedNode(arg0.getPoint());
                graph.resetSelectedLink();
                // On ne prend pas en charge les liens d'un noeud vers lui-même
                if (graph.getNodeCreatingLink() != null && graph.getNodeCreatingLink() != graph.getTargetedNode()) {
                    graph.creerLien((char) ('a' + (graph.getNombreLiens() % 26)) + "", graph.getNodeCreatingLink(), graph.getTargetedNode());
                    graph.setNodeCreatingLink(null);
                }

            } else {
                graph.resetSelectedNode();

                if (graph.getNodeCreatingLink() != null && SwingUtilities.isRightMouseButton(arg0)) {
                    graph.setNodeCreatingLink(null);
                    graph.resetSelectedNode();
                }
            }
        }

        graph.resetTargetedlink();
        graph.resetTargetedNode();
        graph.refresh();
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        graph.setLastClick(arg0.getPoint());
        if (graph.updateTargetedNode(arg0.getPoint()) != null) {
            previousPoint = arg0.getPoint();
            offsetX = graph.getTargetedNode().getX() - previousPoint.x;
            offsetY = graph.getTargetedNode().getY() - previousPoint.y;
            if (arg0.isPopupTrigger()) {
                System.out.println("Salut");
                contextMenuNode.show(arg0.getComponent(), arg0.getX(), arg0.getY());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        graph.resetTargetedNode();
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        if (graph.getTargetedNode() != null) {
            graph.getTargetedNode().setLocation((arg0.getX() + offsetX), (arg0.getY() + offsetY));
            graph.getTargetedNode().updatePosition();

            for (Lien l : graph.getLiens()) {
                l.updateLocation();
            }
            graph.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        if (graph.getNodeCreatingLink() != null) {
            graph.repaint();
        }
    }
}
