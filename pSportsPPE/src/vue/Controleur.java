package vue;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fr.rigaud.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import javafx.scene.control.*;
import javafx.scene.text.Text;


public class Controleur {
	// Main------------------------------------------------------------
	@FXML
	private MenuBar menu;
	@FXML
	private TabPane tabp;
	
	//Accueil----------------------------------------------------------
	@FXML
	private Label l_main;
	
	//Info Fitness/Muscu-----------------------------------------------
	@FXML
	private Label l_fm_t;
	@FXML
	private Label l_fm_dispo;
	@FXML
	private Label l_fm_ocup;
	@FXML
	private Circle c_color;
	
	//Connexion--------------------------------------------------------
	@FXML
	private TextField tf_login;
	@FXML
	private TextField tf_pass;
	@FXML
	private Button b_connection;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final String TITRE_MAIN = "Complexe ";
	private final String TITRE_FITN = "Infos fitness";
	private final String TITRE_MUSC = "Info Musculation";
	private final String NBPD = "Nombre de places occupées : ";
	private final String NBPL = "Nombre de places libre : ";
	
	private Complexe comp;
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Controleur() {
		comp = new Complexe(5,5,"test");
		
	} 
	
	private void initialize()
	{
		 l_main.setText(TITRE_MAIN + comp.getNomComplexe());
		 
		 showFitOrMusc(true);
		 
	}
	
	
	
	
	
	
	
	private void showFitOrMusc(boolean fitn) {
		if(fitn) {
			l_fm_t.setText(TITRE_FITN);
			l_fm_dispo.setText(NBPL + comp.getNbPlacesRestantesFit());
			//l_fm_ocup.setText(NBPD + comp.getNbPlacesOccupeesFit());
		}
		else {
			l_fm_t.setText(TITRE_MUSC);
			l_fm_dispo.setText(NBPL + comp.getNbPlacesRestantesMuscu());
			//l_fm_ocup.setText(NBPD + comp.getNbPlacesOccupeesMuscu());
		}
	}
	
	@FXML
	private void exit() {
		System.exit(0);
	}
	
	
}
