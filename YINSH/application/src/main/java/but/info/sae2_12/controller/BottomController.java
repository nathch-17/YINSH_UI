package but.info.sae2_12.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BottomController {

    @FXML private Label statusPlayerLabel;
    @FXML private Label statusModeLabel;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;

        // Bind le joueur actuel
        mainController.currentTurnProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                statusPlayerLabel.setText(
                    newVal == but.info.sae2_12.model.Team.BLACK ? "Noir" : "Blanc"
                );
                statusPlayerLabel.setStyle(
                    newVal == but.info.sae2_12.model.Team.BLACK
                        ? "-fx-font-size: 11px; -fx-font-weight: bold;"
                        : "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: gray;"
                );
            }
        });

        // Bind le mode d'interaction
        mainController.interactionModeProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                statusModeLabel.setText(newVal.getLabel());
            }
        });
    }

    @FXML
    public void initialize() {
        // Valeurs par défaut à l'init
        statusPlayerLabel.setText("Noir");
        statusModeLabel.setText("Déplacement");
    }
}