package texgen.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.TreeSet;

import org.junit.Test;

/**
 * @author Florian
 *
 */
public class PseudoCodeTest {

	@Test
	public void testGetLignes() {
		// fail("Not yet implemented");
		ArrayList<String> stringArray = new ArrayList<String>();
		stringArray.add("Saisissez votre pseudo code ici.");
		PseudoCode p = new PseudoCode();
		assertEquals(stringArray, p.getLignes());
	}

	@Test
	public void testGetNombreLignes() {
		PseudoCode p = new PseudoCode();
		assertEquals(1, p.getNombreLignes());
	}

	@Test
	public void testPas() {
		PseudoCode p = new PseudoCode();
		assertEquals(0, p.getNombrePas());
		p.supprimerPas(10);
		assertEquals(0, p.getNombrePas());
		p.getTextArea().setText("");
		assertEquals(1, p.getNombreLignes());
		for (int i = 0; i < 100; i++) {
			p.ajouterPas();
			assertEquals(i + 1, p.getNombrePas());
			p.marquage(i, i);
			assertTrue(p.estMarquee(i, i));
			p.getTextArea().append("Test" + ((i < 99) ? "\n" : ""));
		}
		assertEquals(100, p.getNombrePas());
		assertEquals(100, p.getNombreLignes());
		TreeSet<Integer> t = new TreeSet<Integer>();
		for (int i = 0; i < 100; i++) {
			t.add(i);
			assertEquals(t, p.getPas(i));
			t.remove(i);
		}
		for (int i = 0; i < 100; i++) {
			p.ajouterPas();
			p.marquage(i, i);
		}
		t = new TreeSet<Integer>();
		for (int i = 0; i < 100; i++) {
			assertEquals(t, p.getPas(i));
			assertFalse(p.estMarquee(i, i));
		}
	}

	@Test
	public void testMarquage() {
		PseudoCode p = new PseudoCode();
		for (int i = 0; i < 100; i++) {
			assertFalse(p.estMarquee(0, i));
		}
	}

}
