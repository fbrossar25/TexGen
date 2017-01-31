package texgen.vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.JPanel;

import texgen.controleur.ControleurGraph;
import texgen.modele.Lien;
import texgen.modele.Noeud;
import texgen.utilities.DrawUtilities;

@SuppressWarnings("serial")
public class Graph extends JPanel {
    private FenetrePrincipale fenetre;
    private ArrayList<Noeud>  noeuds;
    private ArrayList<Lien>   liens;
    private int               targetedNode = -1;
    private ControleurGraph   ctrl;
    private int               selectedNode;

    public Graph(FenetrePrincipale fenetre) {
        super();
        this.fenetre = fenetre;
        noeuds = new ArrayList<>();
        liens = new ArrayList<>();
        ctrl = new ControleurGraph(this);
        addMouseListener(ctrl);
        addMouseMotionListener(ctrl);
        // setFocusable(true);
    }

    public void resetTargetedNode() {
        targetedNode = -1;
    }

    public Noeud getTargetedNode() {
        if (targetedNode < 0) {
            return null;
        }
        return noeuds.get(targetedNode);
    }

    public Noeud getSelectedNode() {
        if (selectedNode < 0) {
            return null;
        }
        return noeuds.get(selectedNode);
    }

    public int updateSelectedNode(Point p) {
        selectedNode = mouseTargetingNode(p);
        return selectedNode;
    }

    public void resetSelectedNode() {
        selectedNode = -1;
    }

    public int updateTargetedNode(Point p) {
        targetedNode = mouseTargetingNode(p);
        return targetedNode;
    }

    public ArrayList<Noeud> getNoeuds() {
        return noeuds;
    }

    public ArrayList<Lien> getLiens() {
        return liens;
    }

    public Noeud getNoeud(int i) {
        if (i < 0 || i >= noeuds.size()) {
            return null;
        }
        return noeuds.get(i);
    }

    public Noeud getNoeud(String label) {
        for (Noeud n : noeuds) {
            if (n.getText().equals(label)) {
                return n;
            }
        }
        return null;
    }

    public int getNombreLiens() {
        return liens.size();
    }

    public int getNombreNoeuds() {
        return noeuds.size();
    }

    public Lien getLien(String label) {
        for (Lien l : liens) {
            if (l.getText().equals(label)) {
                return l;
            }
        }
        return null;
    }

    public Lien creerLien(String label, Noeud depart, Noeud arrive) {
        if (depart == null || arrive == null) {
            return null;
        }
        Lien l = new Lien(label, depart, arrive);
        liens.add(l);
        add(l);
        l.updateLocation();
        repaint();
        return l;
    }

    public Noeud creerNoeud(String label) {
        Noeud n = new Noeud(label);
        noeuds.add(n);
        add(n);
        repaint();
        return n;
    }

    public void supprimerNoeudSelectionne() {
        if (selectedNode >= 0) {
            remove(getSelectedNode());
            noeuds.remove(getSelectedNode());
        }
        resetSelectedNode();
        repaint();
    }

    public FenetrePrincipale getFenetre() {
        return fenetre;
    }

    public static void drawLink(Graphics g, Lien l) {
        // Dessin de la ligne
        g.drawLine(l.getPointDepart().x, l.getPointDepart().y, l.getPointArrive().x, l.getPointArrive().y);

        // Dessin de la flèche
        Polygon p = new Polygon();
        p.addPoint(l.getPointArrive().x, l.getPointArrive().y);

        Point destination = l.getPointArrive();
        int taille = 12;
        double angle = Math.acos(l.getDx() / l.getLongueur());
        if (l.getDy() >= 0) {
            angle = (Math.PI * 2.0) - angle;
        }
        int x = destination.x - (int) (Math.sin(angle - (Math.PI / 3.0)) * taille);
        int y = destination.y - (int) (Math.cos(angle - (Math.PI / 3.0)) * taille);
        p.addPoint(x, y);

        x = destination.x - (int) (Math.sin(angle - ((2.0 * Math.PI) / 3.0)) * taille);
        y = destination.y - (int) (Math.cos(angle - ((2.0 * Math.PI) / 3.0)) * taille);
        p.addPoint(x, y);

        g.fillPolygon(p);
    }

    public int mouseTargetingNode(Point p) {
        for (int i = 0; i < noeuds.size(); i++) {
            if (noeuds.get(i).contientPoint(p)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        int i = 0;
        for (Noeud n : noeuds) {
            DrawUtilities.drawCenteredCircle(g, n.getCentre(), n.getRayon());
            if (i == selectedNode) {
                g.setColor(Color.GRAY);
                DrawUtilities.drawNodeSelectionSquare(g, n);
                g.setColor(Color.RED);
            }
            i++;
        }
        g.setColor(Color.GRAY);
        for (Lien l : liens) {
            drawLink(g, l);
        }
    }
}
