package but.info.sae2_12.controllers;

import but.info.sae2_12.view.HexSquare;
import coordinates.CoordinateDoubled;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;   // <-- nouvel import
import javafx.scene.shape.Circle;

public class BoardControllers {

    @FXML
    private Pane boardPane;

    @FXML
    private void initialize() {
        boardPane.getChildren().add(new HexSquare(new CoordinateDoubled(4, 0)));
        boardPane.getChildren().add(new HexSquare(new CoordinateDoubled(4, 2)));
        boardPane.getChildren().add(new HexSquare(new CoordinateDoubled(5, 1)));
    }
}