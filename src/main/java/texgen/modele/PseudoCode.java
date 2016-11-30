package texgen.modele;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTextArea;

/**
 * @author Florian
 *
 */
public class PseudoCode extends JTextArea {

	public PseudoCode() {
		super("Saisissez votre pseudo code ici.", 10, 30);
	}

	/**
	 * Retourne les lignes sous forme d'une ArrayList de chaine de caractere.
	 * 
	 * @return Les lignes sous forme d'une chaine de caractere.
	 */
	public ArrayList<String> getLines() {
		String s[] = this.getText().split("\\r?\\n");
		ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(s));
		return stringList;
	}
}
