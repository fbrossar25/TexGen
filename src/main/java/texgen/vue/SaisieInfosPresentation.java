package texgen.vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import texgen.modele.InfosPresentation;
import texgen.utilities.FileUtilities;
import texgen.utilities.GenerateurLatex;
import texgen.utilities.SpringUtilities;

/**
 * Boîte de dialogue permettant de saisir les informations de la présentations (auteur, titre de l'algorithme,...)
 * 
 * @author BROSSARD Florian
 * @author MILLOTTE Fanny
 *
 */
@SuppressWarnings("serial")
public class SaisieInfosPresentation extends JDialog {
    /** la liste des champs de saisie */
    private ArrayList<JTextField> fields;
    /** le bouton valider */
    private JButton               valider;
    /** le bouton reset */
    private JButton               reset;
    /** les informations de la présentation */
    private InfosPresentation     infos;
    /** la fenêtre principale */
    private FenetrePrincipale     fen;
    /** le champ de saisie de l'espacement entre le pseudoCode et le tableau */
    private JSpinner              hspaceCodeTabField;
    /** le champ de saisie de la taille du pseudoCode */
    private JSpinner              codeSizeField;
    /** le champ de saisie de la taille du tableau */
    private JSpinner              tabSizeField;

    /**
     * Constructeur de la classe
     * 
     * @param fen
     *            la fenêtre principale
     * @param infos
     *            les informations de la présentation
     */
    public SaisieInfosPresentation(FenetrePrincipale fen, InfosPresentation infos) {
        super();
        this.setTitle("Informations sur la présentation");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        fields = new ArrayList<>();
        this.infos = infos;
        this.fen = fen;

        JPanel fieldsPane = new JPanel(new SpringLayout());
        // définition des différents champs de saisie
        JLabel jl1 = new JLabel("Auteur :");
        fieldsPane.add(jl1);
        JTextField tf1 = new JTextField(infos.getNomAuteur());
        fieldsPane.add(tf1);
        fields.add(tf1);

        JLabel jl2 = new JLabel("Titre de la présentation :");
        fieldsPane.add(jl2);
        JTextField tf2 = new JTextField(infos.getTitrePresentation());
        fieldsPane.add(tf2);
        fields.add(tf2);

        JLabel jl3 = new JLabel("Titre court :");
        fieldsPane.add(jl3);
        JTextField tf3 = new JTextField(infos.getTitrePresentationCourt());
        fieldsPane.add(tf3);
        fields.add(tf3);

        JLabel jl4 = new JLabel("Sous-titre :");
        fieldsPane.add(jl4);
        JTextField tf4 = new JTextField(infos.getSousTitrePresentation());
        fieldsPane.add(tf4);
        fields.add(tf4);

        JLabel jl5 = new JLabel("Titre de l'agorithme:");
        fieldsPane.add(jl5);
        JTextField tf5 = new JTextField(infos.getTitreAlgo());
        fieldsPane.add(tf5);
        fields.add(tf5);

        JLabel jl6 = new JLabel("Institut :");
        fieldsPane.add(jl6);
        JTextField tf6 = new JTextField(infos.getInstitut());
        fieldsPane.add(tf6);
        fields.add(tf6);

        JLabel jl7 = new JLabel("Date / Information complémentaire :");
        fieldsPane.add(jl7);
        JTextField tf7 = new JTextField(infos.getDate());
        fieldsPane.add(tf7);
        fields.add(tf7);

        JLabel jl8 = new JLabel("Espacement pseudoCode-tableau (0-15 cm) :");
        fieldsPane.add(jl8);
        hspaceCodeTabField = new JSpinner(new SpinnerNumberModel(infos.getHSpaceCodeTab(), 0.0, 15.0, 0.01));
        fieldsPane.add(hspaceCodeTabField);
        JLabel jl9 = new JLabel("Taille du pseudoCode (0-15 cm) :");
        fieldsPane.add(jl9);
        codeSizeField = new JSpinner(new SpinnerNumberModel(infos.getCodeSize(), 0.0, 15.0, 0.01));
        fieldsPane.add(codeSizeField);
        JLabel jl10 = new JLabel("Taille du tableau (0-15 cm) :");
        fieldsPane.add(jl10);
        tabSizeField = new JSpinner(new SpinnerNumberModel(infos.getTabSize(), 0.0, 15.0, 0.01));
        fieldsPane.add(tabSizeField);

        // fields.size() + 3 pour le nombre de champs de texte et les 3 JSpinner
        SpringUtilities.makeGrid(fieldsPane, fields.size() + 3, 2, 0, 0, 5, 2);
        add(fieldsPane, BorderLayout.CENTER);

        // Définition des boutons valider et reset
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        valider = new JButton("Valider");
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInfos();
                String fullPath = FileUtilities.selectFileWithFilter(fen, "Fichier LaTeX .tex", "tex");
                if (fullPath == null || fullPath.equals("")) {
                    return;
                }
                FileUtilities.writeStringInFile(GenerateurLatex.generer(fen), fullPath, true);
                dispose();
            }
        });
        buttonsPanel.add(valider);

        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                reset();
            }
        });
        buttonsPanel.add(reset);
        add(buttonsPanel, BorderLayout.SOUTH);
        pack();
    }

    /**
     * Retourne la fenêtre principale de l'application
     * 
     * @return la fenêtre principale de l'application
     */
    public FenetrePrincipale getFenetre() {
        return fen;
    }

    /**
     * Retourne le nom de l'auteur saisis
     * 
     * @return le nom de l'auteur saisis
     */
    public String getNomAuteur() {
        return fields.get(0).getText();
    }

    /**
     * Retourne le titre de la présentation saisis
     * 
     * @return le titre de la présentation saisis
     */
    public String getTitre() {
        return fields.get(1).getText();
    }

    /**
     * Retourne le titre raccourcis de la présentation saisis
     * 
     * @return le titre raccourcis de la présentation saisis
     */
    public String getTitreCourt() {
        return fields.get(2).getText();
    }

    /**
     * Retourne le sous-titre de la présentation saisis
     * 
     * @return le sous-titre de la présentation saisis
     */
    public String getSousTitre() {
        return fields.get(3).getText();
    }

    /**
     * Retourne le titre de l'algorithme saisis
     * 
     * @return le titre de l'algorithme saisis
     */
    public String getTitreAlgo() {
        return fields.get(4).getText();
    }

    /**
     * Retourne le nom de l'institut saisis
     * 
     * @return le nom de l'institut saisis
     */
    public String getInstitut() {
        return fields.get(5).getText();
    }

    /**
     * Retourne la date saisie
     * 
     * @return la date saisie
     */
    public String getDate() {
        return fields.get(6).getText();
    }

    /**
     * Met à jour les informations stockées
     */
    public void updateInfos() {
        infos.setNomAuteur(getNomAuteur());
        infos.setTitrePresentation(getTitre());
        infos.setTitrePresentationCourt(getTitreCourt());
        infos.setSousTitrePresentation(getSousTitre());
        infos.setTitreAlgo(getTitreAlgo());
        infos.setInstitut(getInstitut());
        infos.setDate(getDate());
        infos.setHSpaceCodeTab((double) hspaceCodeTabField.getModel().getValue());
        infos.setCodeSize((double) codeSizeField.getModel().getValue());
        infos.setTabSize((double) tabSizeField.getModel().getValue());
    }

    /**
     * Redéfinis les informations saisies à la chaînes vide ainsi que leurs correspondance dans les informations<br>
     * stockées (les couleurs ne sont pas modifiées)
     */
    public void reset() {
        infos.setNomAuteur("");
        infos.setDate("");
        infos.setTitreAlgo("");
        infos.setSousTitrePresentation("");
        infos.setTitrePresentation("");
        infos.setTitrePresentationCourt("");
        infos.setInstitut("");
        infos.setHSpaceCodeTab(0.0);
        infos.setCodeSize(5.0);
        infos.setTabSize(5.0);
        hspaceCodeTabField.getModel().setValue(0.0);
        codeSizeField.getModel().setValue(5.0);
        tabSizeField.getModel().setValue(5.0);
        for (JTextField f : fields) {
            f.setText("");
        }
    }

    /**
     * Retourne les informations de la présentation
     * 
     * @return les informations de la présentation
     */
    public InfosPresentation getInfos() {
        return infos;
    }
}
