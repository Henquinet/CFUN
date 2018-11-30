package vue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        
        //désactivation des boutons pour le ticket tant qu'aucun numero n'est séléctioné
        enableButtons(true);
        
        //recuperation des arrivées du complexe
        List<Arrivee> lesArrivees = complexeCFUN.getLesArrivees();
        //remplissage de la combobox
        for (Arrivee uneArrivee : lesArrivees) {
            String numeroArrivee = String.valueOf(uneArrivee.getNumeroArrivee());
            cb_num_arrivee.getItems().add(numeroArrivee);
        }
        
        //ecouteur sur la combo pour activé les boutons
        //ne se fera que si il ya a des actions sur la comboBox
        cb_num_arrivee.valueProperty().addListener(
                (observable, oldValue, newValue) -> enableButtons(false)); //activation des boutons
    }
    
    private void enableButtons(boolean etatButton) {
        b_sortie_fit.setDisable(etatButton);
        b_sortie_muscu.setDisable(etatButton);
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
            stage.setTitle("Ticket de sortie");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}