package texgen.vue;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import texgen.utilities.SpringUtilities;

/**
 * Fenêtre A Propos, donnant les noms des développeurs et les sources des ressources (si nécessaire)
 * 
 * @author BROSSARD Florian
 * @author MILLOTTE Fanny
 */
@SuppressWarnings("serial")
public class APropos extends JFrame {
    /**
     * Constructeur de la classe
     */
    public APropos() {
        super();
        setTitle("A propos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        SpringLayout layout = new SpringLayout();
        JPanel text = new JPanel(layout);
        text.add(new JLabel("Logiciel développé par Fanny MILLOTTE et BROSSARD Florian"));
        text.add(new JLabel("Développé dans le cadre du projet de L3 Informatique à l'université de Franche-Comté à Besançon"));
        text.add(new JLabel("Année 2016-2017"));
        text.add(new JLabel("Images utilisées :"));
        text.add(new JLabel("Bouton ajout diapo : par madebyolivier sur flaticon"));
        text.add(new JLabel("Bouton inserer diapo : par freepik sur flaticon"));
        text.add(new JLabel("Bouton supprimer diapo : par Dario Ferrando sur flaticon"));
        text.add(new JLabel("Boutons supprimer ligne/colonne : par Everaldo Coelho sur IconFinder (everaldo.com)"));
        text.add(new JLabel("Boutons ajouter ligne/colonne et generer : Gnome Project sur IconFinder (art.gnome.org/themes/icon)"));
        SpringUtilities.makeGrid(text, text.getComponentCount(), 1, 0, 0, 0, 0);
        add(text, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }
}
