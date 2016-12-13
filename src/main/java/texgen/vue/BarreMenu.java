package texgen.vue;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import texgen.controleur.ControleurBarreMenu;

@SuppressWarnings("serial")
public class BarreMenu extends JMenuBar {

    private JMenu                           menus[] = { new JMenu("Fichier"), new JMenu("Edition"), new JMenu("Tableau"), new JMenu("Aide") };

    private ArrayList<ArrayList<JMenuItem>> items;

    private FenetrePrincipale               fenetrePrincipale;

    public BarreMenu(FenetrePrincipale fenetrePrincipale) {
        super();
        this.fenetrePrincipale = fenetrePrincipale;

        ControleurBarreMenu controleur = new ControleurBarreMenu(this);

        items = new ArrayList<ArrayList<JMenuItem>>();
        for (int i = 0; i < menus.length; i++) {
            add(menus[i]);
            items.add(new ArrayList<JMenuItem>());
        }

        JMenu menuDiapo = new JMenu("Diaporama");
        menuDiapo.add(new JMenuItem("Ajouter diapo"));
        menuDiapo.add(new JMenuItem("Supprimer diapo"));
        menuDiapo.add(new JMenuItem("diapo suivante"));
        menuDiapo.add(new JMenuItem("diapo précédente"));

        items.get(0).add(new JMenuItem("Ouvrir"));
        items.get(0).add(new JMenuItem("Fermer"));
        items.get(0).add(new JMenuItem("Quitter"));
        items.get(1).add(menuDiapo);
        items.get(1).add(new JMenuItem("Générer"));
        items.get(2).add(new JMenuItem("Ajouter ligne"));
        items.get(2).add(new JMenuItem("Supprimer ligne"));
        items.get(2).add(new JMenuItem("Ajouter colonne"));
        items.get(2).add(new JMenuItem("Supprimer colonne"));
        items.get(3).add(new JMenuItem("A propos"));

        for (int i = 0; i < items.size(); i++) {// Pour Chaque Menu
            for (int j = 0; j < items.get(i).size(); j++) { // Pour chaque item
                                                            // de chaque menu
                menus[i].add(items.get(i).get(j));
                items.get(i).get(j).addActionListener(controleur);
            }
        }
    }

    public FenetrePrincipale getFenetre() {
        return fenetrePrincipale;
    }
}
