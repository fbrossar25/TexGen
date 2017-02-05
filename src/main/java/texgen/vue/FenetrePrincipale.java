package texgen.vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import texgen.controleur.ControleurBarreMenu;
import texgen.controleur.ControleurTableau;

/**
 * Classe gérant la vue de la fenêtre principale
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
@SuppressWarnings("serial")
public class FenetrePrincipale extends JFrame {

    /** Barre de menu */
    private BarreMenu  menuBar;

    /** Panel où est placé le pseudo code */
    private PseudoCode pseudoCode;

    /** Séparateur verticale */
    private JSplitPane separateurV;

    /** Séparateur horizontale */
    private JSplitPane separateurH;

    /** Panel où est placé le tableau */
    private Tableau    tableau;

    /** La barre d'outil */
    private ToolBar    toolBar;

    /** Le graph */
    private Graph      graph;

    /**
     * Constructeur de la classe
     */
    public FenetrePrincipale() {
        setTitle("TexGen");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        menuBar = new BarreMenu(this);
        add(menuBar, BorderLayout.NORTH);
        tableau = new Tableau(this);
        graph = new Graph(this);

        initMenuBar();

        pseudoCode = new PseudoCode(this);
        separateurH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(pseudoCode), new JScrollPane(tableau));

        separateurV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, separateurH, new JScrollPane(graph));

        add(separateurV, BorderLayout.CENTER);

        toolBar = new ToolBar(this);
        add(toolBar, BorderLayout.SOUTH);
        centerFrame();
        // separateurH.setEnabled(false);
        // separateurV.setEnabled(false);
        setVisible(true);
    }

    /**
     * Permet de centrer la fenêtre au démarrage
     */
    private void centerFrame() {
        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();

        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;
        setLocation(dx, dy);
    }

    /**
     * Fonction gérant l'initialisation de la barre de menu
     */
    public void initMenuBar() {
        // Menu Fichier
        ControleurBarreMenu ctrlMenuBar = new ControleurBarreMenu(menuBar);
        JMenu fichier = new JMenu("Fichier");
        JMenuItem sauvegarder = new JMenuItem("Sauvegarder sous...");
        sauvegarder.addActionListener(ctrlMenuBar);
        JMenuItem charger = new JMenuItem("Ouvrir");
        charger.addActionListener(ctrlMenuBar);
        JMenuItem fermer = new JMenuItem("Fermer");
        fermer.addActionListener(ctrlMenuBar);
        JMenuItem quitter = new JMenuItem("Quitter");
        quitter.addActionListener(ctrlMenuBar);
        fichier.add(sauvegarder);
        fichier.add(charger);
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

        // Menu Graph
        JMenu graph = new JMenu("Graph");
        JMenuItem creerNoeud = new JMenuItem("Créer noeud");
        creerNoeud.addActionListener(ctrlMenuBar);
        graph.add(creerNoeud);
        JMenuItem supprimerNoeud = new JMenuItem("Supprimer noeud");
        supprimerNoeud.addActionListener(ctrlMenuBar);
        graph.add(supprimerNoeud);
        JMenuItem creerLien = new JMenuItem("Créer lien");
        creerLien.addActionListener(ctrlMenuBar);
        graph.add(creerLien);
        JMenuItem supprimerLien = new JMenuItem("Supprimer lien");
        supprimerLien.addActionListener(ctrlMenuBar);
        graph.add(supprimerLien);
        menuBar.add(graph);

        // Menu Aide
        JMenu aide = new JMenu("Aide");
        JMenuItem aPropos = new JMenuItem("A propos");
        aPropos.addActionListener(ctrlMenuBar);
        aide.add(aPropos);
        menuBar.add(aide);
    }

    /**
     * Fonction permettant l'ajout d'une diapo
     */
    public void ajouterDiapo() {
        pseudoCode.ajouterDiapo();
        tableau.ajouterDiapo();
        graph.ajouterDiapo();
        allerDiapo(getNombreDiapos());
        toolBar.updateCompteursDiapo();
    }

    /**
     * Fonction permettant de passer à la diapo suivante
     */
    public void diapoSuivante() {
        if (toolBar.isPseudoCodeSelected()) {
            pseudoCode.diapoSuivante();
        }
        if (toolBar.isTableSelected()) {
            tableau.diapoSuivante();
        }
        if (toolBar.isGraphSelected()) {
            graph.diapoSuivante();
        }
        toolBar.updateCompteursDiapo();
    }

    /**
     * Fonction permettant de passer à la diapo précedente
     */
    public void diapoPrecedente() {
        if (toolBar.isPseudoCodeSelected()) {
            pseudoCode.diapoPrecedente();
        }
        if (toolBar.isTableSelected()) {
            tableau.diapoPrecedente();
        }
        if (toolBar.isGraphSelected()) {
            graph.diapoPrecedente();
        }
        toolBar.updateCompteursDiapo();
    }

    /**
     * Fonction donnant la barre de menu
     * 
     * @return la barre de menu
     */
    public BarreMenu getBarreMenu() {
        return menuBar;
    }

    /**
     * Fonction donnant le pseudo code
     * 
     * @return le pseudo code
     */
    public PseudoCode getPseudoCode() {
        return pseudoCode;
    }

    /**
     * Fonction donnant le tableau
     * 
     * @return le tableau
     */
    public Tableau getTableau() {
        return tableau;
    }

    /**
     * Retourne la barre d'outil
     * 
     * @return la barre d'outil
     */
    public ToolBar getToolBar() {
        return toolBar;
    }

    /**
     * Retourne le graph
     * 
     * @return le graph
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * Fonction permettant d'aller à la diapo donnée pour tout les panels
     * 
     * @param i
     *            le numéro de la diapo voulue
     */
    public void allerDiapo(int i) {
        pseudoCode.setDiapoCourante(i);
        tableau.setDiapoCourante(i);
        graph.setDiapoCourante(i);
        toolBar.updateCompteursDiapo();
        repaint();
    }

    /**
     * Fonction permettant d'inserer une diapo à la diapo courante de chaque panel
     */
    public void insererDiapo() {
        pseudoCode.insererDiapo(pseudoCode.getDiapoCourante());
        tableau.insererDiapo(tableau.getDiapoCourante());
        graph.insererDiapo(graph.getDiapoCourante());
        toolBar.updateCompteursDiapo();
    }

    /**
     * Retourne le nombre de diapos max entre les panels
     * 
     * @return le nombre de diapos
     */
    public int getNombreDiapos() {
        return Math.max(pseudoCode.getNombreDiapos(), Math.max(tableau.getNombreDiapos(), graph.getNombreDiapos()));
    }

    /**
     * Appel la méthode reset() sur les différents composants
     * 
     * @param nombreDiapos
     *            le nombre de diapos
     * @param lignes
     *            le nombre de lignes du tableau
     * @param colonnes
     *            le nombre de colonnes du tableau
     */
    public void reset(int nombreDiapos, int lignes, int colonnes) {
        pseudoCode.reset(nombreDiapos);
        tableau.reset(nombreDiapos, lignes, colonnes);
        graph.reset(nombreDiapos);
    }

    /**
     * Appel la méthode reset(1,3,4)
     * 
     * @see FenetrePrincipale#reset(int, int, int)
     */
    public void reset() {
        reset(1, 2, 4);
    }

    /**
     * Fonction permettant de rafraichir les informations de chaque panels au niveau graphique
     */
    public void refresh() {
        // On définis manuellement le model du JTable pour s'assurer que la vue est mise à jour
        tableau.refresh();
        pseudoCode.refresh();
        graph.refresh();
        toolBar.refresh();
        revalidate();
        repaint();
    }
}
