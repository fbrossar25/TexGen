package texgen.vue;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import texgen.controleur.ControleurTableau;

@SuppressWarnings("serial")
public class Tableau extends JPanel {
    private FenetrePrincipale            fenetrePrincipale;
    private JTable                       tab;
    private int                          col;
    private int                          li;
    private final int                    DEFAULT_ROW_HEIGHT = 21;
    private ArrayList<DefaultTableModel> diapos;
    private int                          diapoCourante;
    private ControleurTableau            ctrl;

    public Tableau(FenetrePrincipale fenetrePrincipale) {
        super();
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

    public FenetrePrincipale getFenetre() {
        return fenetrePrincipale;
    }

    public Rectangle getCellRect(int row, int col) {
        return tab.getCellRect(row, col, true);
    }

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

    public void ajouterColonne() {
        getDiapo(diapoCourante).addColumn("");
        col++;
    }

    public void supprimerColonne() {
        if (col > 1) {
            col--;
            getDiapo(diapoCourante).setColumnCount(col);
        }
    }

    public int getDiapoCourante() {
        return diapoCourante;
    }

    public int getNombreDiapos() {
        return diapos.size();
    }

    public void ajouterDiapo() {
        DefaultTableModel newModel = createNewVoidModel();
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

    public void disableAllModelListener() {
        ctrl.setActive(false);
    }

    public void enableAllModelListener() {
        ctrl.setActive(true);
    }

    public DefaultTableModel getDiapo(int i) {
        if ((i < 1) || (i > diapos.size())) {
            return null;
        } else {
            return diapos.get(i - 1);
        }
    }

    public void diapoSuivante() {
        if (diapoCourante < getNombreDiapos()) {
            diapoCourante++;
            tab.setModel(getDiapo(diapoCourante));
        }
    }

    public void diapoPrecedente() {
        if (diapoCourante > 1) {
            diapoCourante--;
            tab.setModel(getDiapo(diapoCourante));
        }
    }

    public void supprimerDiapo(int i) {
        if ((i > 0) && (i <= getNombreDiapos())) {
            diapos.remove(i);
        }
    }

    public JTable getTab() {
        return tab;
    }

    public void repercuterModifTitre(int colonne, int source) {
        disableAllModelListener();
        for (DefaultTableModel m : diapos) {
            m.setValueAt(getDiapo(source).getValueAt(0, colonne), 0, colonne);
        }
        enableAllModelListener();
    }

    public void repercuterModif(int diapoSource, int ligne, int colonne) {
        disableAllModelListener();
        for (int i = diapoSource + 1; i <= getNombreDiapos(); i++) {
            getDiapo(i).setValueAt(getDiapo(diapoSource).getValueAt(ligne, colonne), ligne, colonne);
        }
        enableAllModelListener();
    }
}
