package texgen.modele;

import java.awt.Color;
import java.util.HashMap;

import texgen.vue.FenetrePrincipale;
import texgen.vue.Graph.EtatParcours;

public class InfosPresentation {
    private String                       nomAuteur;
    private String                       titrePresentation;
    private String                       titrePresentationCourt;
    private String                       sousTitrePresentation;
    private String                       titreAlgo;
    private String                       institut;
    private String                       date;
    private HashMap<EtatParcours, Color> couleursLiens;
    private HashMap<EtatParcours, Color> couleursNoeuds;
    private FenetrePrincipale            fen;

    public InfosPresentation(FenetrePrincipale fenetre) {
        nomAuteur = "";
        titrePresentation = "";
        titrePresentationCourt = "";
        sousTitrePresentation = "";
        titreAlgo = "";
        institut = "";
        date = "";
        fen = fenetre;
        initCouleurs();
    }

    public FenetrePrincipale getFenetre() {
        return fen;
    }

    private void initCouleurs() {
        couleursLiens = new HashMap<>();
        couleursNoeuds = new HashMap<>();
        for (EtatParcours etat : EtatParcours.values()) {
            couleursLiens.put(etat, getDefaultColorForEtat(etat));
            couleursNoeuds.put(etat, getDefaultColorForEtat(etat));
        }
    }

    public HashMap<EtatParcours, Color> getCouleursNoeuds() {
        return couleursNoeuds;
    }

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

    public String getNomAuteur() {
        return nomAuteur;
    }

    public void setNomAuteur(String nomAuteur) {
        this.nomAuteur = nomAuteur;
    }

    public String getTitrePresentation() {
        return titrePresentation;
    }

    public void setTitrePresentation(String titrePresentation) {
        this.titrePresentation = titrePresentation;
    }

    public String getTitrePresentationCourt() {
        return titrePresentationCourt;
    }

    public void setTitrePresentationCourt(String titrePresentationCourt) {
        this.titrePresentationCourt = titrePresentationCourt;
    }

    public String getSousTitrePresentation() {
        return sousTitrePresentation;
    }

    public void setSousTitrePresentation(String sousTitrePresentation) {
        this.sousTitrePresentation = sousTitrePresentation;
    }

    public String getTitreAlgo() {
        return titreAlgo;
    }

    public void setTitreAlgo(String titreAlgo) {
        this.titreAlgo = titreAlgo;
    }

    public String getInstitut() {
        return institut;
    }

    public void setInstitut(String institut) {
        this.institut = institut;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void reset() {
        nomAuteur = "";
        date = "";
        titreAlgo = "";
        sousTitrePresentation = "";
        titrePresentation = "";
        titrePresentationCourt = "";
    }
}
