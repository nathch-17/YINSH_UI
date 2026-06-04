package but.info.sae2_12.controllers;

import javafx.scene.layout.Pane;

public class BoardControllers {
    private Pane boardPane;


    private void initialize(){
        javafx.scene.shape.Circle test = new javafx.scene.shape.Circle(100, 100, 30);
        boardPane.getChildren().add(test);
    }

}
