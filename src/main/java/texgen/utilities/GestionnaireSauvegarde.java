package texgen.utilities;

import java.util.TreeSet;

import javax.swing.table.DefaultTableModel;

import texgen.vue.PseudoCode;
import texgen.vue.Tableau;

/**
 * Classe gérant la sauvegarde d'un projet dans un fichier xml
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 */
public class GestionnaireSauvegarde {
    public static void sauvegarder(PseudoCode p, Tableau t, String fullPath) {
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n";
        s += "<projet>\n";
        s += sauvegarderPseudoCode(p) + "\n\n" + sauvegarderTableau(t);
        s += "\n</projet>";
        FileUtilities.writeStringInFile(s, fullPath, false);
    }

    public static String sauvegarderPseudoCode(PseudoCode p) {
        String res = "<pseudocode>\n";

        // Sauvegarde du texte
        res += "\t<text>\n";
        for (String l : p.getLignes()) {
            if (l.equals("\n")) {
                res += "\t\t<ligne></ligne>\n";
            } else {
                res += "\t\t<ligne>" + l + "</ligne>\n";
            }
        }
        res += "\t</text>\n";

        // Sauvegarde des marqueurs
        res += "\t<marqueurs>\n";
        TreeSet<Integer> marqueurs;
        for (int i = 1; i <= p.getNombreDiapos(); i++) {
            marqueurs = p.getMarqueursDiapo(i);
            res += "\t\t<diapo numero=\"" + i + "\">\n";
            for (Integer m : marqueurs) {
                res += "\t\t\t<marqueur>" + m + "</marqueur>\n";
            }
            res += "\t\t</diapo>\n";
        }
        res += "\t</marqueurs>\n";
        return res + "</pseudocode>";
    }

    public static String sauvegarderTableau(Tableau t) {
        String res = "<tableau lignes=\"" + t.getNombreLignes() + "\" colonnes=\"" + t.getNombreColonnes() + "\">\n";

        // Sauvegarde des noms des colonnes
        res += "\t<colonnes>\n";
        int count = 0;
        for (String nom : t.getNomsColonnes()) {
            res += "\t\t<colonne numero=\"" + count + "\">" + nom + "</colonne>\n";
            count++;
        }
        res += "\t</colonnes>\n";

        // Sauvegarde du contenus des diapos
        for (int i = 1; i <= t.getNombreDiapos(); i++) {
            DefaultTableModel model = t.getDiapo(i);
            res += "\t<diapo numero=\"" + i + "\">\n";
            for (int j = 1; j < model.getRowCount(); j++) {
                res += "\t\t<ligne numero=\"" + j + "\">\n";
                for (int k = 0; k < model.getColumnCount(); k++) {
                    String value = (String) model.getValueAt(j, k);
                    if ((value != null) && !value.equals("")) {
                        res += "\t\t\t<case numero=\"" + (k + 1) + "\">" + value + "</case>\n";
                    }
                }
                res += "\t\t</ligne>\n";
            }
            res += "\t</diapo>\n";
        }
        return res + "</tableau>";
    }
}
