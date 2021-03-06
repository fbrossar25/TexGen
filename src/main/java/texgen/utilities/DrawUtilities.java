package texgen.utilities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;

import texgen.modele.Lien;
import texgen.modele.Noeud;
import texgen.vue.Graph;

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
        if (g == null) {
            return;
        }
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
        if (g == null) {
            return;
        }
        x = x - (r / 2);
        y = y - (r / 2);
        g.fillOval(x, y, r, r);
    }

    /**
     * Dessine un cercle centré au point p de rayon r dans l'élément graphique g
     * 
     * @param g
     *            l'élément graphique
     * @param p
     *            le centre du cercle
     * @param r
     *            le rayon du cercle
     */
    public static void fillCenteredCircle(Graphics g, Point p, int r) {
        fillCenteredCircle(g, p.x, p.y, r);
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
        if (g == null || p == null) {
            return;
        }
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
        if (g == null || n == null) {
            return;
        }
        Point centre = n.getCentre();
        int r = n.getRayon();
        g.drawRect(centre.x - r / 2, centre.y - r / 2, r, r);
    }

    /**
     * Dessine un lien entre deux noeuds.
     * 
     * @param g
     *            l'élément graphique
     * @param l
     *            le lien
     * @param fleche
     *            dessiner une fleche ?
     */
    public static void drawLink(Graphics g, Lien l, boolean fleche) {
        if (g == null || l == null || l.getPointDepart() == null || l.getPointArrive() == null) {
            return;
        }
        // Dessin de la ligne
        g.drawLine(l.getPointDepart().x, l.getPointDepart().y, l.getPointArrive().x, l.getPointArrive().y);

        // Dessin de la flèche
        if (fleche) {
            Polygon p = new Polygon();
            p.addPoint(l.getPointArrive().x, l.getPointArrive().y);

            Point destination = l.getPointArrive();
            int taille = 12;
            double angle = l.getAngle();
            int x = destination.x - (int) (Math.sin(angle - (Math.PI / 3.0)) * taille);
            int y = destination.y - (int) (Math.cos(angle - (Math.PI / 3.0)) * taille);
            p.addPoint(x, y);

            x = destination.x - (int) (Math.sin(angle - ((2.0 * Math.PI) / 3.0)) * taille);
            y = destination.y - (int) (Math.cos(angle - ((2.0 * Math.PI) / 3.0)) * taille);
            p.addPoint(x, y);

            g.fillPolygon(p);
        }
    }

    /**
     * Dessine un lien entre un Noeud et un point donnée (position de la souris par exemple)
     * 
     * @param g
     *            l'element graphique
     * @param n
     *            le noeud
     * @param pos
     *            le point
     * @param fleche
     *            dessiner une fleche ?
     */
    public static void drawLink(Graphics g, Noeud n, Point pos, boolean fleche) {
        if (g == null || n == null || pos == null) {
            return;
        }
        Point depart = n.getCentre();
        // Dessin de la ligne
        g.drawLine(depart.x, depart.y, pos.x, pos.y);

        // Dessin de la flèche
        if (fleche) {
            Polygon p = new Polygon();
            p.addPoint(pos.x, pos.y);

            int taille = 12;
            double longueur = Math.sqrt(Math.pow(pos.x - depart.x, 2) + Math.pow(pos.y - depart.y, 2));
            double angle = Math.acos((depart.x - pos.x) / longueur);
            if ((depart.y - pos.y) >= 0) {
                angle = (Math.PI * 2.0) - angle;
            }
            int x = pos.x - (int) (Math.sin(angle - (Math.PI / 3.0)) * taille);
            int y = pos.y - (int) (Math.cos(angle - (Math.PI / 3.0)) * taille);
            p.addPoint(x, y);

            x = pos.x - (int) (Math.sin(angle - ((2.0 * Math.PI) / 3.0)) * taille);
            y = pos.y - (int) (Math.cos(angle - ((2.0 * Math.PI) / 3.0)) * taille);
            p.addPoint(x, y);

            g.fillPolygon(p);
        }
    }

    /**
     * Dessine l'ellipse indiquant que le lien l est selectionné
     * 
     * @param g
     *            l'élement graphique
     * @param graph
     *            le graph
     * @param l
     *            le lien
     * @param taille
     *            la taille de l'ellipse de sélection (hauteur)
     */
    public static void drawLinkSelection(Graphics g, Graph graph, Lien l, int taille) {
        Graphics2D g2d = (Graphics2D) g;
        double theta = l.getAngle();

        Point p = l.getPointArrive();
        // Calcul du point de la fleche --> à modifié
        int x = p.x - (int) (Math.sin(theta - (Math.PI * 2.0)) * taille / 2);
        int y = p.y - (int) (Math.cos(theta - (Math.PI * 2.0)) * taille / 2);

        Ellipse2D el = new Ellipse2D.Double(x, y, l.getLongueur() - l.getDepart().getRayon(), taille);
        g2d.rotate(-theta, x, y);
        g2d.draw(el);
        g2d.rotate(theta, x, y);
    }
}
