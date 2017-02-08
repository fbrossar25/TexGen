package texgen.modele;

import java.awt.Point;
import java.awt.geom.Ellipse2D;

import javax.swing.JTextField;

import texgen.vue.Graph;

@SuppressWarnings("serial")
public class Lien extends JTextField {
    private Noeud depart;
    private Noeud arrive;

    public Lien(String label, Noeud depart, Noeud arrive) {
        super(label);
        this.depart = depart;
        this.arrive = arrive;
    }

    public Noeud getDepart() {
        return depart;
    }

    public Noeud getArrive() {
        return arrive;
    }

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

    public double getLongueur() {
        Point centreDepart = depart.getCentre();
        Point centreArrive = arrive.getCentre();
        return Math.sqrt(Math.pow(centreArrive.x - centreDepart.x, 2) + Math.pow(centreArrive.y - centreDepart.y, 2));
    }

    public int getDy() {
        return depart.getCentre().y - arrive.getCentre().y;
    }

    public int getDx() {
        return depart.getCentre().x - arrive.getCentre().x;
    }

    public void updateLocation() {
        Point centreDepart = depart.getCentre();
        Point centreArrive = arrive.getCentre();
        int x = ((centreDepart.x + centreArrive.x)) / 2 - (getWidth() / 2);
        int y = ((centreDepart.y + centreArrive.y)) / 2 - (getHeight() / 2);
        setLocation(x, y);
    }

    public boolean estAssocieA(Noeud n) {
        return (depart == n || arrive == n);
    }

    /**
     * Retourne un point ayant pour coordonnées le Point p ayant subit un rotation d'angle theta (radians) autour du point (centerX, centerY)
     * 
     * @param p
     *            le point subissant la rotation
     * @param theta
     *            l'angle en radians
     * @param centerX
     *            la coordonnée en X du centre de rotation
     * @param centerY
     *            la coordonnée en Y du centre de rotation
     * @return un nouveau point ayant subis la rotation
     */
    public Point applyRotation(Point p, double theta, int centerX, int centerY) {
        int x1 = p.x - centerX;
        int y1 = p.y - centerY;

        // APPLY ROTATION
        x1 = (int) (x1 * Math.cos(theta) - y1 * Math.sin(theta));
        y1 = (int) (x1 * Math.sin(theta) + y1 * Math.cos(theta));

        // TRANSLATE BACK
        int x = x1 + centerX;
        int y = y1 + centerY;
        return new Point(x, y);
    }

    public boolean contientPoint(Graph graph, int taille, Point p) {
        Ellipse2D el = getSelectionEllipse(graph, taille);
        Point p1 = applyRotation(p, getAngle() * 0, (int) el.getMinX(), (int) el.getMinY());
        // DrawUtilities.fillCenteredCircle(graph.getGraphics(), p1, 10);
        // try {
        // TimeUnit.MILLISECONDS.sleep(250);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        return el.contains(p1);
    }

    public double getAngle() {
        double angle = Math.acos(getDx() / getLongueur());
        if (getDy() >= 0.0) {
            angle = (Math.PI * 2.0) - angle;
        }
        return angle;
    }

    public Ellipse2D getSelectionEllipse(Graph graph, int taille) {
        Point p = getPointArrive();
        double theta = getAngle();

        int x = p.x - (int) (Math.sin(theta - (Math.PI * 2.0)) * taille / 2);
        int y = p.y - (int) (Math.cos(theta - (Math.PI * 2.0)) * taille / 2);

        Ellipse2D el = new Ellipse2D.Double(x, y, getLongueur() - getDepart().getRayon(), taille);
        return el;
    }

    @Override
    public String toString() {
        return "from '" + depart.getText() + "' to '" + arrive.getText() + "' with '" + getText() + "'";
    }
}
