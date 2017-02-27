package texgen.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import texgen.modele.InfosPresentation;
import texgen.vue.Graph.EtatParcours;

/**
 * Boîte de dialogue permettant de personnaliser les couleurs utilisées dans le graph
 * 
 * @author BROSSARD Florian
 * @author MILLOTTE Fanny
 *
 */
@SuppressWarnings("serial")
public class ColorChooser extends JDialog implements ChangeListener, ActionListener {
    /** les informations de la présentation */
    private InfosPresentation       infos;
    /** le menu déroulant permettant de choisir un état */
    private JComboBox<EtatParcours> stateSelector;
    /** le bouton radio permettant d'indiquer si l'on modifie la couleurs des noeuds */
    private JRadioButton            noeuds;
    /** le bouton radio permettant d'indiquer si l'on modifie la couleurs des liens */
    private JRadioButton            liens;
    /** le sélecteur de couleur JAVA */
    private JColorChooser           colorChooser;

    /**
     * Constructeur de la classe
     * 
     * @param infos
     *            les informations de la présentation
     */
    public ColorChooser(InfosPresentation infos) {
        super();
        setLayout(new BorderLayout());
        this.setTitle("Choix des couleurs");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        this.infos = infos;
        // Panel permettant de sélectionner l'états et le type (noeuds ou liens) de couleursque l'on souhaite modifié
        JPanel selectorsPanel = new JPanel(new GridLayout(1, 3));
        ButtonGroup grp = new ButtonGroup();
        noeuds = new JRadioButton("noeuds");
        grp.add(noeuds);
        selectorsPanel.add(noeuds);
        noeuds.addActionListener(this);
        liens = new JRadioButton("liens");
        grp.add(liens);
        selectorsPanel.add(liens);
        liens.addActionListener(this);
        noeuds.setSelected(true);
        stateSelector = new JComboBox<EtatParcours>();
        for (EtatParcours etat : EtatParcours.values()) {
            stateSelector.addItem(etat);
        }
        stateSelector.addActionListener(this);
        selectorsPanel.add(stateSelector);
        add(selectorsPanel, BorderLayout.NORTH);

        colorChooser = new JColorChooser(infos.getCouleursNoeuds().get(stateSelector.getSelectedItem()));
        colorChooser.getSelectionModel().addChangeListener(this);
        add(colorChooser, BorderLayout.CENTER);

        // Bouton permettant de fermer la boîte de dialogue
        // les modifs sont enregistrer même si l'on ferme la boîte de dialogue autrement
        JButton valider = new JButton("Valider");
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(valider, BorderLayout.SOUTH);
        pack();
        setResizable(false);
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        // Quelque soit la couleur et la sélection, on effectus le même traitement
        // Pas besoin de connaître la source
        Color c = colorChooser.getColor();
        if (noeuds.isSelected()) {
            infos.getCouleursNoeuds().put((EtatParcours) stateSelector.getSelectedItem(), c);
        } else if (liens.isSelected()) {
            infos.getCouleursLiens().put((EtatParcours) stateSelector.getSelectedItem(), c);
        }
        infos.getFenetre().repaint();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (noeuds.isSelected()) {
            colorChooser.setColor(infos.getCouleursNoeuds().get(stateSelector.getSelectedItem()));
        } else if (liens.isSelected()) {
            colorChooser.setColor(infos.getCouleursLiens().get(stateSelector.getSelectedItem()));
        }
    }
}
