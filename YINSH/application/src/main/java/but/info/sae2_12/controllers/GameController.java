package but.info.sae2_12.controllers;

import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.factory.FactoryDoubled;
import but.info.sae2_12.model.factory.IFactory;
import but.info.sae2_12.model.state.IState;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class GameController {
    private IFactory factory;
    private IState state;
    private MainController mainController;

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


    @FXML private VBox vboxTypeTeam;



    @FXML private ColorPicker colorPicker1;
    @FXML private ColorPicker colorPicker2;
    @FXML private ColorPicker colorPicker3;


    @FXML private CheckBox chkShowCoordinates;
    @FXML private ListView<String> listViewCoordinateMode;


    @FXML private Slider sliderBorderThickness;




    @FXML public void handleNewGame(){
        mainController.getModel().setCurrentState(factory.randomGame());/// recupere le modele du mainController et changer le currenState en generant un etat aleatoire depuis factory
    }

    @FXML public void handleState1(){

        mainController.getModel().setCurrentState(factory.stateForBlackLineTest());



    }

    @FXML public void handleState2(){

        mainController.getModel().setCurrentState(factory.stateForWhiteLineTest());
    }

    @FXML public void handleState3(){

        mainController.getModel().setCurrentState(factory.doubleLineStateTest());
    }

    public void initialize(){
        chkEditMode.selectedProperty().addListener(((observable, oldValue, newValue) -> {
           vboxTypeTeam.setDisable(!newValue);
       }));


    }

    public Team getSelectedTeam(){
        return radioBlack.isSelected() ? Team.BLACK : Team.WHITE;
    }

    public boolean isRingsSelected(){
        return radioRing.isSelected();
    }

    public void setMainController(MainController mc){
        this.mainController = mc;
        this.factory=new FactoryDoubled();
        chkEditMode.selectedProperty().bindBidirectional(mc.editionModeProperty());
    }





}
