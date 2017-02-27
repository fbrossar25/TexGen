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
 */
@SuppressWarnings("serial")
public class PseudoCode extends JPanel {

    /** Zone de texte */
    private JTextArea                   textArea;

    /** Marqueurs du texte */
    private ArrayList<TreeSet<Integer>> marqueurs;

    /** Police du texte */
    private Font                        font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

    /** Panel gérant l'affichage des numéros de ligne du pseudo code */
    LineNumbersPanel                    lineNumbersPanel;

    /** Nombre de ligne max */
    private int                         rowsNumber;

    /** Taille de l'interligne */
    private final double                linespacing;

    /** Numéro de la diapo courante */
    private int                         diapoCourante;

    /** Nombre de ligne non-vide actuel */
    private int                         linenumber;
    /** La fenetre principale de l'application */
    private FenetrePrincipale           fen;

    /**
     * Constructeur par défaut avec 50 lignes max
     * 
     * @param fen
     *            la fenetre principale
     */
    PseudoCode(FenetrePrincipale fen) {
        this(fen, 50);
    }

    /**
     * Constructeur de la classe
     * 
     * @param fen
     *            la fenetre principale
     * @param rows
     *            Nombre de ligne max
     */
    PseudoCode(FenetrePrincipale fen, int rows) {
        super();
        this.fen = fen;
        rowsNumber = rows;
        linenumber = 1;
        diapoCourante = 1;
        ControleurPseudoCode controleur = new ControleurPseudoCode(this);

        // Calcul de l'interligne
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
     * Retourne la fenêtre principale de l'application
     * 
     * @return la fenêtre principale de l'application
     */
    public FenetrePrincipale getFenetre() {
        return fen;
    }

    /**
     * Rafraichis la vue du pseudoCode
     */
    public void refresh() {
        revalidate();
        repaint();
    }

    /**
     * Fonction permettant la modification du nombre de ligne
     * 
     * @param n
     *            nouveau nombre de ligne
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
     * Fonction donnant les marqueurs de la diapo courante
     * 
     * @return marqueurs de la diapo courante
     */
    public TreeSet<Integer> getMarqueursDiapoCourante() {
        return getMarqueursDiapo(diapoCourante);
    }

    /**
     * Fonction donnant les marqueurs de la diapo spécifiée
     * 
     * @param diapo
     *            Numéro de la diapo
     * @return marqueurs de la diapo
     */
    public TreeSet<Integer> getMarqueursDiapo(int diapo) {
        if ((diapo < 1) || (diapo > getNombreDiapos())) {
            return null;
        }
        return marqueurs.get(diapo - 1);
    }

    /**
     * Fonction gèrant le marquage pour la ligne dans la diapo spécifée
     * 
     * @param diapo
     *            Numéro de la diapo
     * @param ligne
     *            Numéro de la ligne
     */
    public void marquage(int diapo, int ligne) {
        if ((diapo <= 0) || (ligne <= 0) || (diapo > getNombreDiapos()) || (ligne > getNombreLignes())) {
            return;
        }
        // On décrémente le numéro de la diapo pour obtenir l'index corespondant
        diapo--;
        // Si le marqueurs est déja présent, on l'efface, sinon on l'ajoute
        if (marqueurs.get(diapo).contains(ligne)) {
            marqueurs.get(diapo).remove(ligne);
        } else {
            marqueurs.get(diapo).add(ligne);
        }
        repaint();
    }

    /**
     * Fontion donnant le texte de la ligne donnée
     * 
     * @param i
     *            Numéro de la ligne
     * @return texte présent à la ligne donnée, null si la ligne est inexistante ou vide
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
     * Recalcul le nombre de lignes
     */
    public void refreshNombreDeLignes() {
        int n = 1;
        for (char c : textArea.getText().toCharArray()) {
            // On compte le nombre de retour charriot
            if (c == (char) 10) {
                n++;
            }
        }
        linenumber = n;
    }

    /**
     * Fonction donnant tout le texte sous forme de liste
     * 
     * @return liste des lignes
     */
    public ArrayList<String> getLignes() {
        ArrayList<String> list = new ArrayList<>();
        for (String s : textArea.getText().split("\n")) {
            list.add(s);
        }
        return list;
    }

    /**
     * Fonction donnant le nombre de ligne actuel
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
        // S'il n'y avait aucun marqueur, ou si le dernier marqueur était à la dernière ligne, on marque la première
        // ligne, sinon on marque la ligne suivant le dernier marqueur
        if (getDiapo(diapoCourante).isEmpty() || (getDiapo(diapoCourante).last() == getNombreLignes())) {
            prochainMarqueur = 1;
        } else {
            // On utilise new Integer et intValue pour ne pas modifier le dernier marqueur
            prochainMarqueur = new Integer(getDiapo(diapoCourante).last().intValue()) + 1;
            // System.out.println("next mark at " + prochainMarqueur);
        }
        marquage(diapoCourante + 1, prochainMarqueur);
        // afficherMarqueurs();
    }

    /**
     * Fonction permettant l'affichage des marqueurs sur la console
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
     *            Numéro de la diapo à supprimer
     */
    public void supprimerDiapo(int i) {
        if (getNombreDiapos() > 1 && (i >= 0) && (i < marqueurs.size())) {
            if (i == getNombreDiapos()) {
                diapoCourante--;
            }
            marqueurs.remove(i - 1);
        }
    }

    /**
     * Fonction permettant de voir si une ligne est marquée sur une diapo donnée
     * 
     * @param diapo
     *            Numéro de la diapo
     * @param ligne
     *            Numéro de la ligne
     * @return true si la ligne est marquée sur la diapo donnée, false sinon
     */
    public boolean estMarquee(int diapo, int ligne) {
        if ((diapo <= 0) || (ligne <= 0) || (diapo > marqueurs.size()) || (ligne > getNombreLignes())) {
            return false;
        } else {
            return marqueurs.get(diapo - 1).contains(ligne);
        }
    }

    /**
     * Fonction donnant la liste des marqueurs d'une diapo donnée
     * 
     * @param i
     *            Numéro de la diapo recherchée
     * @return diapo correspondante
     */
    public TreeSet<Integer> getDiapo(int i) {
        return ((i > 0) && (i <= marqueurs.size())) ? marqueurs.get(i - 1) : null;
    }

    @Override
    public Font getFont() {
        return font;
    }

    /**
     * Fonction donnant le nombre de diapo actuels
     * 
     * @return nombre de diapo
     */
    public int getNombreDiapos() {
        return marqueurs.size();
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
     * Fonction donnant la hauteur d'une ligne (interligne compris)
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
     * Fonction permettant de passer à la diapo précedante
     */
    public void diapoPrecedente() {
        if (diapoCourante > 1) {
            diapoCourante--;
            lineNumbersPanel.repaint();
        }
    }

    /**
     * Permet d'aller directement à une diapo donnée
     * 
     * @param i
     *            le numero de la diapo
     */
    public void setDiapoCourante(int i) {
        if ((i > 0) && (i <= getNombreDiapos())) {
            diapoCourante = i;
        }
    }

    /**
     * Fonction permettant d'inserer une diapo juste avant la diapo i
     * 
     * @param i
     *            Le numero de la diapo à insérer
     */
    public void insererDiapo(int i) {
        if (i < getNombreDiapos()) {// On copie l'état suivant s'il existe
            TreeSet<Integer> cpy = new TreeSet<>();
            for (Integer n : getDiapo(i)) {
                cpy.add(n);
            }
            marqueurs.add(i - 1, cpy);
        } else {
            ajouterDiapo();
        }
    }

    /**
     * Fonction permettant de remettre à son état initial le pseudoCode
     * 
     * @param nombreDiapos
     *            le nombre de diapos vide à créer
     */
    public void reset(int nombreDiapos) {
        linenumber = 1;
        diapoCourante = 1;
        marqueurs.clear();
        if (nombreDiapos > 1) {
            for (int i = 0; i < nombreDiapos; i++) {
                creerDiapoVide();
            }
        } else {
            creerDiapoVide();
        }
        textArea.setText("");
    }

    /**
     * Fonction permettant de créer une diapo sans marqueurs
     */
    public void creerDiapoVide() {
        marqueurs.add(new TreeSet<Integer>());
    }
}
