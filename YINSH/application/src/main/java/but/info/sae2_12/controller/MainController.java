package but.info.sae2_12.controller;

import javafx.fxml.FXML;

public class MainController {

   

    @FXML
    private BottomController bottomPanelController;

    @FXML
    public void initialize() {
        bottomPanelController.setMainController(this);
    }
}