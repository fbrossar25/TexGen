package texgen.vue;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import texgen.controleur.ControleurBarreMenu;
import texgen.controleur.ControleurTableau;

/**
 * @author fanny
 */
@SuppressWarnings("serial")
public class FenetrePrincipale extends JFrame {

    private BarreMenu  menuBar;
    private PseudoCode pseudoCode;
    private JSplitPane separateurV;
    private JSplitPane separateurH;
    private Tableau    tableau;

    public FenetrePrincipale() {
        setTitle("TexGen");
        this.setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        menuBar = new BarreMenu(this);
        add(menuBar, BorderLayout.NORTH);
        tableau = new Tableau(this);

        initMenuBar();

        pseudoCode = new PseudoCode();
        separateurH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(pseudoCode), new JScrollPane(tableau));

        separateurV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, separateurH, new JScrollPane());

        add(separateurV, BorderLayout.CENTER);

        setVisible(true);
    }

    public void initMenuBar() {
        // Menu Fichier
        ControleurBarreMenu ctrlMenuBar = new ControleurBarreMenu(menuBar);
        JMenu fichier = new JMenu("Fichier");
        JMenuItem fermer = new JMenuItem("Fermer");
        fermer.addActionListener(ctrlMenuBar);
        JMenuItem quitter = new JMenuItem("Quitter");
        quitter.addActionListener(ctrlMenuBar);
        fichier.add(fermer);
        fichier.add(quitter);
        menuBar.ajouterMenu(fichier);

        // Menu Edition
        JMenu edition = new JMenu("Edition");
        JMenuItem generer = new JMenuItem("Générer");
        generer.addActionListener(ctrlMenuBar);
        edition.add(generer);
        menuBar.ajouterMenu(edition);

        // Menu Tableau
        ControleurTableau ctrlTab = new ControleurTableau(tableau);
        JMenu tableau = new JMenu("Tableau");
        JMenu tableau_ligne = new JMenu("Ligne");
        JMenuItem ajouterLigne = new JMenuItem("Ajouter ligne");
        ajouterLigne.addActionListener(ctrlTab);
        JMenuItem supprimerLigne = new JMenuItem("Supprimer ligne");
        supprimerLigne.addActionListener(ctrlTab);
        tableau_ligne.add(ajouterLigne);
        tableau_ligne.add(supprimerLigne);
        tableau.add(tableau_ligne);
        JMenu tableau_colonne = new JMenu("Colonne");
        JMenuItem ajouterColonne = new JMenuItem("Ajouter colonne");
        ajouterColonne.addActionListener(ctrlTab);
        JMenuItem supprimerColonne = new JMenuItem("Supprimer colonne");
        supprimerColonne.addActionListener(ctrlTab);
        tableau_colonne.add(ajouterColonne);
        tableau_colonne.add(supprimerColonne);
        tableau.add(tableau_colonne);
        menuBar.ajouterMenu(tableau);

        // Menu Diapo
        JMenu diapo = new JMenu("Diapo");
        JMenuItem creer = new JMenuItem("Créer diapo");
        creer.addActionListener(ctrlMenuBar);
        diapo.add(creer);
        JMenuItem suivant = new JMenuItem("Diapo suivante");
        suivant.addActionListener(ctrlMenuBar);
        diapo.add(suivant);
        JMenuItem precedent = new JMenuItem("Diapo precedente");
        precedent.addActionListener(ctrlMenuBar);
        diapo.add(precedent);
        menuBar.add(diapo);

        // Menu Aide
        JMenu aide = new JMenu("Aide");
        JMenuItem aPropos = new JMenuItem("A propos");
        aPropos.addActionListener(ctrlMenuBar);
        aide.add(aPropos);
        menuBar.add(aide);
    }

    public void ajouterDiapo() {
        pseudoCode.ajouterDiapo();
        tableau.ajouterDiapo();
        diapoSuivante();
    }

    public void diapoSuivante() {
        pseudoCode.diapoSuivante();
        tableau.diapoSuivante();
    }

    public void diapoPrecedente() {
        pseudoCode.diapoPrecedente();
        tableau.diapoPrecedente();
    }

    public BarreMenu getBarreMenu() {
        return menuBar;
    }

    public PseudoCode getPseudoCode() {
        return pseudoCode;
    }

    public Tableau getTableau() {
        return tableau;
    }
}
