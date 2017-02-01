package texgen.vue;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import texgen.controleur.ControleurToolBar;

/**
 * Classe gérant la vue de la barre d'outil de la fenetre principale
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
@SuppressWarnings("serial")
public class ToolBar extends JToolBar {

    /** Bouton diapo suivante */
    private JButton           suivant;

    /** Bouton diapo précédente */
    private JButton           precedent;

    /** Bouton créer une diapo */
    private JButton           creerDiapo;

    /** Bouton insérer une diapo */
    private JButton           insererDiapo;

    /** Fenetre princiaple */
    private FenetrePrincipale fen;

    /** Checkboxs de selection */
    private JCheckBox[]       selecteurs = { new JCheckBox("PseudoCode"), new JCheckBox("Tableau"), new JCheckBox("Graph") };

    /** Chemin relatif des icones dans le dossier resources */
    private final String      iconsDir   = "icones/";

    /** Le controleur de la barre d'outil */
    private ControleurToolBar ctrl;

    /** Les compteurs de diapo pour chaque panel */
    private JLabel[]          compteursDiapos;

    private JTextField        allerA;

    /**
     * Constructeur de la classe
     * 
     * @param fen
     *            la fenetre principale de l'application
     */
    public ToolBar(FenetrePrincipale fen) {
        this.fen = fen;

        ctrl = new ControleurToolBar(this);
        ClassLoader cl = getClass().getClassLoader();

        // Navigation entre les diapos
        precedent = new JButton(new ImageIcon(cl.getResource(iconsDir + "precedent.jpg")));
        precedent.setToolTipText("Diapo précédente");
        precedent.addActionListener(ctrl);
        add(precedent);

        suivant = new JButton(new ImageIcon(cl.getResource(iconsDir + "suivant.jpg")));
        suivant.setToolTipText("Diapo suivante");
        suivant.addActionListener(ctrl);
        add(suivant);

        compteursDiapos = new JLabel[selecteurs.length];
        for (int i = 0; i < selecteurs.length; i++) {
            add(selecteurs[i]);
            selecteurs[i].setSelected(true);
            compteursDiapos[i] = new JLabel("1/1");
            add(compteursDiapos[i]);
        }

        allerA = new JTextField("1");
        allerA.setColumns(3);
        Dimension d = new Dimension(allerA.getFontMetrics(allerA.getFont()).stringWidth("0000"), (int) (allerA.getFontMetrics(allerA.getFont()).getHeight() * 1.5));
        allerA.setMaximumSize(d);
        allerA.setMinimumSize(d);
        allerA.setPreferredSize(d);
        allerA.setToolTipText("Aller à la diapo");
        allerA.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int i = 1;
                    try {
                        i = Integer.parseInt(allerA.getText());
                    } catch (NumberFormatException exception) {
                        System.out.println("Saisie incorrect, veuillez saisir un nombre entier.");
                        allerA.setText("1");
                        return;
                    }
                    // System.out.println("i = " + i);
                    if ((i > 0) && (i <= fen.getNombreDiapos())) {
                        fen.allerDiapo(i);
                    } else {
                        System.out.println("La diapo " + i + " n'existe pas");
                        allerA.setText("1");
                    }
                }
            }
        });
        add(allerA);

        addSeparator();

        // Bouton raccourcis édition
        creerDiapo = new JButton(new ImageIcon(cl.getResource(iconsDir + "add.png")));
        creerDiapo.setToolTipText("Créer une diapo à la fin");
        creerDiapo.addActionListener(ctrl);
        add(creerDiapo);

        insererDiapo = new JButton(new ImageIcon(cl.getResource(iconsDir + "insert.png")));
        insererDiapo.setToolTipText("Insérer une diapo ici");
        insererDiapo.addActionListener(ctrl);
        add(insererDiapo);
    }

    /**
     * Retourne les fenetre principale de l'application
     * 
     * @return la fenetre principale
     */
    public FenetrePrincipale getFenetre() {
        return fen;
    }

    /**
     * Retourne le tableau des checkBoxs de selection
     * 
     * @return le tableau des checkBoxs
     */
    public JCheckBox[] getSelecteurs() {
        return selecteurs;
    }

    /**
     * Retourne l'etat (cochée/non cochée) de la checkBox pseudoCode
     * 
     * @return true si la checkBox pseudoCode est cochée, false sinon
     */
    public boolean isPseudoCodeSelected() {
        return selecteurs[0].isSelected();
    }

    /**
     * Retourne l'etat (cochée/non cochée) de la checkBox tableau
     * 
     * @return true si la checkBox tableau est cochée, false sinon
     */
    public boolean isTableSelected() {
        return selecteurs[1].isSelected();
    }

    /**
     * Retourne l'etat (cochée/non cochée) de la checkBox graph
     * 
     * @return true si la checkBox graph est cochée, false sinon
     */
    public boolean isGraphSelected() {
        return selecteurs[2].isSelected();
    }

    /**
     * Retourne le bouton diapo suivante
     * 
     * @return le bouton diapo suivante
     */
    public JButton getBoutonSuivant() {
        return suivant;
    }

    /**
     * Retourne le bouton diapo précédente
     * 
     * @return le bouton diapo précédente
     */
    public JButton getBoutonPrecedent() {
        return precedent;
    }

    /**
     * Retourne le bouton créer une diapo
     * 
     * @return le bouton créer une diapo
     */
    public JButton getBoutonCreer() {
        return creerDiapo;
    }

    /**
     * Retourne le bouton insérer une diapo
     * 
     * @return le bouton insérer une diapo
     */
    public JButton getBoutonInserer() {
        return insererDiapo;
    }

    /**
     * Retourne le tableau des compteurs de diapos
     * 
     * @return le tableau des compteurs de diapos
     */
    public JLabel[] getCompteursDiapo() {
        return compteursDiapos;
    }

    /**
     * Met à jour les compteurs de diapos
     * 
     * @param p
     *            le pseudoCode de l'application
     * @param t
     *            le tableau de l'application
     */
    public void updateCompteursDiapo() {
        PseudoCode p = fen.getPseudoCode();
        Tableau t = fen.getTableau();
        Graph g = fen.getGraph();
        compteursDiapos[0].setText(p.getDiapoCourante() + "/" + p.getNombreDiapos());
        compteursDiapos[1].setText(t.getDiapoCourante() + "/" + t.getNombreDiapos());
        compteursDiapos[2].setText(g.getDiapoCourante() + "/" + g.getNombreDiapos());
        allerA.setText("" + Math.max(p.getDiapoCourante(), Math.max(t.getDiapoCourante(), g.getDiapoCourante())));
    }

    /**
     * Retourne le textField allerA
     * 
     * @return le textField permettant de changer de diapo
     */
    public JTextField getAllerA() {
        return allerA;
    }

    /**
     * Fonction permettant de mettre à jour la vue de la toolBar
     */
    public void refresh() {
        updateCompteursDiapo();
    }
}
