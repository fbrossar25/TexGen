package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe utilitaire utiliser pour la lecture/écriture dans des fichiers
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class FileUtilities {

    /**
     * Fonction permettant l'écriture d'un texte dans un fichier
     * 
     * @param s
     *            Texte à écrire
     * @param fullPath
     *            Chemin absolu du fichier
     */
    public static void writeStringInFile(String s, String fullPath) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(new File(fullPath));
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        openDefaultEditor(fullPath);
    }

    /**
     * Fonction permettant l'ouverture de l'éditeur LaTeX par défaut du système
     * 
     * @param fullPath
     *            Nom du fichier
     */
    public static void openDefaultEditor(String fullPath) {
        if ((fullPath == null) || (fullPath.length() <= 0)) {
            System.out.println("Aucun fichier spécifié !");
            System.exit(-1);
        }

        File file = new File(fullPath);

        if (!file.exists() && (file.length() < 0)) {
            System.out.println("Fichier introuvable ou vide !");
            System.exit(-1);
        }

        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            try {
                desktop = Desktop.getDesktop();
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        } else {
            System.out.println("OS non supproté !");
        }
    }
}
