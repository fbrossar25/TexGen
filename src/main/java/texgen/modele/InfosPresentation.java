package texgen.modele;

import java.awt.Color;
import java.util.HashMap;

import texgen.vue.FenetrePrincipale;
import texgen.vue.Graph.EtatParcours;

/**
 * Permet de stockées certaines informations (auteur, titre, couleurs,...) pour la présentation
 * 
 * @author BROSSARD Florian
 * @author MILLOTTE Fanny
 *
 */
public class InfosPresentation {
    /** le nom de l'auteur */
    private String                       nomAuteur;
    /** le titre complet de la présentation */
    private String                       titrePresentation;
    /** le titre de la présentation raccourcis */
    private String                       titrePresentationCourt;
    /** le sous-titre de la présentation */
    private String                       sousTitrePresentation;
    /** le tire de l'algorithme */
    private String                       titreAlgo;
    /** le nom de l'institut */
    private String                       institut;
    /** la date */
    private String                       date;
    /** liste des couleurs des liens en fonction de leurs états */
    private HashMap<EtatParcours, Color> couleursLiens;
    /** liste des couleurs des noeuds en fonction de leurs états */
    private HashMap<EtatParcours, Color> couleursNoeuds;
    /** la fenêtre principale */
    private FenetrePrincipale            fen;
    /** Espace horizontale entre le pseudoCode et le tableau (en cm) */
    private double                       hspace_code_tab;
    /** Taille du pseudoCode en cm */
    private double                       codeSize;
    /** Taille du tableau en cm */
    private double                       tabSize;

    /**
     * Constructeur de la classe
     * 
     * @param fenetre
     *            la fenêtre principale
     */
    public InfosPresentation(FenetrePrincipale fenetre) {
        nomAuteur = "";
        titrePresentation = "";
        titrePresentationCourt = "";
        sousTitrePresentation = "";
        titreAlgo = "";
        institut = "";
        date = "";
        fen = fenetre;
        hspace_code_tab = 0.0;
        codeSize = 5.0;
        tabSize = 5.0;
        initCouleurs();
    }

    /**
     * Retourne la fenêtre principale
     * 
     * @return la fenêtre principale
     */
    public FenetrePrincipale getFenetre() {
        return fen;
    }

    /**
     * Initialise les valeurs par défauts des couleurs
     */
    private void initCouleurs() {
        couleursLiens = new HashMap<>();
        couleursNoeuds = new HashMap<>();
        for (EtatParcours etat : EtatParcours.values()) {
            couleursLiens.put(etat, getDefaultColorForEtat(etat));
            couleursNoeuds.put(etat, getDefaultColorForEtat(etat));
        }
    }

    /**
     * Retourne la HashMap des couleurs des noeuds
     * 
     * @return la HashMap des couleurs des noeuds
     */
    public HashMap<EtatParcours, Color> getCouleursNoeuds() {
        return couleursNoeuds;
    }

    /**
     * Retourne la HashMap des couleurs des liens
     * 
     * @return la HashMap des couleurs des liens
     */
    public HashMap<EtatParcours, Color> getCouleursLiens() {
        return couleursLiens;
    }

    /**
     * Retourne la couleur par défaut correspondant à l'état donné
     * 
     * @param etat
     *            l'état
     * @return la couleur
     */
    public Color getDefaultColorForEtat(EtatParcours etat) {
        switch (etat) {
            case Actif: {
                return Color.RED;
            }

            case Inactif: {
                return Color.LIGHT_GRAY;
            }

            case Parcourus: {
                return Color.BLACK;
            }

            case Solution: {
                return Color.GREEN;
            }

            case NonSolution: {
                return Color.MAGENTA;
            }

            case Erreur: {
                return Color.BLUE;
            }

            default:
                return Color.ORANGE;
        }
    }

    /**
     * Retourne l'état correspondant à l'entier donné :<br>
     * Inactif = 0, Parcourus = 1, Actif = 2, Solution = 3, NonSolution = 4, Erreur/autre = -1
     * 
     * @param i
     *            l'entier
     * @return l'état correspondant à l'état
     */
    public EtatParcours getEtatForInt(int i) {

        switch (i) {
            case 0: {
                return EtatParcours.Inactif;
            }
            case 1: {
                return EtatParcours.Parcourus;
            }
            case 2: {
                return EtatParcours.Actif;
            }
            case 3: {
                return EtatParcours.Solution;
            }
            case 4: {
                return EtatParcours.NonSolution;
            }
            case -1: {
                return EtatParcours.Erreur;
            }
            default: {
                return EtatParcours.Erreur;
            }
        }
    }

