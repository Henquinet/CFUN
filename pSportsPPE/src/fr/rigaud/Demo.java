package fr.rigaud;

public class Demo {

	public static void main(String[] args) {
		Complexe comp = new Complexe("Le complexe");
		comp.entreeUsager(new Arrivee(comp,'M'));
		System.out.println(comp.sortieUsager(1).getMontant());
		
		
	}
}
