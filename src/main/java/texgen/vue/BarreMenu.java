package texgen.vue;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class BarreMenu extends JMenuBar {
    private FenetrePrincipale fenetrePrincipale;

    public BarreMenu(FenetrePrincipale fenetrePrincipale) {
        super();
        this.fenetrePrincipale = fenetrePrincipale;
    }

    public void ajouterMenu(JMenu menu) {
        add(menu);
    }

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

    public JMenu getMenu(String menu) {
        for (int i = 0; i < getMenuCount(); i++) {
            if (getMenu(i).getText().equals(menu)) {
                return getMenu(i);
            }
        }
        return null;
    }

    public FenetrePrincipale getFenetre() {
        return fenetrePrincipale;
    }
}
