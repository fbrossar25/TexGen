package texgen.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import texgen.controleur.ControleurLineNumbersPanel;
import texgen.utilities.DrawUtilities;
import texgen.utilities.SpringUtilities;

/**
 * Classe gérant la vue du panel des numéros de ligne du pseudo code
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
@SuppressWarnings("serial")
public class LineNumbersPanel extends JPanel {

    /** Nombre de ligne max du pseudoCode */
    private final int         ROWS;

    /** Pseudo code */
    private PseudoCode        pseudoCode;

    /** numéros des lignes */
    private ArrayList<JLabel> labels;

    /** Décalage en hauteur des lignes */
    private final int         LABEL_HEIGHT_OFFSET = 1;

    /**
     * Constructeur de la classe
     * 
     * @param pseudoCode
     *            Pseudo code
     */
    LineNumbersPanel(PseudoCode pseudoCode) {
        super();
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        ROWS = pseudoCode.getRowsNumber();
        setFont(pseudoCode.getFont());

        labels = new ArrayList<>();
        this.pseudoCode = pseudoCode;

        for (int i = 1; i <= ROWS; i++) {
            addLabel();
        }

        manageSpring();

        // On fixe la taille du panel pour l'aligner sur le pseudoCode
        Dimension dim = new Dimension(computeWidth(), ROWS * pseudoCode.getLineHeight());
        setPreferredSize(dim);
        setMaximumSize(dim);
        setMinimumSize(dim);
    }

    /**
     * Fonction gèrant le layout du panel
     */
    private void manageSpring() {
        SpringUtilities.makeGrid(this, labels.size(), 1, 0, LABEL_HEIGHT_OFFSET, 0, 0);
    }

    /**
     * Fonction donnant le point où dessiner le marqueurs pour le labnel donné
     * 
     * @param label
     *            le label
     * @return le point
     */
    public Point getLabelDrawPoint(JLabel label) {
        int x = label.getX() + ((computeWidth() / 4) * 3);
        int y = label.getY() + (label.getHeight() / 2);
        return new Point(x, y);
    }

    /**
     * Fonction permettant la création d'un nouveau label
     * 
     * @param text
     *            numéro de la nouvelle ligne
     * @return le label créé
     */
    private JLabel createNewLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(pseudoCode.getFont());
        label.addMouseListener(new ControleurLineNumbersPanel(this));
        return label;
    }

    /**
     * Fonction permettant l'ajout d'une nouvelle ligne
     */
    public void addLabel() {
        JLabel label = createNewLabel("" + (labels.size() + 1));
        labels.add(label);
        add(label);
        manageSpring();
    }

    /**
     * Fonction permettant la suppression de la dernière
     */
    public void removeLabel() {
        remove(labels.get(labels.size() - 1));
        labels.remove(labels.size() - 1);
        manageSpring();
        revalidate();
    }

    /**
     * Fonction renvoyant la largeur nécessaire à l'affichage du panel
     * 
     * @return la largeur du panel
     */
    public int computeWidth() {
        return ((int) (Math.log10(ROWS) + 1) * getFontMetrics(pseudoCode.getFont()).charWidth('0') * 2) + 10;
    }

    /**
     * Fonction donnant le pseudo code
     * 
     * @return le pseudo code
     */
    public PseudoCode getPseudoCode() {
        return pseudoCode;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);

        // Dessin des marqueurs
        g.setColor(Color.RED);
        TreeSet<Integer> marqueurs = pseudoCode.getMarqueursDiapoCourante();
        if (marqueurs == null) {
            System.out.println("Liste de marqueurs diapo " + pseudoCode.getDiapoCourante() + " null");
            return;
        }
        for (JLabel label : labels) {
            int ligne = Integer.parseInt(label.getText());
            if (marqueurs.contains(ligne)) {
                Point pos = getLabelDrawPoint(label);
                DrawUtilities.fillCenteredCircle(g, pos.x, pos.y, 10);
            }
        }
    }
}
