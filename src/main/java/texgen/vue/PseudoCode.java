package texgen.vue;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import texgen.controleur.ControleurPseudoCode;

@SuppressWarnings("serial")
public class PseudoCode extends JPanel {

    private JTextArea                   textArea;
    private ArrayList<TreeSet<Integer>> marqueurs;
    private Font                        font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    LineNumbersPanel                    lineNumbersPanel;
    private int                         rowsNumber;
    private final double                linespacing;
    private int                         diapoCourante;
    private int                         linenumber;

    PseudoCode() {
        this(50);
    }

    PseudoCode(int rows) {
        super();
        rowsNumber = rows;
        linenumber = 1;
        diapoCourante = 1;
        ControleurPseudoCode controleur = new ControleurPseudoCode(this);

        // Getting the line spacing
        JTextPane textPane = new JTextPane();
        textPane.setFont(font);
        textPane.setText("foo");
        MutableAttributeSet set = new SimpleAttributeSet(textPane.getParagraphAttributes());
        linespacing = StyleConstants.getLineSpacing(set);

        setLayout(new BorderLayout());
        marqueurs = new ArrayList<>();
        marqueurs.add(new TreeSet<Integer>());
        textArea = new JTextArea();
        textArea.setRows(rows);
        textArea.addKeyListener(controleur);
        textArea.setFont(font);
        textArea.setText("Saisissez votre pseudo code ici.");
        add(textArea, BorderLayout.CENTER);

        lineNumbersPanel = new LineNumbersPanel(this);

        add(lineNumbersPanel, BorderLayout.WEST);
    }

    public void setNombreDeLignes(int n) {
        if (n > 0) {
            linenumber = n;
        }
    }

    public int getDiapoCourante() {
        return diapoCourante;
    }

    public int getRowsNumber() {
        return rowsNumber;
    }

    public TreeSet<Integer> getMarqueursDiapoCourante() {
        return getMarqueursDiapo(diapoCourante);
    }

    public TreeSet<Integer> getMarqueursDiapo(int diapo) {
        if ((diapo < 1) || (diapo > getNombreDiapos())) {
            System.out.println("Je suis le null pointer qui t'emmerde pour la diapo " + diapo);
            return null;
        }
        return marqueurs.get(diapo - 1);
    }

    public void marquage(int diapo, int ligne) {
        if ((diapo <= 0) || (ligne <= 0) || (diapo > getNombreDiapos()) || (ligne > getNombreLignes())) {
            return;
        }
        diapo--;
        if (marqueurs.get(diapo).contains(ligne)) {
            marqueurs.get(diapo).remove(ligne);
        } else {
            marqueurs.get(diapo).add(ligne);
        }
        repaint();
    }

    public String getLigne(int i) {
        if ((i <= 0) || (i > getNombreLignes())) {
            return null;
        }
        String[] lignes = textArea.getText().split("\n");
        if ((i - 1) < lignes.length) {
            return lignes[i - 1];
        } else {
            return null;
        }
    }

    public void refreshNombreDeLignes() {
        int n = 1;
        for (char c : textArea.getText().toCharArray()) {
            if (c == (char) 10) {
                n++;
            }
        }
        linenumber = n;
    }

    public ArrayList<String> getLignes() {
        ArrayList<String> list = new ArrayList<>();
        for (String s : textArea.getText().split("\n")) {
            list.add(s);
        }
        return list;
    }

    public int getNombreLignes() {
        return linenumber;
    }

    public void ajouterDiapo() {
        if (!marqueurs.add(new TreeSet<Integer>())) {
            System.out.println("Erreur lors de l'ajout d'une diapo (PseudoCode)");
            return;
        }
        int prochainMarqueur;
        if (getDiapo(diapoCourante).isEmpty()) {
            prochainMarqueur = 1;
        } else {
            prochainMarqueur = new Integer(getDiapo(diapoCourante).last().intValue()) + ((diapoCourante < getNombreLignes()) ? 1 : 0);
        }
        marquage(diapoCourante + 1, prochainMarqueur);
        // afficherMarqueurs();
    }

    public void afficherMarqueurs() {
        int n = 1;
        for (TreeSet<Integer> l : marqueurs) {
            System.out.print("Diapo " + n + ":\n\t");
            if (l.isEmpty()) {
                System.out.println("Vide");
            } else {
                for (int i : l) {
                    System.out.print(i + " ");
                }
                System.out.println();
            }
            n++;
        }
    }

    public void supprimerDiapo(int i) {
        if ((i >= 0) && (i < marqueurs.size())) {
            marqueurs.remove(i);
        }
    }

    public boolean estMarquee(int diapo, int ligne) {
        if ((diapo <= 0) || (ligne <= 0) || (diapo > marqueurs.size()) || (ligne > getNombreLignes())) {
            return false;
        } else {
            return marqueurs.get(diapo - 1).contains(ligne);
        }
    }

    public TreeSet<Integer> getDiapo(int i) {
        return ((i > 0) && (i <= marqueurs.size())) ? marqueurs.get(i - 1) : null;
    }

    @Override
    public Font getFont() {
        return font;
    }

    public int getNombreDiapos() {
        return marqueurs.size();
    }

    public int getLineNumbersColumnWidth() {
        return (int) (Math.log10(rowsNumber) + 1) * getFontMetrics(font).charWidth('0');
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public int getLineHeight() {
        return (int) (getFontMetrics(font).getHeight() * (1 + linespacing));
    }

    public void diapoSuivante() {
        if (diapoCourante < getNombreDiapos()) {
            diapoCourante++;
            lineNumbersPanel.repaint();
        }
    }

    public void diapoPrecedente() {
        if (diapoCourante > 1) {
            diapoCourante--;
            lineNumbersPanel.repaint();
        }
    }
}
