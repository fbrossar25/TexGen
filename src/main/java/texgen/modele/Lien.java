package texgen.modele;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextField;

import texgen.utilities.DrawUtilities;
import texgen.vue.Graph;

/**
 * Classe représentant un lien entre deux noeuds
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
@SuppressWarnings("serial")
public class Lien extends JTextField {
    /** le noeud de départ */
    private Noeud depart;
    /** le noeud d'arrivé */
    private Noeud arrive;

    /**
     * Constructeur de la classe
     * 
     * @param label
     *            le label du lien
     * @param depart
     *            le noeud de départ
     * @param arrive
     *            le noeud d'arrivé
     */
    public Lien(String label, Noeud depart, Noeud arrive) {
        super(label);
        this.depart = depart;
        this.arrive = arrive;
    }

    /**
     * Retourne le noeud de départ
     * 
     * @return le noeud de départ
     */
    public Noeud getDepart() {
        return depart;
    }

    /**
     * retourne le noeud d'arrivé
     * 
     * @return le noeud d'arrivé
     */
    public Noeud getArrive() {
        return arrive;
    }

    /**
     * Retourne le point ou le lien touche le noeud de départ
     * 
     * @return le point de départ
     */
    public Point getPointDepart() {
        Point centreDepart = depart.getCentre();

        double ratio = getLongueur() / (depart.getRayon() / 2);

        if (ratio == 0.0) {
            return centreDepart;
        }

        int x = ((int) (getDx() / ratio) - centreDepart.x) * -1;
        int y = ((int) (getDy() / ratio) - centreDepart.y) * -1;

        return new Point(x, y);
    }

    /**
     * Retourne le point ou le lien touche le noeud d'arrivé
     * 
     * @return le point d'arrivé
     */
    public Point getPointArrive() {
        Point centreArrive = arrive.getCentre();
        double ratio = getLongueur() / (arrive.getRayon() / 2);

        if (ratio == 0.0) {
            return centreArrive;
        }

        int x = centreArrive.x + (int) (getDx() / ratio);
        int y = centreArrive.y + (int) (getDy() / ratio);

        return new Point(x, y);
    }

    /**
     * Retourne la longueur du lien
     * 
     * @return la longueur
     */
    public double getLongueur() {
        Point centreDepart = depart.getCentre();
        Point centreArrive = arrive.getCentre();
        return Math.sqrt(Math.pow(centreArrive.x - centreDepart.x, 2) + Math.pow(centreArrive.y - centreDepart.y, 2));
    }

    /**
     * Retourne la différence en y entre les deux noeuds
     * 
     * @return depart.y - arrive.y
     */
    public int getDy() {
        return depart.getCentre().y - arrive.getCentre().y;
    }

    /**
     * Retourne la différence en x des deux points
     * 
     * @return depart.x - arrive.x
     */
    public int getDx() {
        return depart.getCentre().x - arrive.getCentre().x;
    }

    /**
     * Retourne le centre du lien
     * 
     * @return une instance de Point2D.Double correspondant au centre
     */
    public Point2D getCentre() {
        Point centreDepart = depart.getCentre();
        Point centreArrive = arrive.getCentre();
        return new Point2D.Double((centreDepart.x + centreArrive.x) / 2, (centreDepart.y + centreArrive.y) / 2);
    }

    /**
     * Met à jour la position du label du lien en fonction de sa taille et de la position des noeuds
     */
    public void updateLocation() {
        Point centreDepart = depart.getCentre();
        Point centreArrive = arrive.getCentre();
        int x = ((centreDepart.x + centreArrive.x)) / 2 - (getWidth() / 2);
        int y = ((centreDepart.y + centreArrive.y)) / 2 - (getHeight() / 2);
        setLocation(x, y);
    }

    /**
     * Retourne le le noeud n est le point de départ ou d'arrivée du lien
     * 
     * @param n
     *            le noeud à tester
     * @return true si n est le noeud d'arrivée ou de départ, false sinon
     */
    public boolean estAssocieA(Noeud n) {
        return (depart == n || arrive == n);
    }

    /**
     * Applique un rotation du point p autour du point central centreRotation
     * 
     * @param p
     *            le point cible de la rotation
     * @param theta
     *            l'angle de rotation
     * @param centreRotation
     *            le centre de rotation
     * @return une nouvelle instance de Point correspondant à p avec la rotation effectuée
     */
    public Point applyRotation(Point p, double theta, Point2D centreRotation) {
        // FIXME bad rotation
        double x = p.x;
        double y = p.y;

        return new Point((int) x, (int) y);
    }

    /**
     * Retourne si le point p est contenu dans la zone de sélection du lien
     * 
     * @param graph
     *            le graph
     * @param hauteur
     *            la hauteur de la zone
     * @param p
     *            le point du clic
     * @return true si le clic est dans la zone, false sinon
     */
    public boolean contientPoint(Graph graph, int hauteur, Point p) {
        Ellipse2D el = getSelectionEllipse(graph, hauteur);
        Point p1 = applyRotation(p, getAngle(), new Point2D.Double(el.getMinX(), el.getMinY()));

        // fill circle arround the rotated mouse click point
        DrawUtilities.fillCenteredCircle(graph.getGraphics(), p1, 10);
        try {
            TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("p : (" + p.x + "," + p.y + ") -> ");
        System.out.println("(" + p1.x + "," + p1.y + ") theta :" + Math.toDegrees(getAngle()) + " h:" + el.getHeight() / 2 + " l:" + el.getWidth());

        return el.contains(p1);
    }

    /**
     * Retourne l'angle du lien
     * 
     * @return l'angle
     */
    public double getAngle() {
        double angle = Math.acos(getDx() / getLongueur());
        if (getDy() >= 0.0) {
            angle = (Math.PI * 2.0) - angle;
        }
        return angle;
    }

    /**
     * Retourne l'ellipse sans rotation qui fait office de zone de selection
     * 
     * @param graph
     *            le graph
     * @param hauteur
     *            la hauteur de l'ellipse
     * @return une instance de Ellipse2D.Double
     */
    public Ellipse2D getSelectionEllipse(Graph graph, int hauteur) {
        Point p = getPointArrive();
        double theta = getAngle();

        int x = p.x - (int) (Math.sin(theta - (Math.PI * 2.0)) * hauteur / 2);
        int y = p.y - (int) (Math.cos(theta - (Math.PI * 2.0)) * hauteur / 2);

        Ellipse2D el = new Ellipse2D.Double(x, y, getLongueur() - getDepart().getRayon(), hauteur);
        return el;
    }

    @Override
    public String toString() {
        return "from '" + depart.getText() + "' to '" + arrive.getText() + "' with '" + getText() + "'";
    }
}
