package texgen.modele;

import java.awt.Point;

import javax.swing.JTextField;

/**
 * Classe représentant un noeud
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
@SuppressWarnings("serial")
public class Noeud extends JTextField {
    /** le rayon du noeud */
    private int   rayon = 50;
    /** la position du noeud */
    private Point position;

    /**
     * Constructeur de la classe avec comme position par défaut (0,0)
     * 
     * @param label
     *            le label du noeud
     */
    public Noeud(String label) {
        this(label, new Point(0, 0));
    }

    /**
     * Constructueur de la classe avec une position et un label donnés
     * 
     * @param label
     *            le label
     * @param p
     *            la position
     */
    public Noeud(String label, Point p) {
        super(label);
        position = p;
    }

    /**
     * Retourne la position du noeud
     * 
     * @return la position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Met à jour la position du noeud
     */
    public void updatePosition() {
        position.setLocation(getX(), getY());
    }

    /**
     * Replace le noeud avec la position sauvegarder (appel setLocation(position))
     */
    public void replacer() {
        setLocation(position);
        // System.out.println("node replaced at (" + getX() + "," + getY() + ")");
    }

    /**
     * Retourne le centre du noeud
     * 
     * @return le centre du noeud
     */
    public Point getCentre() {
        Point location = getLocation();
        return new Point(location.x + getWidth() / 2, location.y + getHeight() / 2);
    }

    /**
     * retourne le rayon du noeud
     * 
     * @return le rayon du noeud
     */
    public int getRayon() {
        return rayon;
    }

    /**
     * Retourne si la point p est dans le noeud
     * 
     * @param p
     *            le point
     * @return true si p est contenus dans le noeud, false sinon
     */
    public boolean contientPoint(Point p) {
        Point centre = getCentre();
        int r = rayon / 2;
        return (p.x >= (centre.x - r) && p.x <= (centre.x + r) && p.y >= (centre.y - r) && p.y <= (centre.y + r));
    }
}
