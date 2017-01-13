package texgen;

import texgen.vue.FenetrePrincipale;

/**
 * Classe gérant le lancement de l'application
 * 
 * @author Florian Brossard
 * @author Fanny MILLOTTE
 */

public class MainApp {

    /** Chemin du dossier contenant les ressources (icones) de l'application */
    public static final String RESOURCES_DIR = "resources/";

    /**
     * Méthode principale
     * 
     * @param args
     *            arguments
     */
    public static void main(String[] args) {
        new FenetrePrincipale();
    }
}
