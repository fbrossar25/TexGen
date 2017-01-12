package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import texgen.vue.PseudoCode;

/**
 * Classe controleur du Pseudo code
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class ControleurPseudoCode implements KeyListener, ActionListener {

    /** Pseudo code */
    private PseudoCode pseudoCode;

    /**
     * Constructeur de la classe
     * 
     * @param pseudoCode
     *            Pseudo code
     */
    public ControleurPseudoCode(PseudoCode pseudoCode) {
        this.pseudoCode = pseudoCode;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Permet de bloquer l'écriture au nombre de ligne max définis (donné par getRowsNumber())
        if ((e.getKeyChar() == (char) 10) && (pseudoCode.getTextArea().getLineCount() > pseudoCode.getRowsNumber())) {
            String newtxt = "";
            char[] cArray = pseudoCode.getTextArea().getText().toCharArray();
            for (int i = 0; i < (cArray.length - 1); i++) {
                newtxt += cArray[i];
            }
            pseudoCode.getTextArea().setText(newtxt);
            pseudoCode.getTextArea().setCaretPosition(newtxt.length());
        }

        // Compte le nombre de ligne
        int n = 1;
        for (char c : pseudoCode.getTextArea().getText().toCharArray()) {
            if (c == (char) 10) {
                n++;
            }
        }

        // Supprime les marqueurs dont les lignes ont été effacé
        if (n < pseudoCode.getNombreLignes()) {
            pseudoCode.getMarqueursDiapo(pseudoCode.getDiapoCourante()).remove(pseudoCode.getNombreLignes());
            pseudoCode.repaint();
        }

        pseudoCode.setNombreDeLignes(n);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
