package vue;

import fr.rigaud.Arrivee;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControleurBillet {

    // Label-------------------------------------------------------------
    @FXML
    private Label l_billet;
    // Bouton-------------------------------------------------------------
    @FXML
    private Button b_quitter;
    
    public ControleurBillet() {}
    
    @FXML
    private void initialize() {}
    
    public void initChamps(Arrivee arrivee) {
        l_billet.setText(arrivee.afficheBillet());
    }
    
    @FXML
    private void exit() {
        Stage stage = (Stage) b_quitter.getScene().getWindow();
        stage.close(); 
    }
    
}
