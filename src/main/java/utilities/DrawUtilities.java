package utilities;

import java.awt.Graphics;

/**
 * Classe gérant Quelque chose
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 * 
 */
public class DrawUtilities {
	/**
	 * ji sais po
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param r
	 */
    public static void drawCenteredCircle(Graphics g, int x, int y, int r) {
        x = x - (r / 2);
        y = y - (r / 2);
        g.fillOval(x, y, r, r);
    }
}
