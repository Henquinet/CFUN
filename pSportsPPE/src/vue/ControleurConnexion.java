package vue;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ControleurConnexion {

    //TextField + PasswordField--------------------------------------------------------
    @FXML
    private TextField tf_login;
    @FXML
    private PasswordField pf_pass;

    //Bouton--------------------------------------------------------
    @FXML
    private Button b_connection;
    
    //Messages erreurs--------------------------------------------------------
    @FXML
    private Label l_noID;
    @FXML
    private Label l_noMDP;
    @FXML
    private Label l_Wrong;
    
    public ControleurConnexion() {}
    
    @FXML
    private void initialize() {
        l_noID.setText("");
        l_noMDP.setText("");
        l_Wrong.setText("");
        
        l_noID.setTextFill(Color.web("red"));
        l_noMDP.setTextFill(Color.web("red"));
        l_Wrong.setTextFill(Color.web("red"));
    }
    
    @FXML
    private void connexion() {
        final String ERRNOID = "Veuillez renseigner un identifiant";
        final String ERRNOMDP = "Veuillez renseigner un mot de passe";
        final String ERRLOG = "Mauvais identifiant ou mot de passe";
        final String LOGIN = "Burgaud";
        final String MDP = "123";
        
        //remise a 0 des messages d'erreurs
        l_noID.setText("");
        l_noMDP.setText("");
        l_Wrong.setText("");
        
        
        if (tf_login.getText().length() == 0) {
            l_noID.setText(ERRNOID);
        } else {
            if (pf_pass.getText().length() == 0) {
                l_noMDP.setText(ERRNOMDP);
            } else {
                if (tf_login.getText().equals(LOGIN) && pf_pass.getText().equals(MDP)) {
                    openWindow();
                } else {
                    l_Wrong.setText(ERRLOG);
                }
                
            }
        }
    }
    
    @FXML
    private void openWindow() {
        try {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/vue/gestionnaire.fxml"));

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("CFUN");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
