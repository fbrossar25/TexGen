package texgen.utilities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import texgen.modele.Lien;
import texgen.modele.Noeud;

/**
 * Classe utilitaire pour faciliter le dessin
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class DrawUtilities {
    /**
     * Dessine un cercle centré en (x,y) de rayon r dans l'élément graphique g
     * 
     * @param g
     *            l'élément graphique
     * @param x
     *            la coordonnée en x du cercle
     * @param y
     *            la coordonnée en y du cercle
     * @param r
     *            le rayon du cercle
     */
    public static void drawCenteredCircle(Graphics g, int x, int y, int r) {
        x = x - (r / 2);
        y = y - (r / 2);
        g.drawOval(x, y, r, r);
    }

    /**
     * Dessine un cercle centré en (x,y) de rayon r dans l'élément graphique g
     * 
     * @param g
     *            l'élément graphique
     * @param x
     *            la coordonnée en x du cercle
     * @param y
     *            la coordonnée en y du cercle
     * @param r
     *            le rayon du cercle
     */
    public static void fillCenteredCircle(Graphics g, int x, int y, int r) {
        x = x - (r / 2);
        y = y - (r / 2);
        g.fillOval(x, y, r, r);
    }

    /**
     * Dessine un cercle de rayon r centré sur le point p dans l'élément graphique g
     * 
     * @param g
     *            l'élément graphique
     * @param p
     *            le centre du cercle
     * @param r
     *            le rayon du cercle
     */
    public static void drawCenteredCircle(Graphics g, Point p, int r) {
        drawCenteredCircle(g, p.x, p.y, r);
    }

    /**
     * Dessine le carré indiquant que le noeud n est selectionné
     * 
     * @param g
     *            l'élément graphique
     * @param n
     *            le noeud
     */
    public static void drawNodeSelectionSquare(Graphics g, Noeud n) {
        Point centre = n.getCentre();
        int r = n.getRayon();
        g.drawRect(centre.x - r / 2, centre.y - r / 2, r, r);
    }

    /**
     * Dessine un lien (symbolisé par une flèche) entre deux noeuds.
     * 
     * @param g
     *            l'élément graphique
     * @param l
     *            le lien
     */
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
}
