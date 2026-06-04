package but.info.sae2_12.mode;

import but.info.sae2_12.controllers.GameController;
import but.info.sae2_12.controllers.MainController;
import but.info.sae2_12.model.Model;
import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.state.State;
import but.info.sae2_12.model.tokens.Pawn;
import but.info.sae2_12.model.tokens.Ring;
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
        GameController gc = mainController.getGameController(); /// recupere game controller pour recup valeur des radios button

        boolean ring = gc.isRingsSelected(); /// radio ring true ou false

        Model model = mainController.getModel();
        Coordinate clicked = hex.getCoordinate();
        if (event.getButton() == MouseButton.SECONDARY){
            model.removeToken(clicked);
        } else if (event.getButton() == MouseButton.PRIMARY){
            Class<?> tokenClass = Pawn.class;
            if(ring) tokenClass = Ring.class;
            Team team = gc.getSelectedTeam(); /// change la couleur en fonction du radio button selectionné dans le gameController
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
