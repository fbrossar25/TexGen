package texgen.utilities;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import texgen.vue.PseudoCode;

/**
 * Classe utilitaire contenant les fonction utiliser pour parser le pseudoCode
 * 
 * @author Florian BROSSARD
 * @author Fanny MILLOTTE
 *
 */
public class ParserCode {

    /**
     * Les types des blocs gerés.
     * 
     * @author Florian BROSSARD
     * @author Fanny MILLOTTE
     *
     */
    public static enum typeBlocs {
        faireTantQue, tantQueFaire, pour, siAlors, siAlorsSinon, texte
    }

    public static final HashMap<typeBlocs, Pattern> patterns = initPatterns();

    /**
     * Fonction parsant le texte donné en remplaçant tout les blocs énumérés dans typeBlocs par leurs équivalant LaTeX
     * 
     * @param text
     *            le texte à parser
     * @return Le code LaTeX correspondant au texte
     */
    public static String parserPseudoCode(PseudoCode p) {
        String texte = p.getTextArea().getText();
        texte = parse(texte, p);
        return texte;
    }

    /**
     * Retourne le premier type de bloc correspondant sur le texte, typeBlocs.texte si aucun ne correspond.
     * 
     * @param texte
     * @return
     */
    public static typeBlocs blocMatch(String texte) {
        if (patterns.get(typeBlocs.siAlorsSinon).matcher(texte).find(0)) {
            return typeBlocs.siAlorsSinon;
        } else if (patterns.get(typeBlocs.siAlors).matcher(texte).find(0)) {
            return typeBlocs.siAlors;
        } else {
            return typeBlocs.texte;
        }
    }

    /**
     * Fonction gèrant le parsing d'un texte pour un pseudoCode donné
     * 
     * @param texte
     *            le texte
     * @param p
     *            le pseudoCode
     * @return le code LaTeX du texte
     */
    public static String parse(String texte, PseudoCode p) {
        if (p == null || texte == null || texte.equals("")) {
            return "";
        }
        String res = texte;
        typeBlocs typeCourant;
        while ((typeCourant = blocMatch(res)) != typeBlocs.texte) {
            switch (typeCourant) {
                case siAlorsSinon: {
                    res = siAlorsSinonParser(p, res);
                }
                    break;
                case siAlors: {
                    res = siAlorsParser(p, res);
                }
                    break;
                case pour: {

                }
                    break;
                case faireTantQue: {

                }
                    break;

                case tantQueFaire: {

                }
                    break;
                case texte: {

                }
                    break;
                default: {

                }
            }
        }
        return res;
    }

    /**
     * Fonction permettant de calculer la plage de lignes sur laquel se trouve le groupe. Retourne Un tableau à 2 entiers : [0] numero de la premiere ligne du groupe, [1] la derniere ligne du groupe.
     * Erreur si l'un des entiers == 0.
     * 
     * @param p
     *            le pseudoCode
     * @param m
     *            le matcher contenant les groupe
     * @param groupe
     *            le groupe
     * @return le tableau à 2 entiers
     */
    public static int[] calculerPlageLignesGroupe(PseudoCode p, Matcher m, int groupe) {
        int[] res = { 0, 0 };
        if (groupe < 1 || groupe > m.groupCount()) {
            return res;
        }
        int begin = m.start(groupe);
        int end = m.end(groupe);
        int currentLine = 1;
        String text = p.getTextArea().getText();
        for (int i = 0; i <= end; i++) {
            if (text.charAt(i) == '\n') {
                currentLine++;
            }
            if (i == begin) {
                res[0] = currentLine;
            }
            if (i == end) {
                res[1] = currentLine;
                break;
            }
        }
        return res;
    }

    /**
     * Fonction permettant de parser le pattern si condition alors instructions fin
     * 
     * @param p
     *            le pseudoCode
     * @param texte
     *            le texte à parser
     * @return Le code LaTeX du texte
     */
    public static String siAlorsParser(PseudoCode p, String texte) {
        String res = "";
        Matcher m = patterns.get(typeBlocs.siAlors).matcher(texte);
        if (m.matches()) {
            String condition = m.group(1);
            condition = parse(condition, p);
            String alors = m.group(2);
            alors = parse(alors, p);
            res = "\\lIf{" + condition + "}{" + alors + "}";
        }
        return res;
    }

    /**
     * Fonction permettant de parser le pattern si condition alors instructions sinon instructions fin
     * 
     * @param p
     *            le pseudoCode
     * @param texte
     *            le texte à parser
     * @return Le code LaTeX du texte
     */
    public static String siAlorsSinonParser(PseudoCode p, String texte) {
        String res = "";
        Matcher m = patterns.get(typeBlocs.siAlorsSinon).matcher(texte);
        if (m.matches()) {
            String condition = m.group(1);
            condition = parse(condition, p);
            String alors = m.group(2);
            alors = parse(alors, p);
            String sinon = m.group(3);
            sinon = parse(sinon, p);
            res = "\\lIf{" + condition + "}{" + alors + "}\n\\uElse{" + sinon + "}";
        }
        return res;
    }

    /**
     * Fonction qui initialise les patterns utilisés pour parser le code
     * 
     * @return la hashMap des patterns
     */
    private static HashMap<typeBlocs, Pattern> initPatterns() {
        HashMap<typeBlocs, Pattern> patterns = new HashMap<>();
        final Pattern p1 = Pattern.compile(siAlorsSinonRegex(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.UNICODE_CHARACTER_CLASS);
        patterns.put(typeBlocs.siAlorsSinon, p1);
        final Pattern p2 = Pattern.compile(siAlorsRegex(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.UNICODE_CHARACTER_CLASS);
        patterns.put(typeBlocs.siAlors, p2);
        return patterns;
    };

    /**
     * Retourne l'expression régulière JAVA d'un bloc si cond alors stuff sinon stuff fin
     * 
     * @return L'expression régulière JAVA siAlorsSinonInline
     */
    private static String siAlorsSinonRegex() {
        // ReplaceFirestWith("\\uIf{$3}{$7}{$11}");
        return "(?:si|if)(?:\\s+)(\\p{Print}+?)(?:\\s+)(?:alors|then)(?:\\s+)(\\p{Print}+)(?:\\s+)(?:sinon|else)(?:\\s+)(\\p{Print}+)(?:\\s+)(?:fin|end)";
    }

    /**
     * Retourne l'expression régulière JAVA d'un bloc si cond alors stuff fin
     * 
     * @return L'expression régulière JAVA siAlors
     */
    private static String siAlorsRegex() {
        return "(?:si|if)(?:\\s+)(\\p{Print}+?)(?:\\s+)(?:alors|then)(?:\\s+)(\\p{Print}+)(?:\\s+)(fin|end)";
    }
}
