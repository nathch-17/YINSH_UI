package but.info.sae2_12.mode;

import but.info.sae2_12.controllers.MainController;
import but.info.sae2_12.mode.InteractionMode;
import but.info.sae2_12.model.Model;
import but.info.sae2_12.model.tokens.Ring;
import but.info.sae2_12.model.tokens.Token;
import but.info.sae2_12.view.HexSquare;
import coordinates.Coordinate;
import javafx.scene.input.MouseEvent;

import java.util.HashSet;
import java.util.Set;

public class GameMode extends InteractionMode {

    /** anneau sélectionné au 1er clic  */
    private Coordinate selectedRing = null;

    /** cases accessibles actuellement marquées */
    private Set<Coordinate> markedCells = new HashSet<>();

    public GameMode(MainController mainController) {
        super(mainController);
    }

    @Override
    public void handleClick(MouseEvent event, HexSquare hex) {
        Model model = mainController.getModel();
        Coordinate clicked = hex.getCoordinate();
        Token token = model.getTokenAt(clicked);

        if (selectedRing == null) {
            // 1er clic : il faut cliquer sur un anneau du joueur dont c'est le tour.
            if (token instanceof Ring && token.getTeam() == model.getTurn()) {
                selectedRing = clicked;
                showMarkers(model.movesFrom(clicked));
            }
        } else {
            // 2e clic.
            if (markedCells.contains(clicked)) {
                // Case accessible = on déplace l'anneau.
                clearMarkers();
                model.moveRing(selectedRing, clicked);   // mets a jour
                selectedRing = null;
            } else {
                // Ailleurs = on annule
                clearMarkers();
                selectedRing = null;
                if (token instanceof Ring && token.getTeam() == model.getTurn()) {
                    selectedRing = clicked;
                    showMarkers(model.movesFrom(clicked));
                }
            }
        }
    }

    @Override
    public void entered(MouseEvent event, HexSquare hex) {
        hex.hover();
    }

    @Override
    public void exited(MouseEvent event, HexSquare hex) {
        hex.resetColor();
    }

    /** indique avec un marqueur chaque case accessible. */
    private void showMarkers(Set<Coordinate> destinations) {
        markedCells = destinations;
        for (Coordinate c : destinations) {
            HexSquare hex = mainController.getBoardController().getCase(c);
            hex.setForm(hex.createMarker());
        }
    }

    /** efface tous les marqueurs. */
    private void clearMarkers() {
        for (Coordinate c : markedCells) {
            HexSquare hex = mainController.getBoardController().getCase(c);
            hex.setForm(null);
        }
        markedCells = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Mode jeu";
    }
}