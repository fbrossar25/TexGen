package texgen.modele;

import java.awt.Point;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Noeud extends JTextField {
    private int   rayon = 50;
    private Point position;

    public Noeud(String label) {
        this(label, new Point(0, 0));
    }

    public Noeud(String label, Point p) {
        super(label);
        position = p;
    }

    public Point getPosition() {
        return position;
    }

    public void updatePosition() {
        position.setLocation(getX(), getY());
    }

    public void replacer() {
        setLocation(position);
        // System.out.println("node replaced at (" + getX() + "," + getY() + ")");
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
