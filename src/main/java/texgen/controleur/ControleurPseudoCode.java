package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import texgen.vue.PseudoCode;

public class ControleurPseudoCode implements KeyListener, ActionListener {
    private PseudoCode pseudoCode;

    public ControleurPseudoCode(PseudoCode pseudoCode) {
        this.pseudoCode = pseudoCode;
    }

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
