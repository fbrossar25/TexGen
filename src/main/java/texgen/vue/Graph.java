package texgen.vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import texgen.controleur.ControleurGraph;
import texgen.modele.Lien;
import texgen.modele.Noeud;
import texgen.utilities.DrawUtilities;

/**
 * Classe gérant la vue du graph
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
@SuppressWarnings("serial")
public class Graph extends JPanel {

    /** Représente l'état d'un noeud/lien */
    private enum EtatParcours {
        Inactif, Actif, Parcourus,
    }

    /** La fenetre princiapale de l'application */
    private FenetrePrincipale                       fenetre;
    /** HashMap ayant pour clé les noeuds et une liste d'états pour valeurs */
    private HashMap<Noeud, ArrayList<EtatParcours>> noeuds;
    /** Liste de Lien */
    private ArrayList<Lien>                         liens;
    /** Référence au noeud cible (pour le drag'n'drop) */
    private Noeud                                   targetedNode;
    /** Référence au noeud sélectionné (pour suppression, ajout de lien,...) */
    private Noeud                                   selectedNode;
    /** Controleur du graph */
    private ControleurGraph                         ctrl;
    /** Le numéro de la diapo courante */
    private int                                     diapoCourante;
    /** le nombre de diapos */
    private int                                     nombreDiapos;
    /** Référence au noeud créant un lien */
    private Noeud                                   nodeCreatingLink;

    /**
     * Constructeur de la classe
     * 
     * @param fenetre
     *            la fentre principale de l'application
     */
    public Graph(FenetrePrincipale fenetre) {
        super();
        nombreDiapos = 1;
        diapoCourante = 1;
        this.fenetre = fenetre;
        noeuds = new HashMap<>();
        liens = new ArrayList<>();
        ctrl = new ControleurGraph(this);
        addMouseListener(ctrl);
        addMouseMotionListener(ctrl);
        targetedNode = null;
        selectedNode = null;
        nodeCreatingLink = null;
        // setFocusable(true);
    }

    /**
     * Définis quel est le noeud à partir duquel on créer un lien vers un autre
     * 
     * @param n
     *            le noeud de départ
     */
    public void setNodeCreatingLink(Noeud n) {
        nodeCreatingLink = n;
    }

    /**
     * Rafraichis la vue du pseudoCode
     */
    public void refresh() {
        revalidate();
        repaint();
    }

    /**
     * Fonction permettant de remettre à son état initial le graph
     */
    public void reset(int nombreDiapos) {
        if (nombreDiapos < 1) {
            this.nombreDiapos = 1;
        } else {
            this.nombreDiapos = nombreDiapos;
        }
        diapoCourante = 1;
        for (Noeud n : noeuds.keySet()) {
            remove(n);
        }
        noeuds.clear();
        for (Lien l : liens) {
            remove(l);
        }
        liens.clear();
        targetedNode = null;
        selectedNode = null;
        refresh();
    }

    /**
     * Permet de passer à la diapo suivante, si elle existe
     */
    public void diapoSuivante() {
        if (diapoCourante < nombreDiapos) {
            diapoCourante++;
            refresh();
        }
    }

    /**
     * Permet de passer à la diapo précédente, si elle existe
     */
    public void diapoPrecedente() {
        if (diapoCourante > 1) {
            diapoCourante--;
            refresh();
        }
    }

    /**
     * Définis la diapo courante à afficher pour le graph
     * 
     * @param i
     *            le numéro de la diapo souhaitée
     */
    public void setDiapoCourante(int i) {
        if ((i > 0) && (i <= nombreDiapos)) {
            diapoCourante = i;
            refresh();
        }
    }

    /**
     * Retourne le nombre de diapos du graph
     * 
     * @return le nombre de diapos
     */
    public int getNombreDiapos() {
        return nombreDiapos;
    }

    /**
     * NON IMPLEMENTÉE !!<br>
     * Fonction permettant d'inserer une diapo juste avant la diapo i
     * 
     * @param i
     *            la diapo
     */
    public void insererDiapo(int i) {
        // TODO
    }

    /**
     * Retourne le numéro de la diapo courante
     * 
     * @return le numéro de la diapo courante
     */
    public int getDiapoCourante() {
        return diapoCourante;
    }

    /**
     * Insère une nouvelle diapo à la fin
     */
    public void ajouterDiapo() {
        for (Noeud n : getNoeuds()) {
            noeuds.get(n).add(getEtatCourantNoeud(n));
        }
        nombreDiapos++;
        diapoSuivante();
    }

    /**
     * Supprime la diapo numéro i, si elle existe
     * 
     * @param i
     *            le numéro de la diapo à supprimer
     */
    public void suprimerDiapo(int i) {
        if (i > 0 && i <= nombreDiapos) {
            for (Noeud n : getNoeuds()) {
                noeuds.get(n).remove(i);
            }
            if (diapoCourante == nombreDiapos) {
                diapoPrecedente();
            } else if (i == diapoCourante) {
                refresh();
            }
            nombreDiapos--;
        }
    }

    /**
     * Définis la noeud cible (targetedNode) à null
     */
    public void resetTargetedNode() {
        targetedNode = null;
    }

    /**
     * Retourne le noeud cible (targetedNode)
     * 
     * @return le noeud cible
     */
    public Noeud getTargetedNode() {
        return targetedNode;
    }

    /**
     * Retourne le noeud sélectionné (selectedNode)
     * 
     * @return le noeud selectionné
     */
    public Noeud getSelectedNode() {
        return selectedNode;
    }

    /**
     * Retourne le noeud à partir duquel on créer un lien vers un autre
     * 
     * @return le noeud, ou null
     */
    public Noeud getNodeCreatingLink() {
        return nodeCreatingLink;
    }

    /**
     * Met à jour le noeud sélectionné (selectedNode)
     * 
     * @param p
     *            le point de l'évenement détecté sur la souris
     * @return le Noeud sélectionné ou null
     */
    public Noeud updateSelectedNode(Point p) {
        selectedNode = mouseTargetingNode(p);
        return selectedNode;
    }

    /**
     * Définis le noeud sélectionné (selectedNode) à null
     */
    public void resetSelectedNode() {
        selectedNode = null;
    }

    /**
     * Met à jour le noeud cible (targetedNode)
     * 
     * @param p
     *            le point de l'évenement détecté sur la souris
     * @return le Noeud cible ou null
     */
    public Noeud updateTargetedNode(Point p) {
        targetedNode = mouseTargetingNode(p);
        return targetedNode;
    }

    /**
     * Retourne la liste des noeuds du graph
     * 
     * @return la liste des noeuds
     */
    public ArrayList<Noeud> getNoeuds() {
        ArrayList<Noeud> list = new ArrayList<>();
        list.addAll(noeuds.keySet());
        return list;
    }

    /**
     * Retourne la liste des liens du graph
     * 
     * @return la liste des liens
     */
    public ArrayList<Lien> getLiens() {
        return liens;
    }

    /**
     * Retourne le ième Noeud
     * 
     * @param i
     *            l'indice du noeud
     * @return le noeud
     */
    public Noeud getNoeud(int i) {
        if (i < 0 || i >= noeuds.size()) {
            return null;
        }
        return getNoeuds().get(i);
    }

    /**
     * Retourne le premier Noeud ayant le label donné
     * 
     * @param label
     *            le label
     * @return le premier Noeud correspondant
     */
    public Noeud getNoeud(String label) {
        for (Noeud n : noeuds.keySet()) {
            if (n.getText().equals(label)) {
                return n;
            }
        }
        return null;
    }

    /**
     * Retourne le nombre de liens
     * 
     * @return le nombre de liens
     */
    public int getNombreLiens() {
        return liens.size();
    }

    /**
     * Retourne le nombre de noeuds
     * 
     * @return le nombre de noeuds
     */
    public int getNombreNoeuds() {
        return noeuds.size();
    }

    /**
     * Retourne le premier lien ayant pour label le laber donné
     * 
     * @param label
     *            le label
     * @return le premier lien correspondant
     */
    public Lien getLien(String label) {
        for (Lien l : liens) {
            if (l.getText().equals(label)) {
                return l;
            }
        }
        return null;
    }

    /**
     * Créer un lien entre les noeud départ et arrive avec le label donné
     * 
     * @param label
     *            le label
     * @param depart
     *            le noeud de départ
     * @param arrive
     *            le noeud d'arriveé
     * @return le lien créé
     */
    public Lien creerLien(String label, Noeud depart, Noeud arrive) {
        if (depart == null || arrive == null) {
            return null;
        }
        Lien l = new Lien(label, depart, arrive);
        liens.add(l);
        add(l);
        l.updateLocation();
        refresh();
        return l;
    }

    /**
     * Retourne une liste d'états EtatParcours.Inactif de taille nombreDiapos
     * 
     * @return la liste d'états inactif
     */
    public ArrayList<EtatParcours> getFullInactiveStates() {
        ArrayList<EtatParcours> etat = new ArrayList<>();
        for (int i = 1; i <= nombreDiapos; i++) {
            etat.add(EtatParcours.Inactif);
        }
        return etat;
    }

    /**
     * Créer un noeud avec le label donné
     * 
     * @param label
     *            le label
     * @return le noeud créé
     */
    public Noeud creerNoeud(String label) {
        Noeud n = new Noeud(label);
        noeuds.put(n, getFullInactiveStates());
        add(n);
        refresh();
        return n;
    }

    /**
     * Créer un noeud avec le label donné au coordonnées données
     * 
     * @param label
     *            le label
     * @param x
     *            la coordonnée en x
     * @param y
     *            la coordonnée en y
     * @return le noeud créé
     */
    public Noeud creerNoeud(String label, int x, int y) {
        Noeud n = new Noeud(label, new Point(x, y));
        noeuds.put(n, getFullInactiveStates());
        add(n);
        refresh();
        return n;
    }

    /**
     * Supprime le noeud selectionné
     */
    public void supprimerNoeudSelectionne() {
        supprimerNoeud(selectedNode);
    }

    /**
     * Supprime le noeud ciblé
     */
    public void supprimerNoeudCible() {
        supprimerNoeud(targetedNode);
    }

    /**
     * Supprime le Noeud n du graph et les liens associés
     * 
     * @param n
     *            le noeud
     */
    public void supprimerNoeud(Noeud n) {
        if (n != null) {
            supprimerLienAvecNoeud(n);
            remove(n);
            noeuds.remove(n);
        } else {
            System.out.println("n is null and cannot be removed");
        }
        resetSelectedNode();
        refresh();
    }

    /**
     * Supprime les liens associés au Noeud n
     * 
     * @param n
     *            le noeud
     */
    public void supprimerLienAvecNoeud(Noeud n) {
        ArrayList<Lien> toDelete = new ArrayList<>();
        for (Lien l : liens) {
            if (l.estAssocieA(n)) {
                toDelete.add(l);
            }
        }

        for (Lien l : toDelete) {
            supprimerLien(l);
        }
        toDelete.clear();
    }

    /**
     * Supprimer le lien l et tout les noeuds associés
     * 
     * @param l
     *            le lien
     */
    public void supprimerLien(Lien l) {
        if (l != null) {
            remove(l);
            liens.remove(l);
        }
        refresh();
    }

    /**
     * Retourne la fenêtre principale de l'aplication
     * 
     * @return la fenêtre principale
     */
    public FenetrePrincipale getFenetre() {
        return fenetre;
    }

    /**
     * Retourne le noeud incluant le Point p (position d'un clic souris), ou null
     * 
     * @param p
     *            la position du clic
     * @return le Noeud ciblé ou null si aucun ne l'est
     */
    public Noeud mouseTargetingNode(Point p) {
        for (Noeud n : noeuds.keySet()) {
            if (n.contientPoint(p)) {
                return n;
            }
        }
        return null;
    }

    /**
     * Retourne l'état du noeud n à la diapo donné
     * 
     * @param n
     *            le noeud
     * @param diapo
     *            la diapo
     * @return l'état du noeud
     */
    public EtatParcours getEtatNoeudDiapo(Noeud n, int diapo) {
        if (diapo < 1 || diapo > nombreDiapos) {
            return null;
        }
        return noeuds.get(n).get(diapo - 1);
    }

    /**
     * Retourne l'état du noeud n à la diapo courante
     * 
     * @param n
     *            le noeud
     * @return l'état du noeud
     */
    public EtatParcours getEtatCourantNoeud(Noeud n) {
        return getEtatNoeudDiapo(n, diapoCourante);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Noeud n : noeuds.keySet()) {
            // On replace les Noeuds aux point sauvegarde manuelement pour éviter qu'ils se déplacent au redimensionnement du panel
            n.replacer();
            switch (getEtatCourantNoeud(n)) {
                case Actif: {
                    g.setColor(Color.RED);
                }
                    break;

                case Inactif: {
                    g.setColor(Color.LIGHT_GRAY);
                }
                    break;

                case Parcourus: {
                    g.setColor(Color.BLACK);
                }
                    break;

                default:
                    g.setColor(Color.ORANGE);
            }

            DrawUtilities.drawCenteredCircle(g, n.getCentre(), n.getRayon());
            if (n == selectedNode) {
                g.setColor(Color.GRAY);
                DrawUtilities.drawNodeSelectionSquare(g, n);
                g.setColor(Color.RED);
            }

        }

        g.setColor(Color.BLACK);
        for (Lien l : liens) {
            l.updateLocation();
            DrawUtilities.drawLink(g, l, false);
        }

        if (nodeCreatingLink != null) {
            DrawUtilities.drawLink(g, nodeCreatingLink, getMousePosition(), false);
        }
    }
}
