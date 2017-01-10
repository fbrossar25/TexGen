package utilities;

import java.util.ArrayList;

import texgen.vue.FenetrePrincipale;
import texgen.vue.PseudoCode;

public class GenerateurLatex {

    public static String generer(FenetrePrincipale fenetrePrincipale) {
        String string = genererEntete() + "\n\n";
        string += genererColorCode() + "\n\n";
        string += "\\begin{document}\n";
        string += "\\begin{frame}\n";
        string += genererPseudoCode(fenetrePrincipale.getPseudoCode());
        string += "\\end{frame}\n";
        string += "\\end{document}\n";
        return string;
    }

    public static String genererEntete() {
        return "\\documentclass[hyperref={pdfpagemode=FullScreen,colorlinks=true}]{beamer}\n" + "\\usepackage{kpfonts}\n"
                + "\\usepackage[utf8]{inputenc}\n" + "\\usepackage[T1]{fontenc}\n" + "\\usepackage{pdfpages}\n" + "\\usetheme{Boadilla}\n"
                + "\\usepackage{tikz}\n" + "\\usepackage[frenchb]{babel}\n"
                + "\\usepackage[ruled,vlined,linesnumberedhidden,french,slide]{algorithm2e}";
    }

    private static ArrayList<String> separerLignes(String txt) {
        if ((txt == null) || (txt.length() <= 0)) {
            return null;
        } else {
            ArrayList<String> list = new ArrayList<>();
            int i = 0;
            String currentLine = "";
            char c;
            while (i < txt.length()) {
                c = txt.charAt(i);
                currentLine += c;
                if ((i == (txt.length() - 1)) || (c == '\n')) {
                    // Fin du pseudoCode ou ligne suivante
                    list.add(currentLine);
                    currentLine = "";
                }
                i++;
            }
            return list;
        }
    }

    public static String genererColorCode(PseudoCode p, String ligne, int ligneNum) {
        String res = "\\colorCode";
        ArrayList<Integer> marked = new ArrayList<>();
        ArrayList<Integer> unmarked = new ArrayList<>();
        int nbDiapo = p.getNombreDiapos();
        for (int diapo = 1; diapo <= nbDiapo; diapo++) {
            if (p.estMarquee(diapo, ligneNum)) {
                marked.add(diapo);
            } else {
                unmarked.add(diapo);
            }
        }

        String markedString = "{";
        int markedSize = marked.size();
        if (markedSize > 0) {
            for (int i = 0; i < markedSize; i++) {
                markedString += marked.get(i) + ((i == (markedSize - 1)) ? "" : ",");
            }
        }
        markedString += "}";

        String unmarkedString = "{";
        int unmarkedSize = unmarked.size();
        if (unmarkedSize > 0) {
            for (int i = 0; i < unmarkedSize; i++) {
                unmarkedString += unmarked.get(i) + ((i == (unmarkedSize - 1)) ? "" : ",");
            }
        }
        unmarkedString += "}";

        String ligneString = "";
        for (char c : ligne.toCharArray()) {
            ligneString += (c == '\n') ? "" : c;
        }

        return res + unmarkedString + markedString + "{" + ligneString + "}\n";
    }

    public static String genererPseudoCode(PseudoCode pseudoCode) {
        String res = "";

        res += "\\begin{minipage}[t]{6.2cm}\n" + "\\begin{tiny}\n" + "\\begin{algorithm*}[H]\n" + "\\NoCaptionOfAlgo\n\n";

        ArrayList<String> lignes = separerLignes(pseudoCode.getTextArea().getText());
        for (int i = 1; i <= lignes.size(); i++) {
            res += genererColorCode(pseudoCode, lignes.get(i - 1), i);
        }

        res += "\n" + "\\end{algorithm*}\n" + "\\end{tiny}\n" + "\\end{minipage}\\hspace*{0.2cm}\n";
        return res;
    }

    public static String genererColorCode() {
        return "\\newcommand{    \\colorCode}[3]{%\n\\only<#1>{\\textcolor{black}{#3}}\\only<#2>{\\textcolor{red}{#3}}\n}";
    }
}
