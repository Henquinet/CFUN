package vue;

import fr.rigaud.Arrivee;
import fr.rigaud.ChoixCouleur;
import fr.rigaud.Complexe;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

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
    
    private final String TITRE_MAIN = "Etat du Complexe : ";
    private final String TITRE_FITN = "Infos Fitness";
    private final String TITRE_MUSC = "Infos Musculation";
    
    private Complexe comp;
    
    
    public ControleurVisiteur() {
        comp = new Complexe(5,4,"test");
        Arrivee test = new Arrivee(comp,'M');
        comp.entreeUsager(test);
        comp.entreeUsager(test);
        comp.entreeUsager(test);
        comp.entreeUsager(test);
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
        } else {
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
}
