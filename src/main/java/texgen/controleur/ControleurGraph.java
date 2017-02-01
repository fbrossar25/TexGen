package texgen.controleur;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import texgen.modele.Lien;
import texgen.vue.Graph;

public class ControleurGraph implements MouseListener, MouseMotionListener {

    private Graph graph;
    private Point previousPoint;
    private int   offsetX;
    private int   offsetY;

    public ControleurGraph(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if (graph.updateTargetedNode(arg0.getPoint()) != null) {
            graph.updateSelectedNode(arg0.getPoint());
        } else {
            graph.resetSelectedNode();
        }
        graph.resetTargetedNode();
        graph.repaint();
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
        // TODO Auto-generated method stub

    }
}
