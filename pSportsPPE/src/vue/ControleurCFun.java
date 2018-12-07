package vue;

import fr.rigaud.Complexe;
import fr.rigaud.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControleurCFun {
    // Menu-------------------------------------------------------------
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu m_entree;
    @FXML
    private Menu m_sortie;
    @FXML
    private Menu m_gestionnaire;
    @FXML
    private Menu m_quitter;
    @FXML
    private Label l_menu;
    
    private Menu listeMenus[];
    
    protected static Complexe complexeCFUN = new Complexe(5,4,"CFUN ");
    
    @FXML
    protected void initialize() {
        l_menu = new Label("Entrée Visiteurs");
        l_menu.setOnMouseClicked(mouseEvent-> openVueEntréeVisiteur());
        m_entree = new Menu("", l_menu);
        
        l_menu = new Label("Sortie Visiteurs");
        l_menu.setOnMouseClicked(mouseEvent-> openVueSortieVisiteur());
        m_sortie = new Menu("", l_menu);
        
        l_menu = new Label("Gestionnaire");
        l_menu.setOnMouseClicked(mouseEvent-> openConnexionGestionnaire());
        m_gestionnaire = new Menu("", l_menu);

        l_menu = new Label("Quitter");
        l_menu.setOnMouseClicked(mouseEvent-> exit());
        m_quitter = new Menu("", l_menu);

        l_menu = null;

        listeMenus = new Menu[]{m_entree, m_sortie, m_gestionnaire, m_quitter};

        menuBar.getMenus().addAll(listeMenus);
    }
    
    @FXML
    private void openVueEntréeVisiteur() {
        try {
            AnchorPane root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main .class.getResource("/vue/entreeVisiteur.fxml"));

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
    private void openVueSortieVisiteur() {
        try {
            AnchorPane root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main .class.getResource("/vue/sortieVisiteur.fxml"));

            root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            
            Stage stage = Main.getPrimaryStage();
            stage.setScene(scene);
            stage.setTitle("Sortie du complexe");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
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
    private void exit() {
        System.exit(0);
    }
}
