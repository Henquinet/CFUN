package vue;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class ControleurMain {
    
    // Menu-------------------------------------------------------------
    @FXML
    MenuBar menuBar;
    @FXML
    MenuItem mi_visiteur;
    @FXML
    MenuItem mi_gestionnaire;
    @FXML
    MenuItem mi_rester;
    @FXML
    MenuItem mi_quitter;
    // TabPane----------------------------------------------------------
    @FXML
    TabPane tabPane;
    @FXML
    Tab t_etatComplexe;
    @FXML
    Tab t_billetEntree;
    @FXML
    Tab t_ticketsortie;
    // Label------------------------------------------------------------
    @FXML
    Label l_accueil;
    
    public ControleurMain() {}
    
    @FXML
    private void initialize() {
        tabPane.setVisible(false);
    }

    @FXML
    private void toCustomer() {
        l_accueil.setVisible(false);
        tabPane.setVisible(true);
    }
    
    @FXML
    private void exit() {
        System.exit(0);
    }
    
    @FXML
    private void openWindow() {
        try {
            Stage stage = (Stage) menuBar.getScene().getWindow();
            stage.close();
            
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/vue/connexion.fxml"));

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("CFUN");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
