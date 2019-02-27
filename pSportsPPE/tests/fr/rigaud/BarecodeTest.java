package fr.rigaud;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.nio.file.*;

public class BarecodeTest {

	
	@Test
	public void genBarcode() {
		Complexe c = new Complexe("");
		Arrivee ar = new Arrivee(c,'m');
		String path = ar.genBarcode();
		File f = new File(path);
		if(!f.canRead()){
			fail("Fichier code-barre non généré : " + path);
		}
	}
}
