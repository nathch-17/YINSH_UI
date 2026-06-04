package but.info.sae2_12.view;

import but.info.sae2_12.controllers.MainController;
import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.tokens.Pawn;
import but.info.sae2_12.model.tokens.Ring;
import but.info.sae2_12.model.tokens.Token;
import coordinates.Coordinate;
import coordinates.Point;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;



public class HexSquare extends Polygon {

    public static final double LARGEUR = 40;
    public static final double HAUTEUR = 46;
    public static final double MARGE = 40;

    private final Coordinate coordinate;

    private final Pane pane;
    private final Label label;

    private final double centreX;
    private final double centreY;

    private Shape form;

    private final MainController mainController;
    private Color baseColor = Color.LIGHTGRAY;

    public HexSquare(Coordinate coordinate, Pane pane, MainController mainController) {
        this.coordinate = coordinate;
        this.pane = pane;
        this.mainController = mainController;



        // Position du centre en pixels.
        Point point = coordinate.to2DCoordinate();
        this.centreX = point.x() * (LARGEUR / 2.0) + MARGE;
        this.centreY = point.y() * (3.0 * HAUTEUR / 4.0) + MARGE;


        // Les 6 sommets autour du centre.
        getPoints().addAll(
                centreX - LARGEUR / 2, centreY - HAUTEUR / 4, // haut-gauche
                centreX,               centreY - HAUTEUR / 2, // haut
                centreX + LARGEUR / 2, centreY - HAUTEUR / 4, // haut-droite
                centreX + LARGEUR / 2, centreY + HAUTEUR / 4, // bas-droite
                centreX,               centreY + HAUTEUR / 2, // bas
                centreX - LARGEUR / 2, centreY + HAUTEUR / 4  // bas-gauche
        );

        setFill(Color.LIGHTGRAY);
        setStroke(Color.BLACK);
        setStrokeWidth(1);

        setFill(baseColor);
        setStroke(Color.BLACK);
        setStrokeWidth(1);

        setOnMouseClicked(event -> {
            if (mainController.getCurrentMode() != null) {
                mainController.getCurrentMode().handleClick(event, this);
            }
        });
        setOnMouseEntered(event -> {
            if (mainController.getCurrentMode() != null) {
                mainController.getCurrentMode().entered(event, this);
            }
        });
        setOnMouseExited(event -> {
            if (mainController.getCurrentMode() != null) {
                mainController.getCurrentMode().exited(event, this);
            }
        });


        this.label = new Label(coordinate.toString());     // le texte
        this.label.setLayoutX(centreX - 10);       // placé ~au centre de la case
        this.label.setLayoutY(centreY - 8);
        this.label.setMouseTransparent(true);               // le clic traverse vers l'hexagone
        this.label.visibleProperty().bind(mainController.showCoordinatesProperty());
        pane.getChildren().add(this);
        pane.getChildren().add(label);
    }

    /** la case prend une couleur différente qund on hover */
    public void hover() {
        setFill(baseColor.darker());
    }

    /** fin du survol : retour à la couleur de base. */
    public void resetColor() {
        setFill(baseColor);
    }

    public void setBaseColor(Color color) {
        this.baseColor = color;
        setFill(color);
    }

    /**
     Change la forme affichée
     */
    public void setForm(Shape newForm) {
        if (this.form != null) {
            pane.getChildren().remove(this.form);
        }
        this.form = newForm;
        if (this.form != null) {
            pane.getChildren().add(this.form);
        }
    }

    /**
     Affiche le visuel correspondant au jeton du modèle.
     */
    public void setToken(Token token) {
        if (token instanceof Pawn) {
            setForm(createPawn(token.getTeam()));
        } else if (token instanceof Ring) {
            setForm(createRing(token.getTeam()));
        } else {
            setForm(null); // case vide
        }
    }

    /** pion. */
    private Shape createPawn(Team team) {
        Circle c = new Circle(centreX, centreY, LARGEUR / 4);
        c.setFill(team.getColor());
        c.setStroke(Color.GRAY);          // pour bien voir un pion blanc sur fond clair
        c.setMouseTransparent(true);      // les clics traversent vers l'hexagone
        return c;
    }

    /** anneau. */
    private Shape createRing(Team team) {
        Circle c = new Circle(centreX, centreY, LARGEUR / 3);
        c.setFill(Color.TRANSPARENT);     // anneau
        c.setStroke(team.getColor());
        c.setStrokeWidth(4);
        c.setMouseTransparent(true);
        return c;
    }

    /** marqueur pour indiquer une case accessible. */
    public Shape createMarker() {
        Circle c = new Circle(centreX, centreY, LARGEUR / 6);
        c.setFill(Color.LIMEGREEN);
        c.setMouseTransparent(true);
        return c;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Shape getForm() {
        return form;
    }

    public Label getLabel(){
        return label;
    }
}