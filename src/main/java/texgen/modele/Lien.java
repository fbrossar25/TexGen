package texgen.modele;

import java.awt.Point;

import javax.swing.JTextField;

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
}
