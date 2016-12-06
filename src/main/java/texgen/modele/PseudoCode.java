package texgen.modele;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import javax.swing.JComponent;
import javax.swing.JTextArea;

/**
 * @author Florian
 *
 */
public class PseudoCode extends JComponent {

	/**
	 * ArrayList (les pas) de TreeSet d'entier (les lignes marquees dans ce
	 * pas).
	 */
	private ArrayList<TreeSet<Integer>> marqueurs;

	private JTextArea textArea;

	/**
	 * 
	 */
	public PseudoCode() {
		super();
		textArea = new JTextArea("Saisissez votre pseudo code ici.", 10, 30);
		add(textArea);
		marqueurs = new ArrayList<TreeSet<Integer>>();
	}

	/**
	 * Retourne les lignes sous forme d'une ArrayList de chaine de caractere.
	 * 
	 * @return Les lignes sous forme d'une chaine de caractere.
	 */
	public ArrayList<String> getLignes() {
		String s[] = textArea.getText().split("\\r?\\n");
		ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(s));
		return stringList;
	}

	/**
	 * Retourne le nombre de lignes non vides de la zone de texte.
	 * 
	 * @return Le nombre de lignes non vides de la zone de texte.
	 */
	public int getNombreLignes() {
		return textArea.getLineCount();
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
		if (pas >= 0 && pas < getNombrePas() && ligne >= 0 && ligne < getNombreLignes())
			return marqueurs.get(pas).contains(ligne);
		else
			return false;
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
	 * Ajoute un pas.
	 */
	public void ajouterPas() {
		marqueurs.add(new TreeSet<Integer>());
	}

	/**
	 * Retourne le pas i.
	 * 
	 * @param i
	 *            L'index du pas.
	 * @return Le pas.
	 */
	public TreeSet<Integer> getPas(int i) {
		return marqueurs.get(i);
	}

	/**
	 * Supprime le pas i.
	 * 
	 * @param i
	 *            L'index du pas.
	 */
	public void supprimerPas(int i) {
		if (i < marqueurs.size()) {
			marqueurs.remove(i);
		}
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
			if (pas < getNombrePas() && pas >= 0) {
				if (estMarquee(pas, ligne)) {
					unmark(pas, ligne);
				} else {
					mark(pas, ligne);
				}
			}
		}
	}

	/**
	 * Retourne le JTextArea de cette instance.
	 * 
	 * @return Le JTextArea de cette instance.
	 */
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * Ajout des numeros des lignes.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// TODO
	}
}
