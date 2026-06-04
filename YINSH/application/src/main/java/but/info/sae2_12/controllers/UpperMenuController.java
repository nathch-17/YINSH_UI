package but.info.sae2_12.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class UpperMenuController {

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private static final String MAGIC = "SAE212";
    
    @FXML
    private void onAPropos(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("A propos");
        alert.setHeaderText("A propos");

        VBox content = new VBox(8);
        content.getChildren().addAll(
            new Label("Projet realise par :"),
            new Label("Louis Fontaine"),
            new Label("Armand Pivert"),
            new Label("Nathan Chaignon"),
            new Label("Thomas Fresny"),
            new Label("Clement Bazin"),
            new Label("BUT Informatique - Universite de Caen Normandie"),
            new Label("Annee 2025-2026")
        );

        alert.getDialogPane().setContent(content);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }
    
    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onSauvegarder(ActionEvent event) {}

    @FXML
    private void onCharger(ActionEvent event) {}
}