    /**
     * Retourne un entier correspondant à l'état donné :<br>
     * Inactif = 0, Parcourus = 1, Actif = 2, Solution = 3, NonSolution = 4, Erreur/autre = -1
     * 
     * @param etat
     *            l'état
     * @return l'entier correspondant à l'état
     */
    public int getIntForEtat(EtatParcours etat) {
        switch (etat) {
            case Inactif: {
                return 0;
            }
            case Parcourus: {
                return 1;
            }
            case Actif: {
                return 2;
            }
            case Solution: {
                return 3;
            }
            case NonSolution: {
                return 4;
            }
            case Erreur: {
                return -1;
            }
            default: {
                return -1;
            }
        }
    }

    /**
     * Retourne le nom de l'auteur
     * 
     * @return le nom de l'auteur
     */
    public String getNomAuteur() {
        return nomAuteur;
    }

    /**
     * Définis le nom de l'auteur
     * 
     * @param nomAuteur
     *            le nom de l'auteur
     */
    public void setNomAuteur(String nomAuteur) {
        this.nomAuteur = nomAuteur;
    }

    /**
     * Retourne le titre de la présentation
     * 
     * @return le titre de la présentation
     */
    public String getTitrePresentation() {
        return titrePresentation;
    }

    /**
     * Définis le titre de la présentation
     * 
     * @param titrePresentation
     *            le titre de la présentation
     */
    public void setTitrePresentation(String titrePresentation) {
        this.titrePresentation = titrePresentation;
    }

    /**
     * Retourne le titre raccourcis de la présentation
     * 
     * @return le titre raccourcis de la présentation
     */
    public String getTitrePresentationCourt() {
        return titrePresentationCourt;
    }

    /**
     * Définis le titre raccourcis de la présentation
     * 
     * @param titrePresentationCourt
     *            le titre raccourcis de la présentation
     */
    public void setTitrePresentationCourt(String titrePresentationCourt) {
        this.titrePresentationCourt = titrePresentationCourt;
    }

    /**
     * Retourne le sous-titre de la présentation
     * 
     * @return le sous-titre de la présentation
     */
    public String getSousTitrePresentation() {
        return sousTitrePresentation;
    }

    /**
     * Définis le sous-titre de la présentation
     * 
     * @param sousTitrePresentation
     *            le sous-titre de la présentation
     */
    public void setSousTitrePresentation(String sousTitrePresentation) {
        this.sousTitrePresentation = sousTitrePresentation;
    }

    /**
     * Retourne le titre de l'algorithme
     * 
     * @return le titre de l'algorithme
     */
    public String getTitreAlgo() {
        return titreAlgo;
    }

    /**
     * Définis le titre de l'algorithme
     * 
     * @param titreAlgo
     *            le titre de l'algorithme
     */
    public void setTitreAlgo(String titreAlgo) {
        this.titreAlgo = titreAlgo;
    }

    /**
     * Retourne le nom de l'institut
     * 
     * @return le nom de l'institut
     */
    public String getInstitut() {
        return institut;
    }

    /**
     * Définis le nom de l'institut
     * 
     * @param institut
     *            le nom de l'institut
     */
    public void setInstitut(String institut) {
        this.institut = institut;
    }

    /**
     * Retourne la date
     * 
     * @return la date
     */
    public String getDate() {
        return date;
    }

    /**
     * Définis la date
     * 
     * @param date
     *            la date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Retourne la taille du pseudoCode en cm
     * 
     * @return la taille du pseudoCode en cm
     */
    public double getCodeSize() {
        return codeSize;
    }

    /**
     * Définis la taille du pseudoCode en cm
     * 
     * @param size
     *            la taille du pseudoCode en cm
     */
    public void setCodeSize(double size) {
        codeSize = size;
    }

    /**
     * Retourne la taille du tableau en cm
     * 
     * @return la taille du tableau en cm
     */
    public double getTabSize() {
        return tabSize;
    }

    /**
     * Définis la taille du tableau en cm
     * 
     * @param size
     *            la taille du tableau en cm
     */
    public void setTabSize(double size) {
        tabSize = size;
    }

    /**
     * Retourne l'espacement entre le pseudoCode et le tableau
     * 
     * @return l'espacement entre le pseudoCode et le tableau
     */
    public double getHSpaceCodeTab() {
        return hspace_code_tab;
    }

    /**
     * Définis l'espacement entre le pseudoCode et le tableau
     * 
     * @param hspace
     *            l'espacement entre le pseudoCode et le tableau
     */
    public void setHSpaceCodeTab(double hspace) {
        hspace_code_tab = hspace;
    }

    /**
     * Redéfinis toutes les informations et les couleurs à leurs valeurs par défaut (chaîne vides, couleurs par défaut et 0.0 pour les tailles et expacements)
     */
    public void reset() {
        nomAuteur = "";
        date = "";
        titreAlgo = "";
        sousTitrePresentation = "";
        titrePresentation = "";
        titrePresentationCourt = "";
        institut = "";
        hspace_code_tab = 0.0;
        codeSize = 5.0;
        tabSize = 5.0;
        initCouleurs();
    }
}
