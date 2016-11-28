package texgen.vue;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author fanny
 *
 */
public class ZoneTexte extends JPanel {
	/**
	 * 
	 */
	private JTextArea texte;

	/**
	 * 
	 */
	public ZoneTexte() {
		super();
		texte = new JTextArea(10, 30);
		texte.append("Entrez votre pseudo-code.");
		add(new JScrollPane(texte));
	}
}
