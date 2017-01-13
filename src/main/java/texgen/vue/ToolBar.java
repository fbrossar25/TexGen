package texgen.vue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
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

        addSeparator();

        // Bouton raccourcis édition
        creerDiapo = new JButton(new ImageIcon(cl.getResource(iconsDir + "add.png")));
        creerDiapo.setToolTipText("Créer une diapo");
        creerDiapo.addActionListener(ctrl);
        add(creerDiapo);
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
    public void updateCompteursDiapo(PseudoCode p, Tableau t) {
        compteursDiapos[0].setText(p.getDiapoCourante() + "/" + p.getNombreDiapos());
        compteursDiapos[1].setText(t.getDiapoCourante() + "/" + t.getNombreDiapos());
    }
}
