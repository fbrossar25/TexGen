package texgen.modele;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

/**
 * @author Florian
 *
 */
public class PseudoCodeTest {

	@Test
	public void testGetLines() {
		// fail("Not yet implemented");
		ArrayList<String> stringArray = new ArrayList<String>();
		stringArray.add("Saisissez votre pseudo code ici.");
		PseudoCode p = new PseudoCode();
		assertEquals(stringArray, p.getLines());
	}

}
