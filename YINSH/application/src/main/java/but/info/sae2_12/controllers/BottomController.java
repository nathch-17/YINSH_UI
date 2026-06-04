package but.info.sae2_12.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BottomController {

    @FXML
    private Label statusPlayerLabel;

    @FXML
    private Label statusModeLabel;

    private MainController mainController;

    public void setMainController1(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        statusPlayerLabel.setText("Noir");
        statusModeLabel.setText("Déplacement");
    }

    public void updatePlayer(String player) {
        statusPlayerLabel.setText(player);
    }

    public void updateMode(String mode) {
        statusModeLabel.setText(mode);
    }

	public void setMainController(MainController mainController2) {
		// TODO Auto-generated method stub
		
	}
}