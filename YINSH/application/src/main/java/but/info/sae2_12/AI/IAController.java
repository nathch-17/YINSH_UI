package but.info.sae2_12.controller;

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

    /**
     * Permet au MainController d'injecter sa propre instance et d'initialiser l'IA.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        this.miniMax = new MiniMax();

        initialiserLiaisons();
    }

    /**
     * Lie de façon bidirectionnelle les champs de texte graphiques avec les variables de MiniMax.
     * Si l'utilisateur change la valeur dans l'interface, la variable change automatiquement dans l'algorithme.
     */
    private void initialiserLiaisons() {
        Bindings.bindBidirectional(txtWinScore.textProperty(), miniMax.winScoreProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtRingWeight.textProperty(), miniMax.ringRemovedWeightProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtPawnWeight.textProperty(), miniMax.pawnWeightProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtLineWeight.textProperty(), miniMax.nearLineWeightProperty(), new NumberStringConverter());
        txtMobilityWeight.setText("0.5");
    }

    /**
     * Calcule le score actuel et met à jour la ProgressBar entre 0.0 (Avantage Blanc) et 1.0 (Avantage Noir)
     * Cette méthode doit être appelée par votre MainController à chaque fois qu'un pion ou anneau bouge
     */
    public void rafraichirEvaluation(IState currentState) {
        double score = miniMax.evaluate(currentState, Team.BLACK);
        double maxScore = miniMax.getWinScore();
        double pourcentage = (score + maxScore) / (2 * maxScore);
        pourcentage = Math.max(0.0, Math.min(1.0, pourcentage));
        progressEvaluation.setProgress(pourcentage);
    }

    /**
     * Méthode déclenchée lors du clic sur le bouton "Suggérer un coup"
     */
    @FXML
    private void onSuggererCoup(ActionEvent event) {
        IState etatActuel = mainController.getCurrentState();
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