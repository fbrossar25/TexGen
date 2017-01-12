package texgen.controleur;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import texgen.vue.LineNumbersPanel;
import utilities.DrawUtilities;

/**
 * Classe controleur du panel des numéros de lignes du pseudoCode
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */

public class ControleurLineNumbersPanel implements MouseListener {

    /** Panel des numéros de ligne du Pseudo code */
    private LineNumbersPanel lineNumbersPanel;

    /**
     * Constructeur de la classe
     * 
     * @param lineNumbersPanel
     *            Panel des numéros de ligne du Pseudo code
     */
    public ControleurLineNumbersPanel(LineNumbersPanel lineNumbersPanel) {
        this.lineNumbersPanel = lineNumbersPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // On place le marqueur sur la ligne line
        int line = Integer.parseInt(((JLabel) (e.getSource())).getText());
        // System.out.println("Label " + line + " clicked");
        lineNumbersPanel.getPseudoCode().marquage(lineNumbersPanel.getPseudoCode().getDiapoCourante(), line);
        lineNumbersPanel.getPseudoCode().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // On dessine un cercle gris pour visualiser où le marqueur seras sur le panel des numéros de ligne
        JLabel source = (JLabel) e.getSource();
        if (Integer.parseInt(source.getText()) <= lineNumbersPanel.getPseudoCode().getNombreLignes()) {
            Point pos = lineNumbersPanel.getLabelDrawPoint((JLabel) (e.getSource()));
            Graphics g = lineNumbersPanel.getGraphics();
            g.setColor(Color.GRAY);
            DrawUtilities.drawCenteredCircle(g, pos.x, pos.y, 12);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        lineNumbersPanel.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
