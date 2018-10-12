package fr.rigaud;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * Test de la classe Arrivee.
 * 
 * On test si le temps passé dans le complexe correspond bien au prix
 * que l'usager doit payer.
 * 
 * Tarifs :
 *  ~ <= 1/4h = 0€
 *  ~ <= 1/2h = 0.5€
 *  ~ <= 1h = 1€
 *  ~ Tout 1/4h au-delà de 1h = 0.5€
 */
public class ArriveeTest {
	@Test
	public void testCalculPrix() {
		final double DELTA = 0.1;
		Complexe comp = new Complexe(3,3,"yoman");
		Arrivee ar = new Arrivee(comp,'M');
		
		assertEquals(0.0,ar.calculPrix(14),DELTA);//0€ car <= 1/4h
		assertEquals(0.5,ar.calculPrix(20),DELTA);//0.5€ car <= 1/2h
		assertEquals(1.0,ar.calculPrix(40),DELTA);//1€ car <= 1h
		assertEquals(1.5,ar.calculPrix(75),DELTA);//1.5€ car 1h + 1/4h
		assertEquals(2.0,ar.calculPrix(90),DELTA);//2€ car 1h + 1/4h + 1/4h
	}

}
