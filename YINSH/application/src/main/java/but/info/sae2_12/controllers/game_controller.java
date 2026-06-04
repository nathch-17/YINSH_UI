package but.info.sae2_12.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class game_controller {

    @FXML
    private Button btnNewGame;
    @FXML private Button btnState1;
    @FXML private Button btnState2;
    @FXML private Button btnState3;


    @FXML private CheckBox chkEditMode;
    @FXML private RadioButton radioPion;
    @FXML private RadioButton radioRing;
    @FXML private RadioButton radioBlack;
    @FXML private RadioButton radioWhite;
    private ToggleGroup toggleGroupType;  // Pas @FXML (créé en code)
    private ToggleGroup toggleGroupTeam;  // Pas @FXML


    @FXML private ColorPicker colorPicker1;
    @FXML private ColorPicker colorPicker2;
    @FXML private ColorPicker colorPicker3;


    @FXML private CheckBox chkShowCoordinates;
    @FXML private ListView<String> listViewCoordinateMode;


    @FXML private Slider sliderBorderThickness;
    


}
