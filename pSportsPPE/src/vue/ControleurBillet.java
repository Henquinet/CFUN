package vue;

import java.io.File;

import fr.rigaud.Arrivee;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ControleurBillet {

    // Label-------------------------------------------------------------
    @FXML
    private Label l_billet;
    // Bouton------------------------------------------------------------
    @FXML
    private Button b_quitter;
    // images------------------------------------------------------------
    @FXML
    private ImageView img_barcode;
    
    public ControleurBillet() {}
    
    @FXML
    private void initialize() {}
    
    public void initChamps(Arrivee arrivee) {
        l_billet.setText(arrivee.afficheBillet());

        Image image = new Image(new File(arrivee.genBarcode()).toURI().toString());
        
        img_barcode.setImage(image);
    }
    
    @FXML
    private void exit() {
        Stage stage = (Stage) b_quitter.getScene().getWindow();
        stage.close(); 
    }
    
}
