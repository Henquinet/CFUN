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
    private Label l_num_comboBox_muscu;
    @FXML
    private Label l_num_comboBox_fit;
    @FXML
    private ComboBox<String> cb_num_arrivee_muscu;
    @FXML
    private ComboBox<String> cb_num_arrivee_fit;
    
    
    
    @FXML
    public void initialize() {
        super.initialize();
        
        //désactivation des boutons pour le ticket tant qu'aucun numero n'est séléctioné
        enableButtons(true, true);
        enableButtons(false, true);
        
        //recuperation des arrivées du complexe
        List<Arrivee> lesArrivees = complexeCFUN.getLesArrivees();
        //remplissage de la combobox
        for (Arrivee uneArrivee : lesArrivees) {
            //recuperation en String du numéro d'arrivee
            String numeroArrivee = String.valueOf(uneArrivee.getNumeroArrivee());
            
            if (uneArrivee.getChoixSport() == 'M') {
                cb_num_arrivee_muscu.getItems().add(numeroArrivee);
            } else {
                cb_num_arrivee_fit.getItems().add(numeroArrivee);
            }
        }
        
        //ecouteur sur les combo pour activé les boutons
        //ne se fera que si il ya a des actions sur la comboBox
        cb_num_arrivee_muscu.valueProperty().addListener(
                (observable, oldValue, newValue) -> enableButtons(true, false)); //activation du bouton muscu
        cb_num_arrivee_fit.valueProperty().addListener(
                (observable, oldValue, newValue) -> enableButtons(false, false)); //activation du bouton fit
    }
    
    private void enableButtons(Boolean muscu, boolean etatButton) {
        if (muscu) {
            b_sortie_muscu.setDisable(etatButton);
        } else {
            b_sortie_fit.setDisable(etatButton);
        }
    }
    
    @FXML
    private void buttonPushMuscu() {
        int numeroArrivee = Integer.parseInt(cb_num_arrivee_muscu.getValue());
        Arrivee arrivee = complexeCFUN.recherche(numeroArrivee);
        openTicket(arrivee);
        
        //cb_num_arrivee.getItems().remove();
        complexeCFUN.sortieUsager(numeroArrivee);
    }
    
    @FXML
    private void buttonPushFit() {
        int numeroArrivee = Integer.parseInt(cb_num_arrivee_fit.getValue());
        Arrivee arrivee = complexeCFUN.recherche(numeroArrivee);
        openTicket(arrivee);
        
        //cb_num_arrivee.getItems().remove();
        complexeCFUN.sortieUsager(numeroArrivee);
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
            controleurTicket.initChamps(complexeCFUN, arrivee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}