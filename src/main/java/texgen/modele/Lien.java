package texgen.modele;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

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

    public Point2D getCentre() {
        Point centreDepart = depart.getCentre();
        Point centreArrive = arrive.getCentre();
        return new Point2D.Double((centreDepart.x + centreArrive.x) / 2, (centreDepart.y + centreArrive.y) / 2);
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

    public Point applyRotation(Point p, double theta, Ellipse2D el) {

        // FIXME
        double height = el.getHeight();
        double x1 = p.x - Math.abs(el.getMinX() - p.x);
        double y1 = p.y - Math.abs(el.getMinY() - p.y);
        double PI2 = 2.0 * Math.PI;

        // APPLY ROTATION
        double x = (x1 * Math.cos(theta) + y1 * Math.sin(theta));
        double y = (y1 * Math.cos(theta) - x1 * Math.sin(theta));

        x += Math.abs(el.getMinX() - p.x);
        y += Math.abs(el.getMinY() - p.y);

        return new Point((int) x, (int) y);
    }

    public boolean contientPoint(Graph graph, int taille, Point p) {
        // FIXME
        Ellipse2D el = getSelectionEllipse(graph, taille);
        Point p1 = applyRotation(p, getAngle() * 0.0, el);
        //
        // DrawUtilities.fillCenteredCircle(graph.getGraphics(), p1, 10);
        // try {
        // TimeUnit.MILLISECONDS.sleep(250);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // System.out.print("p : (" + p.x + "," + p.y + ") -> ");
        // System.out.println("p1 : (" + p1.x + "," + p1.y + ") theta :" + Math.toDegrees(getAngle()) + " h:" + el.getHeight() / 2);
        //
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
