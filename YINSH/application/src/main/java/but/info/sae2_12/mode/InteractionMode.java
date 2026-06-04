package but.info.sae2_12.mode;

import but.info.sae2_12.controllers.MainController;
import but.info.sae2_12.view.HexSquare;
import javafx.scene.input.MouseEvent;

public abstract class InteractionMode {
    protected final MainController mainController;

    public InteractionMode(MainController mainController) {
        this.mainController = mainController;
    }

    public abstract void handleClick(MouseEvent event, HexSquare hex);

    public abstract void entered(MouseEvent event, HexSquare hex);

    public abstract void exited(MouseEvent event, HexSquare hex);
}
