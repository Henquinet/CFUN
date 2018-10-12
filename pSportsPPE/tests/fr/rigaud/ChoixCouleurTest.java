package fr.rigaud;

import static org.junit.Assert.*;


import org.junit.Test;

/*
 * Test de la classe ChoixCouleur.
 * 
 * On test si le taux d'occupation (en %) correspond bien à la couleur affichée.
 * 
 * Couleurs en fonction des états : 
 *  ~ vert < 70%
 *  ~ 70% <= orange < 100%
 *  ~ rouge = 100%  
 */
public class ChoixCouleurTest {
	@Test
	public void testChoixCouleur() {
		ChoixCouleur coul = new ChoixCouleur(0.5);//Taux d'utilisation de 50%
		assertEquals(Couleur.vert,coul.getCouleur());//Donc couleur verte
		
		coul = new ChoixCouleur(0.72);//Taux d'utilisation à 72%
		assertEquals(Couleur.orange,coul.getCouleur());//Donc couleur orange
		
		coul = new ChoixCouleur(1);//Taux d'utilisation à 100%
		assertEquals(Couleur.rouge,coul.getCouleur());//Donc couleur rouge
	}
	
}
