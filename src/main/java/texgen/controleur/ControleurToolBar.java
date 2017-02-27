package texgen.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import texgen.vue.ToolBar;

/**
 * Contrôleur de la barre d'outils
 * 
 * @author BROSSARD Florian
 * @author MILLOTTE Fanny
 *
 */
public class ControleurToolBar implements ActionListener {
    /** la barre d'outil */
    private ToolBar toolBar;

    /**
     * Constructeur du contrôleur
     * 
     * @param toolBar
     *            la barre d'outils
     */
    public ControleurToolBar(ToolBar toolBar) {
        this.toolBar = toolBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source == toolBar.getBoutonSuivant()) {
            toolBar.getFenetre().diapoSuivante();
        } else if (source == toolBar.getBoutonPrecedent()) {
            toolBar.getFenetre().diapoPrecedente();
        } else if (source == toolBar.getBoutonCreer()) {
            toolBar.getFenetre().ajouterDiapo();
        } else if (source == toolBar.getBoutonInserer()) {
            toolBar.getFenetre().insererDiapo();
        } else if (source == toolBar.getBoutonSupprimer()) {
            toolBar.getFenetre().supprimerDiapoCourante();
        } else if (source == toolBar.getBoutonAjouterColonne()) {
            toolBar.getFenetre().getTableau().ajouterColonne();
        } else if (source == toolBar.getBoutonSupprimerColonne()) {
            toolBar.getFenetre().getTableau().supprimerColonne();
        } else if (source == toolBar.getBoutonAjouterLigne()) {
            toolBar.getFenetre().getTableau().ajouterLigne();
        } else if (source == toolBar.getBoutonSupprimerLigne()) {
            toolBar.getFenetre().getTableau().supprimerLigne();
        } else if (source == toolBar.getBoutonGenerer()) {
            toolBar.getFenetre().saisieInfos();
        }
    }
}
