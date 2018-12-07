package vue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.rigaud.*;
import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.control.*;


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
    private Label l_infos;
	
    //Onglets-----------------------------------------------
    @FXML
    private TabPane tabp;
	@FXML
    private Tab tab_fit;
	@FXML
    private Tab tab_muscu;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final String TITRE_MAIN = "Etat du complexe : ";
	private final String DATE = "date : ";
	private final String HEURE = "heure : ";
	private final String TITRE_FITN = "Infos Fitness";
	private final String TITRE_MUSC = "Infos Musculation";
	private final String NBPD = "Nombre de places occupées : ";
	private final String NBPL = "Nombre de places libre : ";
	private final String TXOCC = "Taux occ. : ";
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ControleurGestionnaire() {} 
	
	@FXML
	public void initialize() {
	    super.initialize();
	    
	    l_infos.setText(complexeCFUN.lesInfos());
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
