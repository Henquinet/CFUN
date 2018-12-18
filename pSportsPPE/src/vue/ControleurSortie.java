package vue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.rigaud.Arrivee;
import fr.rigaud.InvalidBarrCodeException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControleurSortie extends ControleurCFun {

    // TextField-----------------------------------------------------------
    @FXML
    private TextField tf_barcode;
    // Buttons-------------------------------------------------------------
    private Button b_sortie;
    
    
    @FXML
    public void initialize() {
        super.initialize();
    }

    
    @FXML
    private void buttonPush() {
        String barcode = tf_barcode.getText();
        Arrivee arrivee;
        try {
            arrivee = complexeCFUN.sortieBarCode(barcode);
            openTicket(arrivee);
            complexeCFUN.sortieUsager(arrivee.getNumeroArrivee());
            
        } catch (InvalidBarrCodeException e) {
            e.printStackTrace();
        }       
    }
    
    private void openTicket(Arrivee arrivee) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader (getClass().getResource("ticket.fxml"));
            root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ticket de sortie");
            stage.setScene(new Scene(root));
            stage.show();
            
            //recuperation du controleur du loader            
            ControleurTicket controleurTicket = loader.getController();
            //passage du code de formation en paramètre vers une méthode du controleur
            controleurTicket.initChamps(arrivee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}