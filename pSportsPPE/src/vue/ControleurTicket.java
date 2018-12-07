package vue;


import fr.rigaud.Arrivee;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControleurTicket {

 // Label-------------------------------------------------------------

    @FXML
    private Label l_ticket;
    // Bouton-------------------------------------------------------------
    @FXML
    private Button b_quitter;
    
    public ControleurTicket() {}
    
    @FXML
    private void initialize() {}
    
    public void initChamps (Arrivee arrivee) {
        l_ticket.setText(arrivee.afficheTicket());
    }
    
    @FXML
    private void exit() {
        Stage stage = (Stage) b_quitter.getScene().getWindow();
        stage.close(); 
    }
    
    
}
