package vue;

import fr.rigaud.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControleurCFun {
    // Menu-------------------------------------------------------------
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem mi_visiteur;
    @FXML
    private MenuItem mi_gestionnaire;
    @FXML
    private MenuItem mi_rester;
    @FXML
    private MenuItem mi_quitter;
    
    @FXML
    private void openConnexionGestionnaire() {
        try {
            AnchorPane root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main .class.getResource("/vue/connexion.fxml"));

            root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            
            Stage stage = Main.getPrimaryStage();
            stage.setScene(scene);
            stage.setTitle("Connexion Gestionnaire");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void openVueVisiteur() {
        try {
            AnchorPane root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main .class.getResource("/vue/visiteur.fxml"));

            root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            
            Stage stage = Main.getPrimaryStage();
            stage.setScene(scene);
            stage.setTitle("Complexe CFUN");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void exit() {
        System.exit(0);
    }
}
