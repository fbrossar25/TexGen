package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import texgen.vue.PseudoCode;

/**
 * Classe gèrant les controleurs du Pseudo code
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 * 
 */
public class ControleurPseudoCode implements KeyListener, ActionListener {
    
	/** Pseudo code */
	private PseudoCode pseudoCode;

	/**
	 * Constructeur de la classe
	 * 
	 * @param pseudoCode
	 * 	Pseudo code
	 */
    public ControleurPseudoCode(PseudoCode pseudoCode) {
        this.pseudoCode = pseudoCode;
    }
    
    /**
     * Fonction gérant les actions effectuées lorsqu'une clé a été tapée
     * 
     * @param e
     * 	Clé liée à l'événement
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if ((e.getKeyChar() == (char) 10) && (pseudoCode.getTextArea().getLineCount() > pseudoCode.getRowsNumber())) {
            String newtxt = "";
            char[] cArray = pseudoCode.getTextArea().getText().toCharArray();
            for (int i = 0; i < (cArray.length - 1); i++) {
                newtxt += cArray[i];
            }
            pseudoCode.getTextArea().setText(newtxt);
            pseudoCode.getTextArea().setCaretPosition(newtxt.length());
        }

        int n = 1;
        for (char c : pseudoCode.getTextArea().getText().toCharArray()) {
            if (c == (char) 10) {
                n++;
            }
        }

        if (n < pseudoCode.getNombreLignes()) {
            pseudoCode.getMarqueursDiapo(pseudoCode.getDiapoCourante()).remove(pseudoCode.getNombreLignes());
            pseudoCode.repaint();
        }

        pseudoCode.setNombreDeLignes(n);
    }

    /**
     * Fonction gérant les actions effectuées lorsqu'une touche à été relâchée
     * 
     * @param e
     * 	clé liée à l'événement
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Fonction gérant les actions effectuées losqu'une touche a été pressée
     * 
     * @param e
     *	clé liée à l'événement
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Fonction gérant les actions effectuées sur le pseudo code
     * 
     * @param e 
     * 	Evénement lié à l'action
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
