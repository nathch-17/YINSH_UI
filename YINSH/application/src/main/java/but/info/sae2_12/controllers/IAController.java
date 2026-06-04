package but.info.sae2_12.controllers;

import but.info.sae2_12.AI.MiniMax;
import but.info.sae2_12.AI.MinimaxAI;
import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.actions.Action;
import but.info.sae2_12.model.state.IState;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class IAController {
    @FXML private TextField txtWinScore;
    @FXML private TextField txtRingWeight;
    @FXML private TextField txtPawnWeight;
    @FXML private TextField txtLineWeight;
    @FXML private TextField txtMobilityWeight;
    @FXML private ProgressBar progressEvaluation;
    private MainController mainController;
    private MiniMax miniMax;

    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        this.miniMax = new MiniMax();

        initialiserLiaisons();
    }

    
    private void initialiserLiaisons() {
        Bindings.bindBidirectional(txtWinScore.textProperty(), miniMax.winScoreProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtRingWeight.textProperty(), miniMax.ringRemovedWeightProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtPawnWeight.textProperty(), miniMax.pawnWeightProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtLineWeight.textProperty(), miniMax.nearLineWeightProperty(), new NumberStringConverter());
        txtMobilityWeight.setText("0.5");
    }

    public void rafraichirEvaluation(IState currentState) {
        Platform.runLater(() -> {
            if (currentState == null) return;
            double score = miniMax.evaluate(currentState, Team.BLACK);
            double winScore = miniMax.getWinScore();
            double pourcentage;
            if (score >= winScore) {
                pourcentage = 1.0;
            } else if (score <= -winScore) {
                pourcentage = 0.0;
            } 
            else {
                double maxTactique = 500.0; 
                pourcentage = (score + maxTactique) / (2 * maxTactique);
                pourcentage = Math.max(0.05, Math.min(0.95, pourcentage));
            }
            progressEvaluation.setProgress(pourcentage);
        });
    }

   
    @FXML
    private void onSuggererCoup(ActionEvent event) {
        IState etatActuel = mainController.getState();
        Team joueurActuel = etatActuel.turn();
        MinimaxAI iaAide = new MinimaxAI(joueurActuel, 3);
        Action meilleurCoup = iaAide.chooseMove(etatActuel);
        Alert popup = new Alert(Alert.AlertType.INFORMATION);
        popup.setTitle("Suggestion de l'IA");
        popup.setHeaderText("Meilleur coup estimé pour les " + (joueurActuel == Team.BLACK ? "Noirs" : "Blancs") + " :");

        if (meilleurCoup != null) {
            popup.setContentText(meilleurCoup.toString());
        } else {
            popup.setContentText("Aucun coup exploitable trouvé (Fin de partie ou blocage complet).");
        }
        popup.showAndWait();
    }
}