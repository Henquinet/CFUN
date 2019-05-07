package vue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import connexion.ConnexionDerby;
import fr.rigaud.*;
import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;


public class ControleurGestionnaire extends ControleurCFun {	

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
    private ListView<String> lv_infos;
    
    //Equipements -------------------------------
    @FXML
    private Label l_defec_nb;
    @FXML
    private Button b_plus;
    @FXML
    private Button b_moins;
    @FXML
    private Button b_reset;
    @FXML
    private ToggleButton tog_defectueux;
    @FXML
    private TableView<EquipementModel> tv_equip;
	@FXML
	private TableColumn<EquipementModel,String> col_id = new TableColumn<EquipementModel,String>("ID");
	@FXML
	private TableColumn<EquipementModel,String> col_etat = new TableColumn<EquipementModel,String>("ETAT");
	@FXML
	private TableColumn<EquipementModel,String> col_libre = new TableColumn<EquipementModel,String>("LIBRE");
	@FXML
	private TableColumn<EquipementModel,String> col_salle = new TableColumn<EquipementModel,String>("SALLE");
    
    //Onglets-----------------------------------------------
    @FXML
    private TabPane tabp;
	@FXML
    private Tab tab_fit;
	@FXML
    private Tab tab_muscu;
	@FXML
    private Tab tab_defec;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final String TITRE_MAIN = "Etat du complexe : ";
	private final String DATE = "date : ";
	private final String HEURE = "heure : ";
	private final String TITRE_FITN = "Infos Fitness";
	private final String TITRE_MUSC = "Infos Musculation";
	private final String NBPD = "Nombre de places occupées : ";
	private final String NBPL = "Nombre de places libre : ";
	private final String TXOCC = "Taux occ. : ";
	
	
	private ConnexionDerby con = ConnexionDerby.getInstance();
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ControleurGestionnaire() {} 
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// METHODES FXML ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	@FXML
	public void initialize() {
	    super.initialize();
	    
	    lv_infos.setEditable(false);
	    lv_infos.getItems().add(complexeCFUN.lesInfos());
	    
	    col_id.setCellValueFactory(new PropertyValueFactory<EquipementModel,String>("id"));
	    col_etat.setCellValueFactory(new PropertyValueFactory<EquipementModel,String>("etat"));
	    col_libre.setCellValueFactory(new PropertyValueFactory<EquipementModel,String>("libre"));
	    col_salle.setCellValueFactory(new PropertyValueFactory<EquipementModel,String>("salle"));
	    fillTable();
	}
	
	@FXML
	private void changeToMuscu() {
	    showFitOrMusc(false);
	}
	
	@FXML
    private void changeToFit() {
        showFitOrMusc(true);
    }

	@FXML
	private void reset() {
		ConnexionDerby con = ConnexionDerby.getInstance();
		List<Equipement> lst = super.complexeCFUN.getEquipements();
		for(int i = 0; i < lst.size(); i++) {
			lst.get(i).setArrivee(null);
			lst.get(i).setOccupe(false);
			lst.get(i).setDefectueux(false);;

		}
		
		con.reset();
		fillTable();
	}
	
	@FXML
	private void addEquip() {
		int salle = selectSalle();
		
		
		
	}
	
	@FXML
	private void delEquip() {

		
	}
	
	@FXML
	private void switchDefectueux() {
		EquipementModel e = tv_equip.getFocusModel().getFocusedItem();
		Equipement eq = ControleurMain.complexeCFUN.getEquipements().get(tv_equip.getFocusModel().getFocusedIndex());
		
		int etat = 0;
		if(eq.isDefectueux()) 
			etat = 1;
		
		
		//db------------------------------------------------------------
		try {
			con.switchDefectueux(Integer.parseInt(e.getId()), etat);
			e = new EquipementModel(e.getId(),String.valueOf(etat),e.getLibre(),e.getSalle());
			if(etat == 0) {
				eq.setDefectueux(true);
			}
			else {
				eq.setDefectueux(false);
			}
			fillTable();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// METHODES PRIVEES ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int selectSalle() {
		List<String> choices = con.getSalles();
		
		ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
		dialog.setTitle("Choix d'une salle");
		dialog.setHeaderText("Veuillez choisir une salle :");
		dialog.setContentText("Salles :");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		

		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(letter -> System.out.println("Your choice: " + letter));
		
		return choices.indexOf(result.get());
	}
	
	private void fillTable() {
		tv_equip.getItems().clear();
		tv_equip.setItems(con.getEquipements());
	}
	
	private void showFitOrMusc(boolean fitn) {
		double etat = 0;
		String date = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(new Date());
		String heure = new SimpleDateFormat("HH:mm", Locale.FRANCE).format(new Date());
		
		if(fitn) {
		    //mise a jour des informations Fit
		    l_fit_fm_comp.setText(TITRE_MAIN + complexeCFUN.getNomComplexe());
		    l_fit_fm_date.setText(DATE + date);
		    l_fit_fm_heure.setText(HEURE + heure);
			l_fit_fm_t.setText(TITRE_FITN);
			l_fit_fm_dispo.setText(NBPL + complexeCFUN.getNbPlacesRestantesFit());
			l_fit_fm_ocup.setText(NBPD + complexeCFUN.getNbPlacesIndisponibles(false));
			l_fit_fm_taux.setText(TXOCC + complexeCFUN.etatFit()*100 + "%");

			//mise a jour du voyant de fit
            etat = complexeCFUN.etatFit();
            changeCouleur(c_fit_color, etat);
		} else {
		    //mise a jour des informations Muscu
		    l_muscu_fm_comp.setText(TITRE_MAIN + complexeCFUN.getNomComplexe());
            l_muscu_fm_date.setText(DATE + date);
            l_muscu_fm_heure.setText(HEURE + heure);
			l_muscu_fm_t.setText(TITRE_MUSC);
			l_muscu_fm_dispo.setText(NBPL + complexeCFUN.getNbPlacesRestantesMuscu());
			l_muscu_fm_ocup.setText(NBPD + complexeCFUN.getNbPlacesIndisponibles(true));
			l_muscu_fm_taux.setText(TXOCC + complexeCFUN.etatMuscu()*100 + "%");
			
			//mise a jour du voyant de muscu
			etat = complexeCFUN.etatMuscu();
			changeCouleur(c_muscu_color, etat);
		}
	}	
	
	private void changeCouleur(Circle cercleVoyant, double etat) {
        switch(new ChoixCouleur(etat).getCouleur()) {
        case vert:
            cercleVoyant.fillProperty().set(Paint.valueOf("green"));
            break;
        case orange:
            cercleVoyant.fillProperty().set(Paint.valueOf("orange"));
            break;
        case rouge:
            cercleVoyant.fillProperty().set(Paint.valueOf("red"));
            break;
        }
    }
}
