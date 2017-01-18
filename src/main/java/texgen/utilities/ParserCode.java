package texgen.utilities;

import java.util.HashMap;
import java.util.regex.Pattern;

public class ParserCode {

    public static enum typeBlocs {
        faireTantQue, tantQueFaire, pour, siAlors, siAlorsSinon,
    }

    public static final HashMap<typeBlocs, Pattern> patterns = initPatterns();

    /**
     * Fonction parsant le texte donné en remplaçant tout les blocs énumérés dans typeBlocs par leurs équivalant LaTeX
     * 
     * @param text
     *            le texte à parser
     * @return Le code LaTeX correspondant au texte
     */
    public static String parseText(String text) {
        String res = "";
        // StringTokenizer line = new StringTokenizer(myString, "\t"); line.nextToken()
        res = patterns.get(typeBlocs.siAlorsSinon).matcher(text).replaceFirst("\\uIf{$3}{$7}{$11}");
        return res;
    }

    /**
     * Fonction qui initialise les patterns utilisés pour parser le code
     * 
     * @return la hashMap des patterns
     */
    private static HashMap<typeBlocs, Pattern> initPatterns() {
        HashMap<typeBlocs, Pattern> patterns = new HashMap<>();
        patterns.put(typeBlocs.siAlorsSinon, Pattern.compile(siAlorsSinonRegex(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.UNICODE_CHARACTER_CLASS));
        patterns.put(typeBlocs.siAlors, Pattern.compile(siAlorsRegex(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.UNICODE_CHARACTER_CLASS));
        return patterns;
    };

    /**
     * Retourne l'expression régulière JAVA d'un bloc si cond alors stuff sinon stuff fin
     * 
     * @return L'expression régulière JAVA siAlorsSinonInline
     */
    private static String siAlorsSinonRegex() {
        // ReplaceFirestWith("\\uIf{$3}{$7}{$11}");
        return "(Si)(\\s+)((?:\\p{Print}+?))(\\s+)(alors)(\\s+)((?:\\p{Print}+))(\\s+)(sinon)(\\s+)((?:\\p{Print}+))(\\s+)(fin)";
    }

    /**
     * Retourne l'expression régulière JAVA d'un bloc si cond alors stuff fin
     * 
     * @return L'expression régulière JAVA siAlors
     */
    private static String siAlorsRegex() {
        String re1 = "(Si)"; // Word 1
        String re2 = "(\\s+)"; // White Space 1
        String re3 = "((?:[a-z][a-z]+)\\s*)*"; // Word 2
        String re4 = "(\\s+)"; // White Space 2
        String re5 = "(alors)"; // Word 3
        String re6 = "(\\s+)"; // White Space 3
        String re7 = "((?:[a-z][a-z]+))"; // Word 4
        String re8 = "(\\s+)"; // White Space 4
        String re9 = "(fin)"; // Word 5
        return (re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9);
    }
}
