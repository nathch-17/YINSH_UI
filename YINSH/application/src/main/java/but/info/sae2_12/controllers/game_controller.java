package but.info.sae2_12.controllers;

import but.info.sae2_12.model.factory.AbstractFactory;
import but.info.sae2_12.model.factory.FactoryCube;
import but.info.sae2_12.model.factory.FactoryDoubled;
import but.info.sae2_12.model.factory.IFactory;
import but.info.sae2_12.model.state.IState;
import but.info.sae2_12.model.state.State;
import but.info.sae2_12.model.tokens.Token;
import coordinates.Coordinate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class game_controller {
    private IFactory factory;
    private IState state;

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

    @FXML public void handleNewGame(){

    }

    @FXML public void handleState1(){


    }

    @FXML public void handleState2(){

    }

    @FXML public void handleState3(){}



}
