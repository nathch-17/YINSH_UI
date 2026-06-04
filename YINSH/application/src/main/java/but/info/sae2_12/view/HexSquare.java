package but.info.sae2_12.view;

import coordinates.Coordinate;
import coordinates.Point;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;



public class HexSquare extends Polygon {

    // taille d'une case en pixels
    public static final double LARGEUR = 40;
    public static final double HAUTEUR = 46;

    //la coordonnée logique représentée par cette case
    private final Coordinate coordinate;

    public HexSquare(Coordinate coordinate) {
        this.coordinate = coordinate;

        //centre visuel de l'hexagone, à partir de la coordonnée
        Point centre = coordinate.to2DCoordinate();
        //convertit la position en pixels
        double cx = centre.x() * (LARGEUR / 2.0);        // décalage horizontal
        double cy = centre.y() * (HAUTEUR * 3.0 / 4.0);  // décalage vertical

        // les 6 sommets autour du centre (cx, cy),
        getPoints().addAll(
                cx - LARGEUR / 2, cy - HAUTEUR / 4,  // haut-gauche
                cx,               cy - HAUTEUR / 2,  // haut
                cx + LARGEUR / 2, cy - HAUTEUR / 4,  // haut-droite
                cx + LARGEUR / 2, cy + HAUTEUR / 4,  // bas-droite
                cx,               cy + HAUTEUR / 2,  // bas
                cx - LARGEUR / 2, cy + HAUTEUR / 4   // bas-gauche
        );
        //temporaire
        setFill(Color.LIGHTGRAY);
        setStroke(Color.BLACK);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}