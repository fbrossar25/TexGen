package texgen.vue;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * @author fanny
 *
 */
public class FenetrePrincipale extends JFrame {
	/**
	 * 
	 */
	private BarreMenu menuBar;
	/**
	 * 
	 */
	private ZoneTexte zoneTexte;
	/**
	 * 
	 */
	private JSplitPane separateurV;
	/**
	 * 
	 */
	private JSplitPane separateurH;

	/**
	 * 
	 */
	public FenetrePrincipale() {
		setTitle("TexGen");
		this.setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		menuBar = new BarreMenu();
		add(menuBar, BorderLayout.NORTH);

		zoneTexte = new ZoneTexte();
		separateurH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(zoneTexte), new JScrollPane());

		separateurV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, separateurH, new JScrollPane());

		add(separateurV, BorderLayout.CENTER);

		setVisible(true);
	}
}
