package but.info.sae2_12.controllers;

import but.info.sae2_12.mode.EditionMode;
import but.info.sae2_12.mode.GameMode;
import but.info.sae2_12.mode.InteractionMode;
import but.info.sae2_12.mode.RemoveLineMode;
import but.info.sae2_12.model.Model;
import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.factory.FactoryDoubled;
import but.info.sae2_12.model.factory.IFactory;
import but.info.sae2_12.model.state.*;
import but.info.sae2_12.model.tokens.Pawn;
import but.info.sae2_12.model.tokens.Ring;
import coordinates.CoordinateDoubled;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.stage.Window;


public class MainController {
	
	State state;
	Window w;
	private Model model;

	@FXML
	private BoardControllers boardController;

	public void initialize() {
		IFactory factory = new FactoryDoubled();
		this.model = new Model(factory.emptyState());   // plateau vide
		boardController.setMainController(this);

		currentMode.bind(Bindings.createObjectBinding(
				this::computeMode,
				editionMode,
				model.stateProperty()
		));
	// construit le terrain + branche le listener

		// On place une ligne de 5 pions blancs alignés + un anneau blanc, pour tester.
		model.toggleToken(new CoordinateDoubled(4, 0), Pawn.class, Team.WHITE);
		model.toggleToken(new CoordinateDoubled(4, 2), Pawn.class, Team.WHITE);
		model.toggleToken(new CoordinateDoubled(4, 4), Pawn.class, Team.WHITE);
		model.toggleToken(new CoordinateDoubled(4, 6), Pawn.class, Team.WHITE);
		model.toggleToken(new CoordinateDoubled(4, 8), Pawn.class, Team.WHITE);
		model.toggleToken(new CoordinateDoubled(4, 10), Ring.class, Team.WHITE);
		// test provisoire

		System.out.println("Nombre de lignes détectées : " + model.getPawnsLines().size());
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

	private final BooleanProperty editionMode = new SimpleBooleanProperty(true);

	public BooleanProperty editionModeProperty(){
		return editionMode;
	}

	//calcul mode actuel selon priorité
	private InteractionMode computeMode(){
		if (editionMode.get()){
			return new EditionMode(this);
		}else if (!model.getCurrentState().getLines().isEmpty()){
			return new RemoveLineMode(this);
		}else {
			return new GameMode(this);
		}
	}
}
