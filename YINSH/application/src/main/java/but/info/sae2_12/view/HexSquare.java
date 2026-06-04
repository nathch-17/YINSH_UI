package but.info.sae2_12.view;

import but.info.sae2_12.model.Team;
import but.info.sae2_12.model.tokens.Pawn;
import but.info.sae2_12.model.tokens.Ring;
import but.info.sae2_12.model.tokens.Token;
import coordinates.Coordinate;
import coordinates.Point;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;



public class HexSquare extends Polygon {

    public static final double LARGEUR = 40;
    public static final double HAUTEUR = 46;
    public static final double MARGE = 40;

    /** La coordonnée (du modèle) représentée par cette case. */
    private final Coordinate coordinate;

    /** Le Pane central sur lequel on ajoute/retire les formes. */
    private final Pane pane;

    /** Centre de l'hexagone en pixels (calculé une seule fois). */
    private final double centreX;
    private final double centreY;

    /** Forme actuellement posée sur la case : pion, anneau, marqueur ou null. */
    private Shape form;

    public HexSquare(Coordinate coordinate, Pane pane) {
        this.coordinate = coordinate;
        this.pane = pane;

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

    /** Disque plein représentant un pion. */
    private Shape createPawn(Team team) {
        Circle c = new Circle(centreX, centreY, LARGEUR / 4);
        c.setFill(team.getColor());
        c.setStroke(Color.GRAY);          // pour bien voir un pion blanc sur fond clair
        c.setMouseTransparent(true);      // les clics traversent vers l'hexagone
        return c;
    }

    /** Cercle évidé représentant un anneau. */
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
}