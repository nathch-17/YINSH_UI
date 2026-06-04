package but.info.sae2_12.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import but.info.sae2_12.model.state.IState;
import but.info.sae2_12.model.tokens.Ring;
import but.info.sae2_12.model.tokens.Token;
import coordinates.Coordinate;
import coordinates.CoordinateCube;
import javafx.stage.FileChooser;
import java.io.*;
import java.util.Map;

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
    
    
    @FXML
    private void onSauvegarder(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Sauvegarder la partie");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers YINSH (*.yns)", "*.yns"));
        File fichier = fc.showSaveDialog(mainController.getWindow());
        if (fichier == null) return;

        try (PrintWriter out = new PrintWriter(fichier)) {
            IState state = mainController.getState();
            out.println(MAGIC);
            out.println(state.turn().name());
            if (state.getLines().isEmpty()) {
                out.println("false");
            } else {
                out.println("true");
            }
            for (int i = 0; i < state.board().keySet().size(); i++) {
                Coordinate coord = (Coordinate) state.board().keySet().toArray()[i];
                Token t = state.board().get(coord);
                if (t == null) continue;
                CoordinateCube c = (CoordinateCube) coord;
                String type;
                if (t instanceof Ring) {
                    type = "R";
                } else {
                    type = "P";
                }
                String team = t.getTeam().name();
                out.println(c.getQ() + " " + c.getR() + " " + c.getS() + " " + type + " " + team);
            }
        } catch (IOException e) {
            afficherErreur("Erreur lors de la sauvegarde.");
        }
    }

    @FXML
    private void onCharger(ActionEvent event) {}
    
    
    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

}