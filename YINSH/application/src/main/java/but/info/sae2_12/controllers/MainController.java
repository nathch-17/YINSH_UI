package but.info.sae2_12.controllers;

import but.info.sae2_12.mode.EditionMode;
import but.info.sae2_12.mode.GameMode;
import but.info.sae2_12.mode.InteractionMode;
import but.info.sae2_12.mode.RemoveLineMode;
import but.info.sae2_12.model.Model;
import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.factory.FactoryDoubled;
import but.info.sae2_12.model.state.State;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Window;

public class MainController {

    private Model model;
    private Window w;

    // --- Sous-contrôleurs (injectés depuis Main.fxml) ---
    @FXML
    private BoardControllers boardController;       // fx:id="board"
    @FXML
    private BottomController bottomPanelController;  // fx:id="bottomPanel"
    @FXML
    private GameController gameController;           // fx:id="game"
    @FXML
    private IAController iAViewController;           // fx:id="iAView"

    // --- Propriétés partagées ---
    private final ObjectProperty<InteractionMode> currentMode = new SimpleObjectProperty<>();
    private final BooleanProperty editionMode = new SimpleBooleanProperty(false);
    private final BooleanProperty hasWinner = new SimpleBooleanProperty(false);

    public void initialize() {
        // 1. Le modèle DOIT exister avant les sous-contrôleurs (ils le lisent).
        setModel(new Model(new FactoryDoubled().emptyGame()));

        // 2. On donne le contrôleur principal à chaque sous-contrôleur.
        boardController.setMainController(this);
        bottomPanelController.setMainController(this);
        gameController.setMainController(this);
        iAViewController.setMainController(this);

        // 3. PopUp dès qu'un joueur gagne (listener ajouté une seule fois).
        hasWinner.addListener((obs, avant, maintenant) -> {
            if (maintenant) showWinnerPopup();
        });
    }

    /**
     * Définit le modèle et (re)câble les bindings qui en dépendent.
     * Appelée à l'initialisation, et plus tard à chaque nouvelle partie / chargement.
     */
    public void setModel(Model m) {
        this.model = m;

        // Mode courant : édition > suppression de ligne > jeu.
        currentMode.bind(Bindings.createObjectBinding(
                this::computeMode,
                editionMode,
                model.stateProperty()
        ));

        // Un gagnant existe-t-il dans l'état courant ?
        hasWinner.bind(Bindings.createBooleanBinding(
                () -> getState().winner() != null,
                model.stateProperty()
        ));
    }

    /** Calcul du mode courant selon la priorité du sujet. */
    private InteractionMode computeMode() {
        if (editionMode.get()) {
            return new EditionMode(this);
        } else if (!model.getCurrentState().getLines().isEmpty()) {
            return new RemoveLineMode(this);
        } else {
            return new GameMode(this);
        }
    }

    private void showWinnerPopup() {
        Team winner = getState().winner();
        String name = (winner == Team.BLACK) ? "Noir" : "Blanc";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de partie");
        alert.setContentText("Le joueur " + name + " a gagné !");
        alert.showAndWait();
    }

    // --- Accès au modèle et aux propriétés ---
    public Model getModel() { return model; }

    public State getState() { return (State) model.stateProperty().get(); }

    public void setState(State s) { model.setCurrentState(s); }

    public ObjectProperty<InteractionMode> currentModeProperty() { return currentMode; }

    public InteractionMode getCurrentMode() { return currentMode.get(); }

    public BooleanProperty editionModeProperty() { return editionMode; }

    public BooleanProperty hasWinnerProperty() { return hasWinner; }

    // --- Accès aux sous-contrôleurs ---
    public BoardControllers getBoardController() { return boardController; }

    public BottomController getBottomController() { return bottomPanelController; }

    public GameController getGameController() { return gameController; }

    public IAController getIAController() { return iAViewController; }

    public Window getWindow() { return w; }

    private final ObjectProperty<IState> state = new SimpleObjectProperty<>();

    public void updateGameState(IState newState) {
        this.state.set(newState);
    }

    public ObjectProperty<IState> stateProperty() {
        return state;
    }

    public IState getState() {
        return state.get();
    }
}