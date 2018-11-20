package vue;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControleurMain extends ControleurCFun {
    @FXML
    private Label l_bienvenue;
    
    @FXML
    private void initialize() {
        String MSG_BIENVENUE = "Bienvenue au complexe CFUN";
        
        l_bienvenue.setText(MSG_BIENVENUE);
    }
}
