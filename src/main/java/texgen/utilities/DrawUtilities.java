package texgen.utilities;

import java.awt.Graphics;

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
        g.fillOval(x, y, r, r);
    }
}
