package but.info.sae2_12.mode;   // adapte à ton package

import but.info.sae2_12.controllers.MainController;
import but.info.sae2_12.model.Model;
import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.tokens.Pawn;
import but.info.sae2_12.model.tokens.Ring;
import but.info.sae2_12.model.tokens.Token;
import but.info.sae2_12.view.HexSquare;
import coordinates.Coordinate;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Set;

public class RemoveLineMode extends InteractionMode {

    /** Couleur de survol d'une ligne. */
    private static final Color HIGHLIGHT = Color.GOLD;
    /** Couleur d'une ligne verrouillée. */
    private static final Color LOCK = Color.ORANGE;

    /** La ligne verrouillée ,null tant qu'on n'a pas choisi  . */
    private Set<Coordinate> lockedLine = null;

    /** La ligne mise en surbrillance par le survol (null sinon). */
    private Set<Coordinate> hoveredLine = null;

    public RemoveLineMode(MainController mainController) {
        super(mainController);
    }

    @Override
    public void handleClick(MouseEvent event, HexSquare hex) {
        Model model = mainController.getModel();
        Coordinate clicked = hex.getCoordinate();

        if (lockedLine == null) {
            // --- Phase 1 : on choisit la ligne ---
            Set<Coordinate> line = findLine(clicked);
            if (line != null) {
                lockedLine = line;
                hoveredLine = null;
                paint(lockedLine, LOCK);
            }
        } else {
            Token token = model.getTokenAt(clicked);
            if (token instanceof Ring && token.getTeam() == lineTeam(lockedLine)) {
                paint(lockedLine, null);
                model.removeLine(lockedLine, clicked);
                lockedLine = null;
            }
            // sinon rien ne se passe
        }
    }

    @Override
    public void entered(MouseEvent event, HexSquare hex) {
        if (lockedLine != null) return;

        Set<Coordinate> line = findLine(hex.getCoordinate());
        if (line != null) {
            hoveredLine = line;
            paint(hoveredLine, HIGHLIGHT);
        }
    }

    @Override
    public void exited(MouseEvent event, HexSquare hex) {
        if (hoveredLine != null) {
            paint(hoveredLine, null);   // revient à la couleur de base
            hoveredLine = null;
        }
    }

    /**
     * Première ligne disponible contenant cette coordonnée (priorité à la première vue).
     * donne la ligne, ou null si la coordonnée n'appartient à aucune ligne.*/
    private Set<Coordinate> findLine(Coordinate c) {
        List<Set<Coordinate>> lines = mainController.getModel().getPawnsLines();
        for (Set<Coordinate> line : lines) {
            if (line.contains(c)) {
                return line;
            }
        }
        return null;
    }

    /** colore toutes les cases d'une ligne.*/
    private void paint(Set<Coordinate> line, Color color) {
        for (Coordinate c : line) {
            HexSquare hex = mainController.getBoardController().getCase(c);
            if (color == null) {
                hex.resetColor();
            } else {
                hex.setFill(color);
            }
        }
    }

    /** donne l'équipe des pions de la ligne */
    private Team lineTeam(Set<Coordinate> line) {
        Coordinate first = line.iterator().next();
        Pawn pawn = (Pawn) mainController.getModel().getTokenAt(first);
        return pawn.getTeam();
    }

    @Override
    public String toString() {
        return "Mode retirer une ligne";
    }
}