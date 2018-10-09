package fr.rigaud;

import static org.junit.Assert.*;


import org.junit.*;
import org.junit.Test;

public class ChoixCouleurTest {

	@Test
	public void testChoixCouleur() {
		ChoixCouleur coul = new ChoixCouleur(0.5);
		assertEquals(Couleur.vert,coul.getCouleur());
		
		coul = new ChoixCouleur(0.72);
		assertEquals(Couleur.orange,coul.getCouleur());
		
		coul = new ChoixCouleur(1);
		assertEquals(Couleur.rouge,coul.getCouleur());
	}
	
}
