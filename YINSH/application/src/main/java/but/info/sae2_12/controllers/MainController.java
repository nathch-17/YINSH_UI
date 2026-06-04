package but.info.sae2_12.controllers;

import but.info.sae2_12.mode.EditionMode;
import but.info.sae2_12.mode.GameMode;
import but.info.sae2_12.mode.InteractionMode;
import but.info.sae2_12.mode.RemoveLineMode;
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
		IState base = factory.doubleLineStateTest();   // un plateau qui contient des lignes
// On recrée l'état en y stockant les lignes calculées (sinon removeLine refuse) :
		this.model = new Model(new State(base.board(), base.turn(), IState.getPawnsLines(base.board())));
		boardController.setMainController(this);
		currentMode.set(new RemoveLineMode(this));   // test provisoire

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

	/** donne le mode actuel */
	public InteractionMode getCurrentMode() {
		return currentMode.get();
	}

	public ObjectProperty<InteractionMode> currentModeProperty() {
		return currentMode;
	}
}
