package texgen.vue;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * @author fanny
 */
@SuppressWarnings("serial")
public class FenetrePrincipale extends JFrame {
    /**
     * 
     */
    private BarreMenu  menuBar;
    /**
     * 
     */
    private PseudoCode pseudoCode;
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
        menuBar = new BarreMenu(this);
        add(menuBar, BorderLayout.NORTH);

        pseudoCode = new PseudoCode();
        separateurH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(pseudoCode), new JScrollPane());

        separateurV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, separateurH, new JScrollPane());

        add(separateurV, BorderLayout.CENTER);

        setVisible(true);
    }

    public PseudoCode getPseudoCode() {
        return pseudoCode;
    }
}
