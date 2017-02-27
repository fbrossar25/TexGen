package texgen.vue;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;

import texgen.modele.Lien;
import texgen.modele.Noeud;
import texgen.vue.Graph.EtatParcours;

/**
 * Ensembles de tests pour le graph
 * 
 * @author BROSSARD Florian
 * @author MILLOTTE Fanny
 */
public class GraphTest {

    /**
     * Test la création d'un lien et la suppression d'un noeud associé
     */
    @Test
    public void testLien() {
        Graph g = new Graph(null);
        Noeud n1 = g.creerNoeud("1", 0, 0);
        Noeud n2 = g.creerNoeud("2", 25, 25);
        Lien l = g.creerLien("a", n1, n2);
        assertEquals(n1, l.getDepart());
        assertEquals(n2, l.getArrive());
        g.supprimerNoeud(n2);
        assertEquals(n1, g.getNoeud(0));
        assertEquals(0, g.getNombreLiens());
        n2 = g.creerNoeud("2", 25, 25);
        l = g.creerLien("a", n1, n2);
        assertEquals(n1, l.getDepart());
        assertEquals(n2, l.getArrive());
        g.supprimerNoeud(n1);
        assertEquals(n2, g.getNoeud(0));
        assertEquals(0, g.getNombreLiens());
    }

    /**
     * Test la récupération des noeuds et des infos associées
     */
    @Test
    public void testNoeuds() {
        Graph g = new Graph(null);
        Noeud n1 = g.creerNoeud("1");
        Noeud n2 = g.creerNoeud("2", 25, 25);
        g.changerEtatNoeudDiapoCourante(n2, EtatParcours.Actif);
        assertEquals(n1, g.getNoeud("1"));
        assertEquals(new Point(0, 0), n1.getPosition());
        assertEquals(new Point(25, 25), n2.getPosition());
        assertEquals(EtatParcours.Inactif, g.getEtatCourantNoeud(n1));
        assertEquals(EtatParcours.Actif, g.getEtatCourantNoeud(n2));
        g.ajouterDiapo();
        assertEquals(EtatParcours.Parcourus, g.getEtatNoeudDiapo(n2, 2));
    }

}
