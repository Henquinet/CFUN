package vue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.rigaud.Arrivee;
import fr.rigaud.ChoixCouleur;
import fr.rigaud.Complexe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ControleurMain {
    
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
    // TabPane----------------------------------------------------------
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tab_fit;
    @FXML
    private Tab tab_muscu;
    @FXML
    private Tab t_billetEntree;
    @FXML
    private Tab t_ticketsortie;
    // Label------------------------------------------------------------
    @FXML
    private Label l_muscu_fm_comp;
    @FXML
    private Label l_muscu_fm_t;
    @FXML
    private Circle c_muscu_color;
    
    @FXML
    private Label l_fit_fm_comp;
    @FXML
    private Label l_fit_fm_t;
    @FXML
    private Circle c_fit_color;
    
    private final String TITRE_MAIN = "Etat du Complexe : ";
    private final String TITRE_FITN = "Infos Fitness";
    private final String TITRE_MUSC = "Infos Musculation";
    
    private Complexe comp;
    
    
    public ControleurMain() {
        comp = new Complexe(5,4,"test");
        Arrivee test = new Arrivee(comp,'M');
        comp.entreeUsager(test);
        comp.entreeUsager(test);
        comp.entreeUsager(test);
        comp.entreeUsager(test);
    }
    
    @FXML
    private void initialize() {
        tabPane.setVisible(false);
    }

    @FXML
    private void toCustomer() {
        tabPane.setVisible(true);
    }
    
    @FXML
    private void changeToMuscu() {
        showFitOrMusc(false);
    }
    
    @FXML
    private void changeToFit() {
        showFitOrMusc(true);
    }

    private void showFitOrMusc(boolean fitn) {
        double etat = 0;
        
        if(fitn) {
            l_fit_fm_comp.setText(TITRE_MAIN + comp.getNomComplexe());
            l_fit_fm_t.setText(TITRE_FITN);
            etat = comp.etatFit();
            
            switch(new ChoixCouleur(etat).getCouleur()) {
            case vert:
                c_fit_color.fillProperty().set(Paint.valueOf("green"));
                break;
            case orange:
                c_fit_color.fillProperty().set(Paint.valueOf("orange"));
                break;
            case rouge:
                c_fit_color.fillProperty().set(Paint.valueOf("red"));
                break;
            } 
        }
        else {
            
            l_muscu_fm_comp.setText(TITRE_MAIN + comp.getNomComplexe());
            l_muscu_fm_t.setText(TITRE_MUSC);
            etat = comp.etatMuscu();

            switch(new ChoixCouleur(etat).getCouleur()) {
            case vert:
                c_muscu_color.fillProperty().set(Paint.valueOf("green"));
                break;
            case orange:
                c_muscu_color.fillProperty().set(Paint.valueOf("orange"));
                break;
            case rouge:
                c_muscu_color.fillProperty().set(Paint.valueOf("red"));
                break;
            }
        }
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
