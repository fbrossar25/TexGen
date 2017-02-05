package texgen.controleur;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import texgen.modele.Lien;
import texgen.vue.Graph;

public class ControleurGraph implements MouseListener, MouseMotionListener {

    private Graph      graph;
    private JPopupMenu contextMenu;
    private Point      previousPoint;
    private int        offsetX;
    private int        offsetY;

    public ControleurGraph(Graph graph) {
        this.graph = graph;
        initContextMenu();
    }

    private void initContextMenu() {
        contextMenu = new JPopupMenu();
        ControleurGraphContextMenu ctrl = new ControleurGraphContextMenu(contextMenu, graph);
        JMenuItem supprimer = new JMenuItem("Supprimer");
        supprimer.addActionListener(ctrl);
        contextMenu.add(supprimer);
        JMenuItem lier = new JMenuItem("Créer lien");
        lier.addActionListener(ctrl);
        contextMenu.add(lier);
        graph.addMouseListener(ctrl);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if (graph.updateTargetedNode(arg0.getPoint()) != null) {
            graph.updateSelectedNode(arg0.getPoint());
            if (graph.getNodeCreatingLink() != null) {
                graph.creerLien((char) ('a' + (graph.getNombreLiens() % 26)) + "", graph.getNodeCreatingLink(), graph.getTargetedNode());
                graph.setNodeCreatingLink(null);
            }
        } else {
            graph.resetSelectedNode();
        }
        graph.resetTargetedNode();
        graph.refresh();
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        if (graph.updateTargetedNode(arg0.getPoint()) != null) {
            // System.out.println("draging node " + noeuds.get(targetedNode).getText());
            previousPoint = arg0.getPoint();
            offsetX = graph.getTargetedNode().getX() - previousPoint.x;
            offsetY = graph.getTargetedNode().getY() - previousPoint.y;
            if (arg0.isPopupTrigger()) {
                System.out.println("Salut");
                contextMenu.show(arg0.getComponent(), arg0.getX(), arg0.getY());
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
