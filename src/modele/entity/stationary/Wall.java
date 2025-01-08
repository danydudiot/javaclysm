package modele.entity.stationary;

import modele.Colors;

/**
 * Classe représentant un mur dans le jeu.
 */
public class Wall extends StaticEntity {

    /**
     * Constructeur de la classe Wall.
     * Initialise la position du mur et sa représentation visuelle.
     *
     * @param x La coordonnée X du mur.
     * @param y La coordonnée Y du mur.
     */
    public Wall(int x, int y) {
        super(x, y);
        this.representation = Colors.WHITE + "#" + Colors.RESET;
        this.displayName = "Mur";
    }
}