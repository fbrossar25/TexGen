package texgen.vue;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import texgen.modele.PseudoCode;

/**
 * @author fanny
 *
 */
public class ZoneTexte extends JPanel {
	/**
	 * 
	 */
	private PseudoCode pseudoCode;

	/**
	 * 
	 */
	public ZoneTexte() {
		super();
		pseudoCode = new PseudoCode();
		add(new JScrollPane(pseudoCode));
	}

	public PseudoCode getPseudoCode() {
		return pseudoCode;
	}
}
