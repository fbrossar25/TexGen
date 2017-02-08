package texgen.controleur;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import texgen.modele.Lien;
import texgen.vue.Graph;

public class ControleurGraph implements MouseListener, MouseMotionListener {

    private Graph      graph;
    private JPopupMenu contextMenuNode;
    private JPopupMenu contextMenuLink;
    private Point      previousPoint;
    private int        offsetX;
    private int        offsetY;

    public ControleurGraph(Graph graph) {
        this.graph = graph;
        initContextMenuLink();
        initContextMenuNode();
    }

    private void initContextMenuLink() {
        contextMenuLink = new JPopupMenu();
        ControleurGraphContextMenuLink ctrl = new ControleurGraphContextMenuLink(contextMenuLink, graph);
        JMenuItem supprimer = new JMenuItem("Supprimer");
        supprimer.addActionListener(ctrl);
        contextMenuLink.add(supprimer);
        graph.addMouseListener(ctrl);
    }

    private void initContextMenuNode() {
        contextMenuNode = new JPopupMenu();
        ControleurGraphContextMenuNode ctrl = new ControleurGraphContextMenuNode(contextMenuNode, graph);
        JMenuItem supprimer = new JMenuItem("Supprimer");
        supprimer.addActionListener(ctrl);
        contextMenuNode.add(supprimer);
        JMenuItem lier = new JMenuItem("Créer lien");
        lier.addActionListener(ctrl);
        contextMenuNode.add(lier);
        graph.addMouseListener(ctrl);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if (graph.updateTargetedLink(arg0.getPoint()) != null) {
            graph.updateSelectedLink(arg0.getPoint());
        } else {
            graph.resetSelectedLink();
        }
        graph.resetTargetedlink();

        if (graph.updateTargetedNode(arg0.getPoint()) != null) {
            graph.updateSelectedNode(arg0.getPoint());
            // On ne prend pas en charge les liens d'un noeud vers lui-même
            if (graph.getNodeCreatingLink() != null && graph.getNodeCreatingLink() != graph.getTargetedNode()) {
                graph.creerLien((char) ('a' + (graph.getNombreLiens() % 26)) + "", graph.getNodeCreatingLink(), graph.getTargetedNode());
                graph.setNodeCreatingLink(null);
            }
        } else {
            if (graph.getNodeCreatingLink() != null && SwingUtilities.isRightMouseButton(arg0)) {
                graph.setNodeCreatingLink(null);
            }
            graph.resetSelectedNode();
        }
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
