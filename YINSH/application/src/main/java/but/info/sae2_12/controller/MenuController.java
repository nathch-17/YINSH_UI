package but.info.sae2_12.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MenuController {

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void onLoad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger une partie");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Fichiers YINSH (*.yns)", "*.yns")
        );

        Stage stage = mainController.getStage();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            mainController.loadGame(file);
        }
    }

    @FXML
    private void onSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarder la partie");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Fichiers YINSH (*.yns)", "*.yns")
        );
        fileChooser.setInitialFileName("partie.yns");

        Stage stage = mainController.getStage();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            mainController.saveGame(file);
        }
    }

    @FXML
    private void onAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("À propos");
        alert.setHeaderText("YINSH — SAE 2.1 & 2.2");
        alert.setContentText(
            "Développé par :\n" +
            "- Prénom NOM\n" +
            "- Prénom NOM\n" +
            "- Prénom NOM\n\n" +
            "Université de Caen Normandie — BUT Informatique 2025-2026"
        );
        alert.showAndWait();
    }
}