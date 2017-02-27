package texgen.vue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Ensemble de tests pour le tableau
 * 
 * @author BROSSARD Florian
 * @author MILLOTTE Fanny
 */
public class TableauTest {

    /**
     * Test la détection de changement de variables
     */
    @Test
    public void testMarquage() {
        Tableau t = new Tableau(null);
        int li = t.getNombreLignes();
        assertEquals(2, li);
        for (int i = 0; i < li; i++) {
            assertFalse(t.estMarquee(1, 1, i));
        }
        t.getDiapo(1).setValueAt("test", 1, 2);
        assertTrue(t.estMarquee(1, 1, 2));
        t.ajouterColonne();
        t.ajouterLigne();
        t.ajouterDiapo();
        assertTrue(t.estMarquee(1, 1, 2));
        t.diapoSuivante();
        assertFalse(t.estMarquee(1, 2));
    }

    /**
     * Test la répercussion de modifications de valeurs
     */
    @Test
    public void testModifs() {
        Tableau t = new Tableau(null);
        for (int i = 0; i < 99; i++) {
            t.ajouterDiapo();
        }
        for (int diapo = 1; diapo <= t.getNombreDiapos(); diapo++) {
            for (int i = 0; i < t.getNombreColonnes(); i++) {
                assertEquals("", t.getDiapo(diapo).getValueAt(0, i));
            }
        }
        t.setValueAt("A", 1, 0, 0);
        for (int diapo = 1; diapo <= t.getNombreDiapos(); diapo++) {
            for (int i = 0; i < t.getNombreColonnes(); i++) {
                assertEquals((i == 0) ? "A" : "", t.getDiapo(diapo).getValueAt(0, i));
            }
        }
        String[] l = { "A", "", "", "" };
        assertEquals(l.length, t.getNomsColonnes().size());
        for (int i = 0; i < l.length; i++) {
            assertEquals(l[i], t.getNomsColonnes().get(i));
        }

        t.setValueAt("a", 1, 1, 1);
        ArrayList<String> values = t.getCellValues(1, 1);
        assertEquals(t.getNombreDiapos(), values.size());
        for (int i = 1; i <= t.getNombreDiapos(); i++) {
            assertEquals("a", t.getDiapo(i).getValueAt(1, 1));
            assertEquals("a", values.get(i - 1));
        }
    }
}
