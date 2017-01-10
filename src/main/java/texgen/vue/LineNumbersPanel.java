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
import utilities.DrawUtilities;
import utilities.SpringUtilities;

@SuppressWarnings("serial")
public class LineNumbersPanel extends JPanel {
    private final int         ROWS;
    private PseudoCode        pseudoCode;
    private ArrayList<JLabel> labels;
    private final int         LABEL_HEIGHT_OFFSET = 1;

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

        setPreferredSize(new Dimension(computeWidth(), ROWS * pseudoCode.getLineHeight()));
        setMaximumSize(new Dimension(computeWidth(), ROWS * pseudoCode.getLineHeight()));
        setMinimumSize(new Dimension(computeWidth(), ROWS * pseudoCode.getLineHeight()));
    }

    private void manageSpring() {
        SpringUtilities.makeGrid(this, labels.size(), 1, 0, LABEL_HEIGHT_OFFSET, 0, 0);
    }

    public Point getLabelDrawPoint(JLabel label) {
        int x = label.getX() + ((computeWidth() / 4) * 3);
        int y = label.getY() + (label.getHeight() / 2);
        return new Point(x, y);
    }

    private JLabel createNewLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(pseudoCode.getFont());
        label.addMouseListener(new ControleurLineNumbersPanel(this));
        return label;
    }

    public void addLabel() {
        JLabel label = createNewLabel("" + (labels.size() + 1));
        labels.add(label);
        add(label);
        manageSpring();
    }

    public void removeLabel() {
        remove(labels.get(labels.size() - 1));
        labels.remove(labels.size() - 1);
        manageSpring();
        revalidate();
    }

    public int computeWidth() {
        return ((int) (Math.log10(ROWS) + 1) * getFontMetrics(pseudoCode.getFont()).charWidth('0') * 2) + 10;
    }

    public PseudoCode getPseudoCode() {
        return pseudoCode;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);

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
                DrawUtilities.drawCenteredCircle(g, pos.x, pos.y, 10);
            }
        }
    }
}
