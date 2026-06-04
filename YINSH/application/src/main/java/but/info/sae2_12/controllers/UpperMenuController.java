package but.info.sae2_12.controllers
;

import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.state.IState;
import but.info.sae2_12.model.state.State;
import but.info.sae2_12.model.tokens.Pawn;
import but.info.sae2_12.model.tokens.Ring;
import but.info.sae2_12.model.tokens.Token;
import coordinates.Coordinate;
import coordinates.CoordinateCube;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.*;

public class UpperMenuController {

    private static final String MAGIC = "SAE212";
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void onAPropos(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("A propos");
        alert.setHeaderText("YINSH - SAE 2.1 & 2.2");

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
            out.println(state.getLines().isEmpty() ? "false" : "true");

            for (Map.Entry<Coordinate, Token> entry : state.board().entrySet()) {
                if (entry.getValue() == null) continue;
                CoordinateCube c = (CoordinateCube) entry.getKey();
                Token t = entry.getValue();
                out.println(c.getQ() + " " + c.getR() + " " + c.getS() + " " + (t instanceof Ring ? "R" : "P") + " " + t.getTeam().name());
            }
        } catch (IOException e) {
            afficherErreur("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    @FXML
    private void onCharger(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Charger une partie");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers YINSH (*.yns)", "*.yns"));
        File fichier = fc.showOpenDialog(mainController.getWindow());
        if (fichier == null) return;

        try (Scanner in = new Scanner(fichier)) {
            if (!in.nextLine().equals(MAGIC)) {
                afficherErreur("Format de fichier invalide");
                return;
            }

            Team turn = Team.valueOf(in.nextLine());
            boolean hasLines = in.nextLine().equals("true");

            Map<Coordinate, Token> board = new HashMap<>();
            for (Coordinate c : mainController.getState().board().keySet())
                board.put(c, null);

            while (in.hasNextLine()) {
                String[] parts = in.nextLine().split(" ");
                Coordinate coord = new CoordinateCube(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                Team team = Team.valueOf(parts[4]);
                board.put(coord, parts[3].equals("R") ? new Ring(team) : new Pawn(team));
            }

            List<Set<Coordinate>> lines = hasLines ? IState.getPawnsLines(board) : new ArrayList<>();
            mainController.setState(new State(board, turn, lines));

        } catch (IOException e) {
            afficherErreur("Erreur lors du chargement : " + e.getMessage());
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
}