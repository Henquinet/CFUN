package fr.rigaud;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArriveeTest {
	
	@SuppressWarnings("deprecation")
	@Test
	public void testCalculPrix() {
		final double DELTA = 0.1;
		Complexe comp = new Complexe(3,3,"yoman");
		Arrivee ar = new Arrivee(comp,'M');
		
		assertEquals(0.0,ar.calculPrix(14),DELTA);
		assertEquals(0.5,ar.calculPrix(20),DELTA);
		
		assertEquals(1.0,ar.calculPrix(40),DELTA);
		assertEquals(1.5,ar.calculPrix(75),DELTA);
		assertEquals(2.0,ar.calculPrix(90),DELTA);
	//	assertEquals(0.0,ar.calculPrix(14),DELTA);
	}

}
