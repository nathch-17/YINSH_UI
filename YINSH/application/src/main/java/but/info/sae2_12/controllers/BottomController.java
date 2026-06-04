package but.info.sae2_12.controllers;

import but.info.sae2_12.model.Team;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BottomController {

    @FXML
    private Label statusPlayerLabel;

    @FXML
    private Label statusModeLabel;

    private MainController mainController;

    

    @FXML
    public void initialize() {
        statusPlayerLabel.setText("Blanc");
        statusModeLabel.setText("Déplacement");
    }

    public void updatePlayer(String player) {
        statusPlayerLabel.setText(player);
    }

    public void updateMode(String mode) {
        statusModeLabel.setText(mode);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;

        
        mainController.getModel().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState != null) {
                Team turn = newState.turn();
                String playerName = (turn == Team.BLACK) ? "Noir" : "Blanc";
                Platform.runLater(() -> statusPlayerLabel.setText(playerName));
            }
        });

       
        mainController.currentModeProperty().addListener((obs, oldMode, newMode) -> {
            if (newMode != null) {
                Platform.runLater(() -> statusModeLabel.setText(newMode.toString()));
            }
        });
    }
}