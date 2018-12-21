package vue;

import fr.rigaud.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import connexion.ConnexionDerby;



public class ControleurConnexion extends ControleurCFun {


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
    public void initialize() {
        super.initialize();
        
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
        ArrayList<String> IDgestionnaire = ConnexionDerby.getInstance().getGestionnaire();
        //remise a 0 des messages d'erreurs
        l_noID.setText("");
        l_noMDP.setText("");
        l_Wrong.setText("");
        
        
        if (tf_login.getText().length() == 0) {
            l_noID.setText(ERRNOID);
        } else {
            if (pf_pass.getText().length() == 0 ) {
                l_noMDP.setText(ERRNOMDP);
            } else {
                try {
					if (tf_login.getText().equals(IDgestionnaire.get(0)) && hashSHA2(pf_pass.getText()).equals(IDgestionnaire.get(1))) {
					    openVueGestionnaire();
					} else {
					    l_Wrong.setText(ERRLOG);
					}
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }
        }
    }
    
    @FXML
    private void openVueGestionnaire() {
        try {
            AnchorPane root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main .class.getResource("/vue/gestionnaire.fxml"));

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

    public static String hashSHA2(String password) throws NoSuchAlgorithmException {
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
		        byte[] array = md.digest(password.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
	}

}
