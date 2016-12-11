package texgen.vue;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author fanny
 */
@SuppressWarnings("serial")
public class BarreMenu extends JMenuBar {
    /**
     * 
     */
    private JMenu                           menus[] = { new JMenu("Fichier"), new JMenu("Edition"), new JMenu("Tableau"), new JMenu("Aide") };
    /**
     * 
     */
    private ArrayList<ArrayList<JMenuItem>> items;

    /**
     * 
     */
    public BarreMenu() {
        super();
        items = new ArrayList<ArrayList<JMenuItem>>();
        for (int i = 0; i < menus.length; i++) {
            this.add(menus[i]);
            items.add(new ArrayList<JMenuItem>());
        }

        items.get(0).add(new JMenuItem("Ouvrir"));
        items.get(0).add(new JMenuItem("Fermer"));
        items.get(0).add(new JMenuItem("Quitter"));
        items.get(1).add(new JMenuItem("Generer"));
        items.get(2).add(new JMenuItem("Ajouter ligne"));
        items.get(2).add(new JMenuItem("Supprimer ligne"));
        items.get(2).add(new JMenuItem("Ajouter colonne"));
        items.get(2).add(new JMenuItem("Supprimer colonne"));
        items.get(3).add(new JMenuItem("A propos"));

        for (int i = 0; i < items.size(); i++) {// Pour Chaque Menu
            for (int j = 0; j < items.get(i).size(); j++) { // Pour chaque item
                                                            // de chaque menu
                menus[i].add(items.get(i).get(j));
            }
        }

    }
}
