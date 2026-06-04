package but.info.sae2_12.controllers;

import but.info.sae2_12.model.tokens.Token;
import but.info.sae2_12.view.HexSquare;
import coordinates.Coordinate;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class BoardControllers {

    @FXML
    private Pane boardPane;

    private MainController mainController;

    private final Map<Coordinate, HexSquare> cases = new HashMap<>();

    @FXML
    private void initialize() {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        creerTerrain();

        mainController.getModel().stateProperty().addListener((obs, ancien, nouveau) -> refresh());

    }

    /** Redessine le contenu de chaque case d'après le modèle. */
    private void refresh() {
        Map<Coordinate, Token> board = mainController.getModel().getBoard();
        for (Coordinate c : cases.keySet()) {
            cases.get(c).setToken(board.get(c));
        }
    }

    /** Permet aux modes de récupérer la case d'une coordonnée. */
    public HexSquare getCase(Coordinate c) {
        return cases.get(c);
    }

    private void creerTerrain() {
        Map<Coordinate, Token> board = mainController.getModel().getBoard();

        for (Coordinate c : board.keySet()) {
            HexSquare hex = new HexSquare(c, boardPane, mainController);
            hex.setToken(board.get(c));
            cases.put(c, hex);
        }
    }
}