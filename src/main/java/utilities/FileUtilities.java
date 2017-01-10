package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtilities {
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
