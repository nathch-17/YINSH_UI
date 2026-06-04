package but.info.sae2_12.controllers;

import but.info.sae2_12.model.state.*;
import javafx.fxml.FXML;
import javafx.stage.Window;

public class MainController {
	
	State state;
	Window w;
	
	 @FXML
	    private BottomController bottomPanelController;

	 @FXML
	 public void initialize() {
	     bottomPanelController.setMainController(this);
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
