package texgen.vue;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import texgen.controleur.ControleurTableau;

/**
 * Classe gérant la vue du tableau
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
@SuppressWarnings("serial")
public class Tableau extends JPanel {

    /** Fenêtre principale du projet */
    private FenetrePrincipale            fenetrePrincipale;

    /** Tableau */
    private JTable                       tab;

    /** Nombre de colonne du tableau */
    private int                          col;

    /** Nombre de ligne du tableau */
    private int                          li;

    /** Hauteur de ligne par défaut */
    private final int                    DEFAULT_ROW_HEIGHT = 21;

    /** Liste des modeles du tableau */
    private ArrayList<DefaultTableModel> diapos;

    /** Numéro de la diapo courante */
    private int                          diapoCourante;

    /** Controleur du tableau */
    private ControleurTableau            ctrl;

    /**
     * Constructeur de la classe
     * 
     * @param fenetrePrincipale
     *            Fenêtre principale du projet
     */
    public Tableau(FenetrePrincipale fenetrePrincipale) {
        super();
        // On utilise le même controleur pour chaque élément du tableau pour n'avoir à en désactiver qu'un au besoin
        ctrl = new ControleurTableau(this);
        diapoCourante = 1;
        this.fenetrePrincipale = fenetrePrincipale;
        setLayout(new BorderLayout());
        li = 2;
        col = 4;
        diapos = new ArrayList<>();

        DefaultTableModel model = createNewVoidModel();
        diapos.add(model);
        model.addTableModelListener(ctrl);

        tab = new JTable();
        tab.setModel(getDiapo(diapoCourante));
        tab.setRowHeight(DEFAULT_ROW_HEIGHT);
        add(tab, BorderLayout.CENTER);
    }

    /**
     * Fonction permettant la création d'un nouveau modele "vide" (càd avec li*col case contenant chacune une chaine
     * vide)
     * 
     * @return le modele "vide" créer
     */
    public DefaultTableModel createNewVoidModel() {
        DefaultTableModel model = new DefaultTableModel();
        String[] defaultLine = new String[col];
        for (int i = 0; i < col; i++) {
            defaultLine[i] = "";
            model.addColumn("");
        }

        for (int j = 0; j < li; j++) {
            model.addRow(defaultLine);
        }

        return model;
    }

    /**
     * Fonction donnant la fenêtre principale
     * 
     * @return la fenêtre principale
     */
    public FenetrePrincipale getFenetre() {
        return fenetrePrincipale;
    }

    /**
     * Fonction donnant le rectangle correspondant à une cellule dont on donne les numéros de ligne et de colonne
     * 
     * @param row
     *            Numéro de ligne correspondant à la cellule
     * @param col
     *            Numéro de colonne correspondant à la cellule
     * @return le rectangle correspondant à la cellule recherchée
     */
    public Rectangle getCellRect(int row, int col) {
        return tab.getCellRect(row, col, true);
    }

    /**
     * Fonction permettant l'ajout d'une ligne dans le tableau
     */
    public void ajouterLigne() {
        String[] defaultLine = new String[col];
        for (int i = 0; i < col; i++) {
            defaultLine[i] = "";
        }
        disableAllModelListener();
        for (DefaultTableModel m : diapos) {
            m.addRow(defaultLine);
        }
        enableAllModelListener();
        li++;

    }

    /**
     * Fonction permmetant la suppression de la derniere ligne du tableau
     */
    public void supprimerLigne() {
        if (li > 2) {
            disableAllModelListener();

            for (DefaultTableModel m : diapos) {
                m.removeRow(li - 1);
            }
            enableAllModelListener();
            li--;
        }
    }

    /**
     * Fonction permettant l'ajout d'une colonne dans le tableau
     */
    public void ajouterColonne() {
        getDiapo(diapoCourante).addColumn("");
        col++;
    }

    /**
     * Fonction permettant la suppression de la derniere colonne du tableau
     */
    public void supprimerColonne() {
        if (col > 1) {
            col--;
            getDiapo(diapoCourante).setColumnCount(col);
        }
    }

    /**
     * Fonction permettant d'obtenir le numéro de la diapo courante
     * 
     * @return le numéro de la diapo courante
     */
    public int getDiapoCourante() {
        return diapoCourante;
    }

    /**
     * Fonction permettant d'obtenir le nombre de diapo
     * 
     * @return le nombre de diapo
     */
    public int getNombreDiapos() {
        return diapos.size();
    }

    /**
     * Fonction permettant l'ajout d'une diapo
     */
    public void ajouterDiapo() {
        DefaultTableModel newModel = createNewVoidModel();
        // On copie le modele de la diapo précédente
        for (int i = 0; i < li; i++) {
            for (int j = 0; j < col; j++) {
                newModel.setValueAt(getDiapo(diapoCourante).getValueAt(i, j), i, j);
            }
        }
        if (!diapos.add(newModel)) {
            System.out.println("Erreur lors de l'ajout d'une diapo (Tableau)");
            return;
        } else {
            newModel.addTableModelListener(ctrl);
        }
    }

    /**
     * Fonction désactivant le controleur du tableau
     */
    public void disableAllModelListener() {
        ctrl.setActive(false);
    }

    /**
     * Fonction activant le controleur du tableau
     */
    public void enableAllModelListener() {
        ctrl.setActive(true);
    }

    /**
     * Fonction donnant le modele d'une diapo donnée
     * 
     * @param i
     *            Numéro de la diapo
     * @return le modele correspondant
     */
    public DefaultTableModel getDiapo(int i) {
        if ((i < 1) || (i > diapos.size())) {
            return null;
        } else {
            return diapos.get(i - 1);
        }
    }

    /**
     * Fonction permettant de passer à la diapo suivante
     */
    public void diapoSuivante() {
        if (diapoCourante < getNombreDiapos()) {
            diapoCourante++;
            tab.setModel(getDiapo(diapoCourante));
        }
    }

    /**
     * Fonction permettant de passer à la diapo précedante
     */
    public void diapoPrecedente() {
        if (diapoCourante > 1) {
            diapoCourante--;
            tab.setModel(getDiapo(diapoCourante));
        }
    }

    /**
     * Fonction permettant de supprimer une diapo donnée
     * 
     * @param i
     *            Numéro de la diapo à supprimer
     */
    public void supprimerDiapo(int i) {
        if ((i > 0) && (i <= getNombreDiapos())) {
            diapos.remove(i);
        }
    }

    /**
     * Fonction donnant le tableau
     * 
     * @return le tableau
     */
    public JTable getTab() {
        return tab;
    }

    /**
     * Fonction repercutant la modification d'un titre d'une colonne (première ligne du tableau) sur toutes les diapos
     * 
     * @param colonne
     *            Numéro de la colonne modifiée
     * @param source
     *            Numéro de la diapo source de la modification
     */
    public void repercuterModifTitre(int colonne, int source) {
        // Désactivation du controleur pour éviter les appels récursif
        // car le controleur réagit à setValueAt
        disableAllModelListener();
        for (DefaultTableModel m : diapos) {
            m.setValueAt(getDiapo(source).getValueAt(0, colonne), 0, colonne);
        }
        enableAllModelListener();
    }

    /**
     * Fonction repercutant la modification d'une cellule du tableau sur cette même cellule pour les diapos suivante
     * 
     * @param diapoSource
     *            Numéro de la diapo où se situe la modification
     * @param ligne
     *            Numéro de la ligne modifiée
     * @param colonne
     *            Numéroi de la colonne modifiée
     */
    public void repercuterModif(int diapoSource, int ligne, int colonne) {
        // Désactivation du controleur pour éviter les appels récursif
        // car le controleur réagit à setValueAt
        disableAllModelListener();
        for (int i = diapoSource + 1; i <= getNombreDiapos(); i++) {
            getDiapo(i).setValueAt(getDiapo(diapoSource).getValueAt(ligne, colonne), ligne, colonne);
        }
        enableAllModelListener();
    }
}
