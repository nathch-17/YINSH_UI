package but.info.sae2_12;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import but.info.sae2_12.model.factory.FactoryDoubled;
import but.info.sae2_12.model.factory.IFactory;
import but.info.sae2_12.model.state.IState;
import but.info.sae2_12.view.HexSquare;
import coordinates.Coordinate;
import javafx.scene.layout.Pane;
/**
 * JavaFX App
 * https://www.redblobgames.com/grids/hexagons/
 */
public class App extends Application {

    private static Scene scene;
    public static String mode;
    @Override
    public void start(Stage stage) {
        // ===== TEST PROVISOIRE : à supprimer une fois le contrôleur central en place =====
        Pane plateau = new Pane();

        // On génère un état de test rempli (pions + anneaux).
        IFactory factory = new FactoryDoubled();
        IState state = factory.testState();

        // Pour chaque coordonnée du plateau, on crée sa case et on l'ajoute au Pane.
        for (Coordinate c : state.board().keySet()) {
            HexSquare hex = new HexSquare(c, plateau);
            plateau.getChildren().add(hex);             // on ajoute la case
            hex.setToken(state.board().get(c));         // puis on affiche son contenu
        }

        Scene scene = new Scene(plateau, 800, 700);
        stage.setScene(scene);
        stage.setTitle("Plateau YINSH - test");
        stage.show();
        // ===== FIN DU TEST PROVISOIRE =====
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}