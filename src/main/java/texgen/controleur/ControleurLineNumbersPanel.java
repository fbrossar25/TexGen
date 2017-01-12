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
 * Classe gèrant les controleurs du Panel correspondant aux numéros de ligne du Pseudo code
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 * 
 */

public class ControleurLineNumbersPanel implements MouseListener {
    
	/** Panel correspondant aux numéros de ligne du Pseudo code */
	private LineNumbersPanel lineNumbersPanel;

	/**
	 * Constructeur de la classe
	 * 
	 * @param lineNumbersPanel
	 * 	Panel correspondant aux numéros de ligne du Pseudo code
	 * 
	 */
    public ControleurLineNumbersPanel(LineNumbersPanel lineNumbersPanel) {
        this.lineNumbersPanel = lineNumbersPanel;
    }

    /**
     * Fonction gérant les actions effectuées avec un clique de la souris
     * 
     * @param e
     * 	Evénement lié à la souris
     * 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int line = Integer.parseInt(((JLabel) (e.getSource())).getText());
        // System.out.println("Label " + line + " clicked");
        lineNumbersPanel.getPseudoCode().marquage(lineNumbersPanel.getPseudoCode().getDiapoCourante(), line);
        lineNumbersPanel.getPseudoCode().repaint();
    }
    
    /**
     * Fonction gérant les actions effectuées lorsque la souris entre dans un composant
     * 
     * @param e
     * 	Evénement lié à la souris
     * 
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel source = (JLabel) e.getSource();
        if (Integer.parseInt(source.getText()) <= lineNumbersPanel.getPseudoCode().getNombreLignes()) {
            Point pos = lineNumbersPanel.getLabelDrawPoint((JLabel) (e.getSource()));
            Graphics g = lineNumbersPanel.getGraphics();
            g.setColor(Color.GRAY);
            DrawUtilities.drawCenteredCircle(g, pos.x, pos.y, 12);
        }
    }

    /**
     * Fonction gérant les actions effectuées lorsque la souris quitte un composant
     * 
     * @param e
     * 	Evénement lié à la souris
     * 
     */
    @Override
    public void mouseExited(MouseEvent e) {
        lineNumbersPanel.repaint();
    }
    
    /**
     * Fonction gérant les actions effectuées lorsqu'un bouton de la souris a été appuyé sur un composant
     *
     * @param e
     * 	Evénement lié à la souris
     * 
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    /**
     * Fonction gérant les actions effectuées lorsqu'un bouton de la souris a été relâché sur un composant
     * 
     * @param e
     * 	Evénement lié à la souris
     * 
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
