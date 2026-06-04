package but.info.sae2_12.controllers;

import but.info.sae2_12.CoordinateDisplayMode;
import but.info.sae2_12.mode.EditionMode;
import but.info.sae2_12.mode.GameMode;
import but.info.sae2_12.mode.InteractionMode;
import but.info.sae2_12.mode.RemoveLineMode;
import but.info.sae2_12.model.Model;
import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.factory.FactoryDoubled;
import but.info.sae2_12.model.state.State;
import coordinates.Coordinate;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

public class MainController {

    private Model model;

    @FXML private BorderPane root;
    @FXML private BoardControllers boardController;
    @FXML private BottomController bottomPanelController;
    @FXML private GameController gameController;
    @FXML private IAController iAViewController;
    @FXML private UpperMenuController upperMenuController;

    private final ObjectProperty<InteractionMode> currentMode = new SimpleObjectProperty<>();
    private final BooleanProperty editionMode = new SimpleBooleanProperty(false);
    private final BooleanProperty hasWinner = new SimpleBooleanProperty(false);
    private final BooleanProperty shwoCoordinates = new SimpleBooleanProperty(false);
    private final ObjectProperty<CoordinateDisplayMode> coordinateMode = new SimpleObjectProperty<>(CoordinateDisplayMode.DOUBLED);
    private final BooleanProperty showCoordinates = new SimpleBooleanProperty(false);
    public ObjectProperty<CoordinateDisplayMode> coordinateModeProperty() { return coordinateMode; }

    public void initialize() {
        setModel(new Model(new FactoryDoubled().emptyGame()));

        boardController.setMainController(this);
        bottomPanelController.setMainController(this);
        gameController.setMainController(this);
        iAViewController.setMainController(this);
        upperMenuController.setMainController(this);

        hasWinner.addListener((obs, avant, maintenant) -> {
            if (maintenant) showWinnerPopup();
        });

        model.stateProperty().addListener((obs, oldState, newState) ->
            iAViewController.rafraichirEvaluation(newState)
        );
    }

    public void setModel(Model m) {
        this.model = m;

        currentMode.bind(Bindings.createObjectBinding(
                this::computeMode,
                editionMode,
                model.stateProperty()
        ));

        hasWinner.bind(Bindings.createBooleanBinding(
                () -> getState().winner() != null,
                model.stateProperty()
        ));
    }

    private InteractionMode computeMode() {
        if (editionMode.get())                             return new EditionMode(this);
        if (!model.getCurrentState().getLines().isEmpty()) return new RemoveLineMode(this);
        return new GameMode(this);
    }

    private void showWinnerPopup() {
        Team winner = getState().winner();
        String name = (winner == Team.BLACK) ? "Noir" : "Blanc";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de partie");
        alert.setContentText("Le joueur " + name + " a gagné !");
        alert.showAndWait();
    }

    public Model getModel() { return model; }
    public State getState() { return (State) model.stateProperty().get(); }
    public void setState(State s) { model.setCurrentState(s); }
    public ObjectProperty<InteractionMode> currentModeProperty() { return currentMode; }
    public InteractionMode getCurrentMode() { return currentMode.get(); }
    public BooleanProperty editionModeProperty() { return editionMode; }
    public BooleanProperty hasWinnerProperty() { return hasWinner; }
    public BoardControllers getBoardController() { return boardController; }
    public BottomController getBottomController() { return bottomPanelController; }
    public GameController getGameController() { return gameController; }
    public IAController getIAController() { return iAViewController; }

    public Window getWindow() {
        if (root != null && root.getScene() != null) return root.getScene().getWindow();
        return null;
    }
    public BooleanProperty showCoordinatesProperty() { return showCoordinates; }
}