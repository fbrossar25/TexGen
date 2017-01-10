package utilities;

import java.awt.Graphics;

public class DrawUtilities {
    public static void drawCenteredCircle(Graphics g, int x, int y, int r) {
        x = x - (r / 2);
        y = y - (r / 2);
        g.fillOval(x, y, r, r);
    }
}
