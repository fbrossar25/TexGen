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

@SuppressWarnings("serial")
public class ColorChooser extends JDialog implements ChangeListener, ActionListener {
    private InfosPresentation       infos;
    private JComboBox<EtatParcours> stateSelector;
    private JRadioButton            noeuds;
    private JRadioButton            liens;
    private JColorChooser           colorChooser;

    public ColorChooser(InfosPresentation infos) {
        super();
        setLayout(new BorderLayout());
        this.setTitle("Choix des couleurs");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        this.infos = infos;
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

        colorChooser = new JColorChooser(infos.getCouleursNoeuds().get(stateSelector.getSelectedItem()));
        colorChooser.getSelectionModel().addChangeListener(this);
        add(colorChooser, BorderLayout.CENTER);

        selectorsPanel.add(stateSelector);
        add(selectorsPanel, BorderLayout.NORTH);
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
