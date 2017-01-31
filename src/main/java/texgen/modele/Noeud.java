package texgen.modele;

import java.awt.Point;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Noeud extends JTextField {
    private int rayon = 50;

    public Noeud(String label) {
        super(label);
    }

    public void deplacer(int dx, int dy) {
        setLocation(getX() + dx, getY() + dy);
    }

    public Point getCentre() {
        Point location = getLocation();
        return new Point(location.x + getWidth() / 2, location.y + getHeight() / 2);
    }

    public int getRayon() {
        return rayon;
    }

    public boolean contientPoint(Point p) {
        Point centre = getCentre();
        int r = rayon / 2;
        return (p.x >= (centre.x - r) && p.x <= (centre.x + r) && p.y >= (centre.y - r) && p.y <= (centre.y + r));
    }
}
