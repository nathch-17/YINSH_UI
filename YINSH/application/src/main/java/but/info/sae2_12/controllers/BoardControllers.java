package but.info.sae2_12.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;   // <-- nouvel import
import javafx.scene.shape.Circle;

public class BoardControllers {

    @FXML
    private Pane boardPane;

    @FXML
    private void initialize() {
        // 1. Est-ce que initialize() est bien appelée ? Et boardPane est-il bien injecté ?
        System.out.println(">>> initialize() appelée. boardPane = " + boardPane);

        // 2. Cercle, cette fois VRAIMENT rouge
        Circle test = new Circle(100, 100, 30);
        test.setFill(Color.RED);   // on force le rouge explicitement
        boardPane.getChildren().add(test);

        // 3. Confirme que le cercle a bien été ajouté
        System.out.println(">>> Nombre d'enfants du pane : " + boardPane.getChildren().size());
    }
}