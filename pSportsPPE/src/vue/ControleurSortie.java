package vue;

import java.io.IOException;

import fr.rigaud.Arrivee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControleurSortie extends ControleurCFun {
    // Boutons------------------------------------------------------------
    @FXML
    private Button b_sortie_muscu;
    @FXML
    private Button b_sortie_fit;
    // ComboBox-----------------------------------------------------------
    @FXML
    private Label l_num_comboBox;
    @FXML
    private ComboBox<String> cb_num_arrivee;
    
    
    
    @FXML
    public void initialize() {
        super.initialize();
        
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////// REMPLIR LA COMBOBOX AVEC LES NUMERO D ARRIVEE ///////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    
    @FXML
    private void buttonPushMuscu() {
        openTicket();
    }
    
    @FXML
    private void buttonPushFit() {
        openTicket();
    }
    
    private void openTicket() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader (getClass().getResource("ticket.fxml"));
            root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Billet d'entrée");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}