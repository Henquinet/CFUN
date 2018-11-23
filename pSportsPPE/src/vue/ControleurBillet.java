package vue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.rigaud.Complexe;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControleurBillet {

    // Label-------------------------------------------------------------
    @FXML
    private Label l_complexe;
    @FXML
    private Label l_date;
    @FXML
    private Label l_heure;
    @FXML
    private Label l_billet;
    // Bouton-------------------------------------------------------------
    @FXML
    private Button b_quitter;
    // Messages-------------------------------------------------------------
    private final String MSG_COMPLEXE = "Complexe ";
    private final String MSG_DATE = "date : ";
    private final String MSG_HEURE = "heure : ";
    private final String MSG_BILLET = "Billet d'entrée n° : ";
    
    public ControleurBillet() {}
    
    public void initChamps (Complexe complexe) {
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(new Date());
        String heure = new SimpleDateFormat("HH:mm", Locale.FRANCE).format(new Date());
        
        l_complexe.setText(MSG_COMPLEXE + complexe.getNomComplexe());
        l_date.setText(MSG_DATE + date);
        l_heure.setText(MSG_HEURE + heure);
        l_billet.setText(MSG_BILLET + Complexe.getNumeroActuel());
       
    }
    
    @FXML
    private void initialize() {}
    
    @FXML
    private void exit() {
        Stage stage = (Stage) b_quitter.getScene().getWindow();
        stage.close(); 
    }
    
}
