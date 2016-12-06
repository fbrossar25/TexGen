package texgen.modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import javax.swing.JTextArea;

/**
 * @author Florian
 *
 */
public class PseudoCode extends JTextArea {

	/**
	 * ArrayList (les pas) de TreeSet d'entier (les lignes marquees dans ce
	 * pas).
	 */
	ArrayList<TreeSet<Integer>> marqueurs;

	/**
	 * 
	 */
	public PseudoCode() {
		super("Saisissez votre pseudo code ici.", 10, 30);
		marqueurs = new ArrayList<TreeSet<Integer>>();
	}

	/**
	 * Retourne les lignes sous forme d'une ArrayList de chaine de caractere.
	 * 
	 * @return Les lignes sous forme d'une chaine de caractere.
	 */
	public ArrayList<String> getLines() {
		String s[] = getText().split("\\r?\\n");
		ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(s));
		return stringList;
	}

	/**
	 * Retourne le nombre de lignes non vides de la zone de texte.
	 * 
	 * @return Le nombre de lignes non vides de la zone de texte.
	 */
	public int getNombreLignes() {
		return getLineCount();
	}

	/**
	 * Retourne true si la ligne ligne pour le pas est marquee, false sinon.
	 * 
	 * @param pas
	 *            Le pas.
	 * @param ligne
	 *            La ligne.
	 * @return True si la ligne est marquee,false sinon.
	 */
	public boolean estMarquee(int pas, int ligne) {
		return marqueurs.get(pas).contains(ligne);
	}

	/**
	 * Fonction privee qui marque une ligne pour un pas donne.
	 * 
	 * @param pas
	 *            Le pas.
	 * @param ligne
	 *            La ligne.
	 */
	private void mark(int pas, int ligne) {
		if (!marqueurs.get(pas).contains(ligne)) {
			marqueurs.get(pas).add(ligne);
		}
	}

	/**
	 * Fonction privee qui enleve la marque d'une ligne pour un pas donne.
	 * 
	 * @param pas
	 *            Le pas.
	 * @param ligne
	 *            La ligne.
	 */
	private void unmark(int pas, int ligne) {
		if (marqueurs.get(pas).contains(ligne)) {
			marqueurs.get(pas).remove(ligne);
		}
	}

	/**
	 * Retourne le nombre de pas du pseudo code.
	 * 
	 * @return Le nombre de pas du pseudo code.
	 */
	public int getNombrePas() {
		return marqueurs.size();
	}

	/**
	 * Fonction qui gere le marquage / non marquage d'une ligne pour un pas
	 * donne.
	 * 
	 * @param pas
	 *            Le pas.
	 * @param ligne
	 *            La ligne.
	 */
	public void marquage(int pas, int ligne) {
		if (ligne < getNombreLignes() && ligne >= 0) {
			if (pas < getNombrePas() && pas >= 0)
				if (estMarquee(pas, ligne)) {
					mark(pas, ligne);
				} else {
					unmark(pas, ligne);
				}
		}
	}
}
