package but.info.sae2_12.controllers;

import but.info.sae2_12.model.Model;
import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.state.State;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Window;

public class MainController {

    Model model;
    Window w;

    private final BooleanProperty hasWinner = new SimpleBooleanProperty(false);

    @FXML
    private BottomController bottomPanelController;

    public void initialize() {
        bottomPanelController.setMainController(this);
    }

    public void setModel(Model m) {
        this.model = m;

        hasWinner.bind(Bindings.createBooleanBinding(
                () -> ((State) model.stateProperty().get()).winner() != null,
                model.stateProperty()
        ));

        hasWinner.addListener(obs -> {
            if (hasWinner.get()) showWinnerPopup();
        });
    }

    private void showWinnerPopup() {
        Team winner = getState().winner();
        String name = winner == Team.BLACK ? "Noir" : "Blanc";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de partie");
        alert.setContentText("Le joueur " + name + " a gagné !");
        alert.showAndWait();
    }

    public BooleanProperty hasWinnerProperty() {
        return hasWinner;
    }

    public State getState() {
        return (State) model.stateProperty().get();
    }

    public void setState(State s) {
        model.setCurrentState(s);
    }

    public Window getWindow() {
        return w;
    }
}