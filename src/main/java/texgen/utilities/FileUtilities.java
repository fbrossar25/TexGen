package texgen.utilities;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import texgen.vue.FenetrePrincipale;

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
     * @param openEditor
     *            true pour ouvrir l'editeur après l'écriture du fichier, false sinon
     */
    public static void writeStringInFile(String s, String fullPath, boolean openEditor) {
        Writer fstream = null;
        BufferedWriter out = null;
        try {
            fstream = new OutputStreamWriter(new FileOutputStream(new File(fullPath)), StandardCharsets.UTF_8);
            out = new BufferedWriter(fstream);
            out.write(s);
            out.close();
            fstream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        if (openEditor) {
            openDefaultEditor(fullPath);
        }
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

    /**
     * Ouvre une fenêtre de sélection de fichier dans le FenetrePrincipale avec une description et la seule extensions accepté données.
     * 
     * @param f
     *            la fenêtre principale de l'application
     * @param description
     *            la description du type de fichier voulu
     * @param ext
     *            l'extension acceptée
     * @return le chemin absolu du fichier selectionné
     */
    public static String selectFileWithFilter(FenetrePrincipale f, String description, String ext) {
        FileNameExtensionFilter extFilter = new FileNameExtensionFilter(description, ext);
        JFileChooser jc = new JFileChooser();
        jc.addChoosableFileFilter(extFilter);
        jc.setAcceptAllFileFilterUsed(false);
        jc.showOpenDialog(f);
        if (jc.getSelectedFile() == null) {
            return "";
        }
        String fullPath = jc.getSelectedFile().getAbsolutePath();
        if (!fullPath.endsWith("." + ext)) {
            fullPath += "." + ext;
        }
        return fullPath;
    }

}
