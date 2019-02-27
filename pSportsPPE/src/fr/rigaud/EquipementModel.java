package fr.rigaud;
import javafx.beans.property.SimpleStringProperty;

public class EquipementModel {

	private final SimpleStringProperty id;
	 

	private final SimpleStringProperty etat;
	 

 	private final SimpleStringProperty libre;
	 
 	private final SimpleStringProperty salle;
	
	    
	public EquipementModel(String id, String etat, String libre,String salle) {
		super();
		this.id = new SimpleStringProperty(id);
		this.etat = new SimpleStringProperty(etat);
		this.libre = new SimpleStringProperty(libre);
		this.salle = new SimpleStringProperty(salle);
	}
 	

 	public String getId() {
		 return id.getValue();
	 }

 	public String getEtat() {
		 return etat.getValue();
	 }


 	public String getLibre() {
	      return libre.getValue();
	 }
 	
 	public String getSalle() {
	      return salle.getValue();
	 }
	
	
}
