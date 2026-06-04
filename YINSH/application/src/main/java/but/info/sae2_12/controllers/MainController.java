package but.info.sae2_12.controllers;

import but.info.sae2_12.mode.GameMode;
import but.info.sae2_12.mode.InteractionMode;
import but.info.sae2_12.model.Model;
import but.info.sae2_12.model.factory.FactoryDoubled;
import but.info.sae2_12.model.factory.IFactory;
import but.info.sae2_12.model.state.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.stage.Window;


public class MainController {
	
	State state;
	Window w;
	private Model model;

	@FXML
	private BoardControllers boardController;

	public void initialize(){
		IFactory factory = new FactoryDoubled();
		this.model = new Model(factory.testState());

		boardController.setMainController(this);
		// TEST PROVISOIRE : à retirer une fois le binding du 3.1.2 en place.
		currentMode.set(new GameMode(this));
	}

	public Model getModel() {
		return model;
	}

	public State getState() {
		return this.state;
	}
	public void setState(State s) {
		this.state=s;
	}
	public Window getWindow() {
		return this.w;
	}

	public BoardControllers getBoardController(){
		return boardController;
	}

	public InteractionMode getInteractionMode(){
		return currentMode.get();
	}

	private final ObjectProperty<InteractionMode> currentMode = new SimpleObjectProperty<>();

	/** @return le mode courant (utilisé par HexSquare). */
	public InteractionMode getCurrentMode() {
		return currentMode.get();
	}

	/** La propriété, pour pouvoir y attacher des listeners / bindings (3.1.2). */
	public ObjectProperty<InteractionMode> currentModeProperty() {
		return currentMode;
	}
}
