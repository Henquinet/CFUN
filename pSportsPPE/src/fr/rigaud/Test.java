package fr.rigaud;



public class Test {
	
	
	
	public static void main(String[] args) {
		Complexe comp = new Complexe("sdf");
		Arrivee ar = new Arrivee(comp,'M');
		ar.genBarcode();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Arrivee ar2 = new Arrivee(comp,'M');
		System.out.println(ar2.genBarcode());
	}
	
	
}
