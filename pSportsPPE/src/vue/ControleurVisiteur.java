package vue;

import java.io.IOException;

import fr.rigaud.Arrivee;
import fr.rigaud.ChoixCouleur;
import fr.rigaud.Complexe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ControleurVisiteur extends ControleurCFun{
    
    // TabPane----------------------------------------------------------
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tab_fit;
    @FXML
    private Tab tab_muscu;
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
    // Boutons------------------------------------------------------------ 
    @FXML
    private Button b_enter_muscu;
    @FXML
    private Button b_enter_fit;
    // Titres------------------------------------------------------------ 
    private final String TITRE_MAIN = "Etat du Complexe : ";
    private final String TITRE_FITN = "Infos Fitness";
    private final String TITRE_MUSC = "Infos Musculation";
    
    private Complexe complexe;
    
    
    public ControleurVisiteur() {
        complexe = new Complexe(5,4,"CFUN");
    }
    
    @FXML
    public void initialize() {
        super.initialize();
    }

    @FXML
    private void toCustomer() {
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
            l_fit_fm_comp.setText(TITRE_MAIN + complexe.getNomComplexe());
            l_fit_fm_t.setText(TITRE_FITN);
            
            //mise a jour du voyant de fit
            etat = complexe.etatFit();
            changeCouleur(c_fit_color, etat);
        } else {
            l_muscu_fm_comp.setText(TITRE_MAIN + complexe.getNomComplexe());
            l_muscu_fm_t.setText(TITRE_MUSC);
            
            //mise a jour du voyant de muscu
            etat = complexe.etatMuscu();
            changeCouleur(c_muscu_color, etat);
        }
    }
    
    private void changeCouleur(Circle cercleVoyant, double etat) {
        switch(new ChoixCouleur(etat).getCouleur()) {
        case vert:
            cercleVoyant.fillProperty().set(Paint.valueOf("green"));
            break;
        case orange:
            cercleVoyant.fillProperty().set(Paint.valueOf("orange"));
            break;
        case rouge:
            cercleVoyant.fillProperty().set(Paint.valueOf("red"));
            break;
        }
    }
    
    @FXML
    private void buttonPushMuscu() {
        //mise a jour du voyant de muscu
        double etat = complexe.etatMuscu();
        changeCouleur(c_muscu_color, etat);
        
        //ajout d'une arrivée dans la muscu du complexe
        Arrivee uneArrivee = new Arrivee(complexe,'M');
        complexe.entreeUsager(uneArrivee);
        openBillet();
    }
    
    @FXML
    private void buttonPushFit() {
        //mise a jour du voyant de fit
        double etat = complexe.etatFit();
        changeCouleur(c_fit_color, etat);
        
        //ajout d'une arrivée dans le fit du complexe
        Arrivee uneArrivee = new Arrivee(complexe,'F');
        complexe.entreeUsager(uneArrivee);
        openBillet();
    }
    
    private void openBillet() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader (getClass().getResource("billet.fxml"));
            root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Billet d'entrée");
            stage.setScene(new Scene(root));
            stage.show();
            
            //recuperation du controleur du loader            
            ControleurBillet controleurBillet = loader.getController();
            //passage du code de formation en paramètre vers une méthode du controleur
            controleurBillet.initChamps(complexe);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
