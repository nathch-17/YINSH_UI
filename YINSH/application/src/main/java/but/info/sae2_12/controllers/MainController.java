package but.info.sae2_12.controllers;

import but.info.sae2_12.model.Model;
import but.info.sae2_12.model.factory.FactoryCube;
import but.info.sae2_12.model.factory.FactoryDoubled;
import but.info.sae2_12.model.factory.IFactory;
import but.info.sae2_12.model.state.*;
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
}
