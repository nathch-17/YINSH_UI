package but.info.sae2_12.controllers;

import but.info.sae2_12.model.tokens.Token;
import but.info.sae2_12.view.HexSquare;
import coordinates.Coordinate;
import coordinates.CoordinateDoubled;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;   // <-- nouvel import
import javafx.scene.shape.Circle;

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

    public void setMainController(MainController mainController){
        this.mainController = mainController;
        creerTerrain();
    }

    private void creerTerrain() {
        Map<Coordinate, Token> board = mainController.getModel().getBoard();

        for (Coordinate c : board.keySet()) {
            HexSquare hex = new HexSquare(c, boardPane);
            boardPane.getChildren().add(hex);
            hex.setToken(board.get(c));
            cases.put(c, hex);
        }
    }
}