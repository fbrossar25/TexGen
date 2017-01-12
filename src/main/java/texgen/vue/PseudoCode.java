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

/**
 * Classe gérant la vue du pseudo code
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 * 
 */
@SuppressWarnings("serial")
public class PseudoCode extends JPanel {

	/** Zone de texte */
    private JTextArea                   textArea;
    
    /** Marqueurs du texte */
    private ArrayList<TreeSet<Integer>> marqueurs;
    
    /** Style du texte */
    private Font                        font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    
    /** Panel gérant l'affichage des numéros de ligne du pseudo code */
    LineNumbersPanel                    lineNumbersPanel;
    
    /** Nombre de ligne max */
    private int                         rowsNumber;
    
    /** ??????? */
    private final double                linespacing;
    
    /** Numéro de la diapo courante */
    private int                         diapoCourante;
    
    /** Nombre de ligne */
    private int                         linenumber;

    PseudoCode() {
        this(50);
    }

    /**
     * Constructeur de la classe
     * 
     * @param rows
     * Nombre de ligne max
     */
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

    /**
     * Fonction permettant la modification du nombre de ligne
     * 
     * @param n
     * nouveau nombre de ligne
     */
    public void setNombreDeLignes(int n) {
        if (n > 0) {
            linenumber = n;
        }
    }

    /**
     * Fonction donnant le numéro de la diapo courante
     * 
     * @return numéro de la diapo courante
     */
    public int getDiapoCourante() {
        return diapoCourante;
    }

    /**
     * Fonction donnant le nombre de ligne max
     * 
     * @return nombre de ligne max
     */
    public int getRowsNumber() {
        return rowsNumber;
    }

    /**
     * Fonction donnant les marqueurs de la diapo suivante
     * 
     * @return marqueurs de la diapo suivante
     */
    public TreeSet<Integer> getMarqueursDiapoCourante() {
        return getMarqueursDiapo(diapoCourante);
    }

    /**
     * Fonction donnant les marqueurs de la diapo spécifiée
     * 
     * @param diapo
     * Numéro de la diapo dont on veux les marqueurs
     * 
     * @return marqueurs de la diapo spécifiée
     */
    public TreeSet<Integer> getMarqueursDiapo(int diapo) {
        if ((diapo < 1) || (diapo > getNombreDiapos())) {
            return null;
        }
        return marqueurs.get(diapo - 1);
    }

    /**
     * Fonction j'en sais rien
     * 
     * @param diapo
     * Numéro de la diapo 
     * @param ligne
     * Numéro de la ligne
     */
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

    /**
     * Fontion donnant le texte à une ligne donnée
     * 
     * @param i
     * Numéro de la ligne
     * 
     * @return texte présent à la ligne donnée
     */
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

    /**
     * ?????
     */
    public void refreshNombreDeLignes() {
        int n = 1;
        for (char c : textArea.getText().toCharArray()) {
            if (c == (char) 10) {
                n++;
            }
        }
        linenumber = n;
    }

    /**
     * Fonction donnant tout le texte présent dans la zone de texte
     * 
     * @return texte entier
     */
    public ArrayList<String> getLignes() {
        ArrayList<String> list = new ArrayList<>();
        for (String s : textArea.getText().split("\n")) {
            list.add(s);
        }
        return list;
    }

    /**
     * Fonction donnant le nombre de ligne
     * 
     * @return nombre de ligne
     */
    public int getNombreLignes() {
        refreshNombreDeLignes();
        return linenumber;
    }

    /**
     * Fonction permettant l'ajout d'une diapo
     */
    public void ajouterDiapo() {
        if (!marqueurs.add(new TreeSet<Integer>())) {
            System.out.println("Erreur lors de l'ajout d'une diapo (PseudoCode)");
            return;
        }
        int prochainMarqueur;
        if (getDiapo(diapoCourante).isEmpty() || (getDiapo(diapoCourante).last() == getNombreLignes())) {
            prochainMarqueur = 1;
        } else {
            prochainMarqueur = new Integer(getDiapo(diapoCourante).last().intValue()) + ((diapoCourante < getNombreLignes()) ? 1 : 0);
            // System.out.println("next mark at " + prochainMarqueur);
        }
        marquage(diapoCourante + 1, prochainMarqueur);
        // afficherMarqueurs();
    }

    /**
     * Fonction permettant l'affichage des marqueurs
     */
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

    /**
     * Fonction permettant la suppression d'une diapo donnée
     * 
     * @param i
     * Numéro de la diapo à supprimer
     */
    public void supprimerDiapo(int i) {
        if ((i >= 0) && (i < marqueurs.size())) {
            marqueurs.remove(i);
        }
    }

    /**
     * Fonction permettant de voir si une ligne est marquée sur une diapo donnée
     * 
     * @param diapo
     * Numéro de la diapo
     * @param ligne
     * Numéro de la ligne
     * 
     * @return présence d'un marquage ou non présence
     */
    public boolean estMarquee(int diapo, int ligne) {
        if ((diapo <= 0) || (ligne <= 0) || (diapo > marqueurs.size()) || (ligne > getNombreLignes())) {
            return false;
        } else {
            return marqueurs.get(diapo - 1).contains(ligne);
        }
    }

    /**
     * Fonction donnant une diapo dont on donne le numéro
     * 
     * @param i
     * Numéro de la diapo recherchée
     * 
     * @return diapo correspondante
     */
    public TreeSet<Integer> getDiapo(int i) {
        return ((i > 0) && (i <= marqueurs.size())) ? marqueurs.get(i - 1) : null;
    }

    /**
     * Fonction donnant le style du texte présent dans la zone de texte
     */
    @Override
    public Font getFont() {
        return font;
    }

    /**
     * Fonction donnant le nombre de diapo
     * 
     * @return nombre de diapo
     */
    public int getNombreDiapos() {
        return marqueurs.size();
    }

    /**
     * Fonction donnant la taille d'une colonne
     * 
     * @return taille d'une colonne
     */
    public int getLineNumbersColumnWidth() {
        return (int) (Math.log10(rowsNumber) + 1) * getFontMetrics(font).charWidth('0');
    }

    /**
     * Fonction donnant la zone de texte
     * 
     * @return zone de texte
     */
    public JTextArea getTextArea() {
        return textArea;
    }

    /**
     * Fonction donnant la hauteur d'une ligne
     * 
     * @return hauteur d'une ligne
     */
    public int getLineHeight() {
        return (int) (getFontMetrics(font).getHeight() * (1 + linespacing));
    }

    /**
     * Fontion permettant de passer à la diapo suivante
     */
    public void diapoSuivante() {
        if (diapoCourante < getNombreDiapos()) {
            diapoCourante++;
            lineNumbersPanel.repaint();
        }
    }

    /**
     * FOnction permettant de passer à la diapo précedante
     */
    public void diapoPrecedente() {
        if (diapoCourante > 1) {
            diapoCourante--;
            lineNumbersPanel.repaint();
        }
    }
}
