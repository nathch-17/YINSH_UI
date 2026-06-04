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
import coordinates.CoordinateDoubled;
import javafx.stage.FileChooser;
import java.io.*;
import java.util.*;
import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.state.State;
import but.info.sae2_12.model.tokens.Pawn;

public class UpperMenuController {

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private static final byte[] MAGIC = "SAE212".getBytes();

    @FXML
    private void onAPropos(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("A propos");
        alert.setHeaderText("A propos");
        VBox content = new VBox(8);
        content.getChildren().addAll(
            new Label("Projet réalisé par :"),
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
            out.write(new String(MAGIC) + "\n");
            IState state = mainController.getState();
            out.println(state.turn().name());
            out.println(!state.getLines().isEmpty());
            for (Map.Entry<Coordinate, Token> entry : state.board().entrySet()) {
                Token t = entry.getValue();
                if (t != null)
                    out.println(entry.getKey() + " " + (t instanceof Ring ? "R" : "P") + " " + t.getTeam().name());
            }
        } catch (IOException e) {
            afficherErreur("Erreur lors de la sauvegarde.");
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
            if (!in.nextLine().equals(new String(MAGIC))) {
                afficherErreur("Format de fichier invalide");
                return;
            }
            Team turn = Team.valueOf(in.nextLine());
            boolean hasLines = Boolean.parseBoolean(in.nextLine());

            Map<Coordinate, Token> board = new HashMap<>();
            for (Coordinate c : mainController.getState().board().keySet())
                board.put(c, null);

            while (in.hasNextLine()) {
                // ligne format : "[y, x] R/P TEAM"
                String[] parts = in.nextLine().replaceAll("[\\[\\]]", "").split("[, ]+");
                Coordinate coord = new CoordinateDoubled(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                Token token = parts[2].equals("R") ? new Ring(Team.valueOf(parts[3])) : new Pawn(Team.valueOf(parts[3]));
                board.put(coord, token);
            }

            List<Set<Coordinate>> lines = hasLines ? IState.getPawnsLines(board) : new ArrayList<>();
            mainController.setState(new State(board, turn, lines));
        } catch (IOException e) {
            afficherErreur("Erreur lors du chargement.");
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
}