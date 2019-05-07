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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
            tf_barcode.clear();
            
        } catch (InvalidBarrCodeException e) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Erreur : Code-Barre invalide !");
        	alert.setHeaderText("Code barre invalide !");
        	alert.setContentText(e.getMessage());
        	alert.showAndWait().ifPresent(rs -> {
        	    if (rs == ButtonType.OK) {
        	        System.out.println("Pressed OK.");
        	    }
        	});
        } 
        catch(NumberFormatException e) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Erreur : Code-Barre invalide !");
        	alert.setHeaderText("Code barre invalide !");
        	alert.setContentText("Le code barre doit contenir uniquement des chiffres");
        	alert.showAndWait().ifPresent(rs -> {
        	    if (rs == ButtonType.OK) {
        	        System.out.println("Pressed OK.");
        	    }
        	});
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