package texgen.utilities;

import java.awt.Graphics;
import java.awt.Point;

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
}
