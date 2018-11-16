package vue;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import fr.rigaud.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ControleurGestionnaire {
	// Main------------------------------------------------------------
	@FXML
	private TabPane tabp;
	
	//Accueil----------------------------------------------------------
	@FXML
	private Label l_main;
	
	//Info general
	@FXML
    private Label l_muscu_fm_comp;
	@FXML
    private Label l_muscu_fm_date;
	@FXML
    private Label l_muscu_fm_heure;
	
    @FXML
    private Label l_fit_fm_comp;
    @FXML
    private Label l_fit_fm_date;
    @FXML
    private Label l_fit_fm_heure;
	
	
	//Info Fitness/Muscu-----------------------------------------------
	@FXML
	private Label l_muscu_fm_t;
	@FXML
	private Label l_muscu_fm_dispo;
	@FXML
	private Label l_muscu_fm_ocup;
	@FXML
	private Label l_muscu_fm_etat;
	@FXML
    private Label l_muscu_fm_taux;
	@FXML
	private Circle c_muscu_color;
	
	@FXML
    private Label l_fit_fm_t;
    @FXML
    private Label l_fit_fm_dispo;
    @FXML
    private Label l_fit_fm_ocup;
    @FXML
    private Label l_fit_fm_etat;

    @FXML
    private Label l_fit_fm_taux;
    @FXML
    private Circle c_fit_color;
    
    @FXML
    private Label l_infos;
	
    //Onglets-----------------------------------------------
	@FXML
    private Tab tab_fit;
	@FXML
    private Tab tab_muscu;
    @FXML
    private Tab tab_quitter;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final String TITRE_MAIN = "Etat du Complexe : ";
	private final String DATE = "date : ";
	private final String HEURE = "heure : ";
	private final String TITRE_FITN = "Infos Fitness";
	private final String TITRE_MUSC = "Infos Musculation";
	private final String NBPD = "Nombre de places occupées : ";
	private final String NBPL = "Nombre de places libre : ";
	private final String TXOCC = "Taux occ. : ";
	
	private Complexe comp;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ControleurGestionnaire() {
		comp = new Complexe(5,4,"test");
		Arrivee test = new Arrivee(comp,'M');
		comp.entreeUsager(test);
		comp.entreeUsager(test);
		comp.entreeUsager(test);
		comp.entreeUsager(test);
	} 
	
	@FXML
	private void initialize() {
	    l_infos.setText(comp.lesInfos());
	}
	
	@FXML
	private void changeToMuscu() {
	    showFitOrMusc(false);
	}
	
	@FXML
    private void changeToFit() {
        showFitOrMusc(true);
    }

	private void showFitOrMusc(boolean fitn) {
		double etat = 0;
		double tauxOcc;
		double totalPlace;
		String date = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(new Date());
		String heure = new SimpleDateFormat("HH:mm", Locale.FRANCE).format(new Date());
		
		if(fitn) {
		    l_fit_fm_comp.setText(TITRE_MAIN + comp.getNomComplexe());
		    l_fit_fm_date.setText(DATE + date);
		    l_fit_fm_heure.setText(HEURE + heure);
			l_fit_fm_t.setText(TITRE_FITN);
			l_fit_fm_dispo.setText(NBPL + comp.getNbPlacesRestantesFit());
			l_fit_fm_ocup.setText(NBPD + comp.getNbPlacesOccupeesFit());
			l_fit_fm_taux.setText(TXOCC + comp.etatFit()*100 + "%");
			etat = comp.etatFit();
			
			switch(new ChoixCouleur(etat).getCouleur()) {
            case vert:
                c_fit_color.fillProperty().set(Paint.valueOf("green"));
                break;
            case orange:
                c_fit_color.fillProperty().set(Paint.valueOf("orange"));
                break;
            case rouge:
                c_fit_color.fillProperty().set(Paint.valueOf("red"));
                break;
			} 
		}
		else {
		    
		    l_muscu_fm_comp.setText(TITRE_MAIN + comp.getNomComplexe());
            l_muscu_fm_date.setText(DATE + date);
            l_muscu_fm_heure.setText(HEURE + heure);
			l_muscu_fm_t.setText(TITRE_MUSC);
			l_muscu_fm_dispo.setText(NBPL + comp.getNbPlacesRestantesMuscu());
			l_muscu_fm_ocup.setText(NBPD + comp.getNbPlacesOccupeesMuscu());
			l_muscu_fm_taux.setText(TXOCC + comp.etatMuscu()*100 + "%");
			etat = comp.etatMuscu();

			switch(new ChoixCouleur(etat).getCouleur()) {
            case vert:
                c_muscu_color.fillProperty().set(Paint.valueOf("green"));
                break;
            case orange:
                c_muscu_color.fillProperty().set(Paint.valueOf("orange"));
                break;
            case rouge:
                c_muscu_color.fillProperty().set(Paint.valueOf("red"));
                break;
			} 
		}
	}
		
	@FXML
	private void exit() {
		System.exit(0);
	}
	
	
}
