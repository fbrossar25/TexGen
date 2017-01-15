package texgen.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

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

    /** Couleur du marquage des case modifiée. */
    private Color                        markColor;

    /**
     * Constructeur de la classe
     * 
     * @param fenetrePrincipale
     *            Fenêtre principale du projet
     */
    public Tableau(FenetrePrincipale fenetrePrincipale) {
        super();
        markColor = new Color(0, 255, 0);
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
        tab.setDefaultRenderer(Object.class, new TableCellRenderer() {
            JLabel comp = new JLabel();
            String val  = new String();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                comp.setOpaque(true);
                comp.setForeground(Color.BLACK); // text color
                if (value != null) {
                    val = value.toString();
                    comp.setText(val);
                    if (estMarquee(row, column)) {
                        comp.setBackground(markColor);
                    } else {
                        comp.setBackground(Color.WHITE);
                    }
                }
                return comp;
            }
        });
        tab.setModel(getDiapo(diapoCourante));
        tab.setRowHeight(DEFAULT_ROW_HEIGHT);
        add(tab, BorderLayout.CENTER);
    }

    /**
     * Retourne la présence ou l'absence de marquage de la cellule (ligne,colonne) à la diapo donnée
     * 
     * @param diapo
     *            la diapo
     * @param ligne
     *            la ligne
     * @param colonne
     *            la colonne
     * @return true si la cellule est marquée, false sinon
     */
    public boolean estMarquee(int diapo, int ligne, int colonne) {
        // On ne marque pa la premiere ligne (les titres)
        if ((diapo < 1) || (diapo > getNombreDiapos()) || (ligne < 1) || (ligne > li) || (colonne < 0) || (colonne > col)) {
            return false;
        }

        Object value = getDiapo(diapo).getValueAt(ligne, colonne);
        if (diapo == 1) {
            return (value != null) && !value.equals("");
        } else {
            return (value != null) && !value.equals("") && !value.equals(getDiapo(diapo - 1).getValueAt(ligne, colonne));
        }
    }

    /**
     * Retourne la présence ou l'absence de marquage de la cellule (ligne,colonne) à la diapo courante
     * 
     * @param ligne
     *            la ligne
     * @param colonne
     *            la colonne
     * @return true si la cellule est marquée, false sinon
     */
    public boolean estMarquee(int ligne, int colonne) {
        return estMarquee(diapoCourante, ligne, colonne);
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
     * Fonction permettant d'inserer une diapo au numero donné
     * 
     * @param i
     *            le numero de la diapo
     */
    public void insererDiapo(int i) {
        if ((i > 0) && (i <= getNombreDiapos())) {
            DefaultTableModel newModel = createNewVoidModel();
            // On copie le modele de la diapo précédente
            for (int j = 0; j < li; j++) {
                for (int k = 0; k < col; k++) {
                    newModel.setValueAt(getDiapo(i - 1).getValueAt(j, k), j, k);
                }
            }
            diapos.add(i - 1, newModel);

            if (getDiapo(i) != newModel) {
                System.out.println("Erreur lors de l'ajout d'une diapo (Tableau)");
                return;
            } else {
                newModel.addTableModelListener(ctrl);
            }
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
            diapos.remove(i - 1);
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

    /**
     * Fonction permettant d'aller à la diapo numéro i
     * 
     * @param i
     *            le numéro de la diapo
     */
    public void setDiapoCourante(int i) {
        if ((i > 1) && (i <= getNombreDiapos())) {
            diapoCourante = i;
        }
    }

    /**
     * Retourne le nombre de lignes du tableau
     * 
     * @return le nombre de lignes
     */
    public int getNombreLignes() {
        return li;
    }

    /**
     * Retourne le nombre de colonnes du tableau
     * 
     * @return le nombre de colonnes
     */
    public int getNombreColonnes() {
        return col;
    }

    /**
     * Retourne la liste des noms de colonnes du tableau (càd la première ligne du tableau)
     * 
     * @return la liste des noms des colonnes
     */
    public ArrayList<String> getNomsColonnes() {
        ArrayList<String> colonnes = new ArrayList<>();
        for (int i = 0; i < diapos.get(0).getColumnCount(); i++) {
            colonnes.add((String) diapos.get(0).getValueAt(0, i));
        }
        return colonnes;
    }
}
