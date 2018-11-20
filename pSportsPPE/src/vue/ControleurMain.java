package vue;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControleurMain extends ControleurCFun {
    @FXML
    private Label l_bienvenue;
    
    @FXML
    public void initialize() {
        String MSG_BIENVENUE = "Bienvenue au complexe CFUN";
        
        super.initialize();
        
        l_bienvenue.setText(MSG_BIENVENUE);
    }
}
