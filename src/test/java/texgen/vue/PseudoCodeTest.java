package texgen.vue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.TreeSet;

import org.junit.Test;

/**
 * @author Florian
 */
public class PseudoCodeTest {

    @Test
    public void testGetLignes() {
        // fail("Not yet implemented");
        ArrayList<String> stringArray = new ArrayList<String>();
        stringArray.add("Saisissez votre pseudo code ici.");
        PseudoCode p = new PseudoCode();
        assertEquals(stringArray, p.getLignes());
        p.getTextArea().append("\nTest");
        assertEquals("Test", p.getLigne(2));
    }

    @Test
    public void testGetNombreLignes() {
        PseudoCode p = new PseudoCode();
        assertEquals(1, p.getNombreLignes());
    }

    @Test
    public void testDiapos() {
        PseudoCode p = new PseudoCode(100);
        assertEquals(1, p.getNombreDiapos());
        p.supprimerDiapo(10);
        assertEquals(1, p.getNombreDiapos());
        assertNull(p.getDiapo(10));
        p.getTextArea().setText("");
        assertEquals(1, p.getNombreLignes());
        p.getTextArea().append("\nTest");
        assertEquals(2, p.getNombreLignes());
        p.getTextArea().setText("");
        for (int i = 1; i <= 100; i++) {
            p.getTextArea().append("Test" + ((i < 100) ? "\n" : ""));
            p.marquage(1, i);
            assertTrue(p.estMarquee(1, i));
        }

        assertEquals(100, p.getNombreLignes());
        TreeSet<Integer> t = new TreeSet<Integer>();

        for (int i = 1; i <= 100; i++) {
            t.add(i);
        }
        assertEquals(t, p.getDiapo(1));
    }

    @Test
    public void testMarquage() {
        PseudoCode p = new PseudoCode();
        p.ajouterDiapo();
        p.marquage(2, 4);
        for (int i = 0; i < 5; i++) {
            p.getTextArea().append("Test\n");
        }
        p.marquage(2, 5);
        p.marquage(2, 6);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((i == 2) && (j == 5)) {
                    assertTrue(p.estMarquee(i, j));
                } else {
                    assertFalse(p.estMarquee(i, j));
                }
            }
        }
    }

}
