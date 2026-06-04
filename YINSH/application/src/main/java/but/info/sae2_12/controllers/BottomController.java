package but.info.sae2_12.controllers;

import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.tokens.Pawn;
import but.info.sae2_12.model.tokens.Ring;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BottomController {
	private MainController mainController;
	
	@FXML
	private Label statusPlayerLabel;

	@FXML
	private Label statusModeLabel;

	@FXML
	private Label blackRingsLabel;
	
	@FXML
	private Label blackPawnsLabel;
	
	@FXML
	private Label whiteRingsLabel;
	
	@FXML
	private Label whitePawnsLabel;

	@FXML
	public void initialize() {
		statusPlayerLabel.setText("Blanc");
		statusModeLabel.setText("Déplacement");
	}

	public void updatePlayer(String player) {
		statusPlayerLabel.setText(player);
	}

	public void updateMode(String mode) {
		statusModeLabel.setText(mode);
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;

		mainController.getModel().stateProperty().addListener((obs, oldState, newState) -> {
			if (newState != null) {
				Team turn = newState.turn();
				String playerName = (turn == Team.BLACK) ? "Noir" : "Blanc";
				Platform.runLater(() -> statusPlayerLabel.setText(playerName));
			}
		});

		mainController.currentModeProperty().addListener((obs, oldMode, newMode) -> {
			if (newMode != null) {
				Platform.runLater(() -> statusModeLabel.setText(newMode.toString()));
			}
		});

		mainController.getModel().stateProperty().addListener((obs, oldState, newState) -> {
			if (newState != null) {
				Team turn = newState.turn();
				String playerName = (turn == Team.BLACK) ? "Noir" : "Blanc";

				long blackRings = newState.board().values().stream()
						.filter(t -> t instanceof Ring && t.getTeam() == Team.BLACK).count();
				long whiteRings = newState.board().values().stream()
						.filter(t -> t instanceof Ring && t.getTeam() == Team.WHITE).count();
				long blackPawns = newState.board().values().stream()
						.filter(t -> t instanceof Pawn && t.getTeam() == Team.BLACK).count();
				long whitePawns = newState.board().values().stream()
						.filter(t -> t instanceof Pawn && t.getTeam() == Team.WHITE).count();

				Platform.runLater(() -> {
					statusPlayerLabel.setText(playerName);
					blackRingsLabel.setText(String.valueOf(blackRings));
					whiteRingsLabel.setText(String.valueOf(whiteRings));
					blackPawnsLabel.setText(String.valueOf(blackPawns));
					whitePawnsLabel.setText(String.valueOf(whitePawns));
				});
			}
		});
	}
}