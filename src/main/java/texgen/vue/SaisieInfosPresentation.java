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
import javax.swing.JTextField;

import texgen.modele.InfosPresentation;
import texgen.utilities.FileUtilities;
import texgen.utilities.GenerateurLatex;

@SuppressWarnings("serial")
public class SaisieInfosPresentation extends JDialog {
    private ArrayList<JTextField> fields;
    private JButton               valider;
    private JButton               reset;
    private InfosPresentation     infos;
    private FenetrePrincipale     fen;

    public SaisieInfosPresentation(FenetrePrincipale fen, InfosPresentation infos) {
        super();
        this.setTitle("Informations sur la présentation");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        fields = new ArrayList<>();
        this.infos = infos;
        this.fen = fen;

        JPanel fieldsPane = new JPanel(new GridLayout(6, 2));

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

        JLabel jl6 = new JLabel("Date / Information complémentaire :");
        fieldsPane.add(jl6);
        JTextField tf6 = new JTextField(infos.getDate());
        fieldsPane.add(tf6);
        fields.add(tf6);

        add(fieldsPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));

        initForm();
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
    }

    public FenetrePrincipale getFenetre() {
        return fen;
    }

    public String getNomAuteur() {
        return fields.get(0).getText();
    }

    public String getTitre() {
        return fields.get(1).getText();
    }

    public String getTitreCourt() {
        return fields.get(2).getText();
    }

    public String getSousTitre() {
        return fields.get(3).getText();
    }

    public String getTitreAlgo() {
        return fields.get(4).getText();
    }

    public String getDate() {
        return fields.get(5).getText();
    }

    public void updateInfos() {
        infos.setNomAuteur(getNomAuteur());
        infos.setTitrePresentation(getTitre());
        infos.setTitrePresentationCourt(getTitreCourt());
        infos.setSousTitrePresentation(getSousTitre());
        infos.setTitreAlgo(getTitreAlgo());
        infos.setDate(getDate());
    }

    private void initForm() {
        fields.get(0).setText(infos.getNomAuteur());
        fields.get(1).setText(infos.getTitrePresentation());
        fields.get(2).setText(infos.getTitrePresentationCourt());
        fields.get(3).setText(infos.getSousTitrePresentation());
        fields.get(4).setText(infos.getTitreAlgo());
        fields.get(5).setText(infos.getDate());
        pack();
    }

    public void reset() {
        infos.reset();
        for (JTextField f : fields) {
            f.setText("");
        }
    }

    public InfosPresentation getInfos() {
        return infos;
    }
}
