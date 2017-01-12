package texgen.vue;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Classe gérant la vue de la barre de menu
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
@SuppressWarnings("serial")
public class BarreMenu extends JMenuBar {

    /** Fenêtre principale du projet */
    private FenetrePrincipale fenetrePrincipale;

    /**
     * Constructeur de la classe
     * 
     * @param fenetrePrincipale
     *            Fenêtre principale du projet
     */
    public BarreMenu(FenetrePrincipale fenetrePrincipale) {
        super();
        this.fenetrePrincipale = fenetrePrincipale;
    }

    /**
     * Fonction permettant l'ajout d'un menu
     * 
     * @param menu
     *            Menu à ajouter
     */
    public void ajouterMenu(JMenu menu) {
        add(menu);
    }

    /**
     * Fonction permettant l'ajout d'un item dans un menu
     * 
     * @param menu
     *            nom du menu dans lequel ajouter l'item
     * @param item
     *            item à ajouter dans le menu
     */
    public void ajouterItem(String menu, JMenuItem item) {
        JMenu targetedMenu = null;
        for (int i = 0; i < getMenuCount(); i++) {
            if (getMenu(i).getText().equals(menu)) {
                targetedMenu = getMenu(i);
                break;
            }
        }
        if (targetedMenu != null) {
            targetedMenu.add(item);
        }
    }

    /**
     * Fonction donnant le menu dont on donne le nom
     * 
     * @param menu
     *            nom du menu à rechercher
     * @return menu trouvé, null sinon
     */
    public JMenu getMenu(String menu) {
        for (int i = 0; i < getMenuCount(); i++) {
            if (getMenu(i).getText().equals(menu)) {
                return getMenu(i);
            }
        }
        return null;
    }

    /**
     * Fonction donnant la fenêtre principale
     * 
     * @return fenêtre principale
     */
    public FenetrePrincipale getFenetre() {
        return fenetrePrincipale;
    }
}
