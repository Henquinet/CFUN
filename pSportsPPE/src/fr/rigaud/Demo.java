package fr.rigaud;
import java.awt.HeadlessException;



public class Demo {

	public static void main(String[] args) {
		
		Complexe comp = new Complexe(4,4,"Le complexe");
		Arrivee ar = new Arrivee(comp,'M');
		comp.entreeUsager(ar);
		ar.afficheBillet();
		
		
		try {
			comp.sortieBarCode(javax.swing.JOptionPane.showInputDialog("test"));
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidBarrCodeException e) {
			javax.swing.JOptionPane.showMessageDialog(null, e.toString(), "Erreur code-bar", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
