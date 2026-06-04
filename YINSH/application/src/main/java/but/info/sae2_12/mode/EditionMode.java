package but.info.sae2_12.mode;

import but.info.sae2_12.controllers.MainController;
import but.info.sae2_12.model.Model;
import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.tokens.Pawn;
import but.info.sae2_12.view.HexSquare;
import coordinates.Coordinate;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class EditionMode extends InteractionMode{
    public EditionMode(MainController mainController){
        super(mainController);
    }

    @Override
    public void handleClick(MouseEvent event, HexSquare hex) {
        Model model = mainController.getModel();
        Coordinate clicked = hex.getCoordinate();
        if (event.getButton() == MouseButton.SECONDARY){
            model.removeToken(clicked);
        } else if (event.getButton() == MouseButton.PRIMARY){
            Class<?> tokenClass = Pawn.class;
            Team team = Team.BLACK;
            model.toggleToken(clicked, tokenClass, team);
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

    @Override
    public String toString(){
        return "Mode édition";
    }
}
