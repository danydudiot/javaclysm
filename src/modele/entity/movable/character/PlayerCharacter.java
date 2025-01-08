package modele.entity.movable.character;

import modele.Board;
import modele.Colors;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

/**
 * Classe représentant le personnage joueur dans le jeu.
 */
public class PlayerCharacter extends Character {
    /**
     * L'orientation actuelle du joueur.
     */
    protected char orientation;

    /**
     * Constructeur de la classe PlayerCharacter.
     * Initialise la position du joueur et sa représentation visuelle.
     *
     * @param x La coordonnée X du joueur.
     * @param y La coordonnée Y du joueur.
     */
    public PlayerCharacter(int x, int y) {
        super(x, y);
        this.representation = Colors.PLAYER + "@" + Colors.RESET;
        this.orientation = 'z';
        this.displayName = "Joueur";
    }

    /**
     * Obtient l'orientation actuelle du joueur.
     *
     * @return L'orientation actuelle du joueur.
     */
    public char getOrientation() {
        return orientation;
    }

    /**
     * Vérifie si le joueur peut se déplacer dans la direction donnée.
     *
     * @param direction La direction du déplacement.
     * @return true si le joueur peut se déplacer dans la direction donnée, sinon false.
     */
    public boolean canMove(char direction) {
        Terrain target = Board.getInstance().getToward(x, y, direction);
        return target != null && target.isEmpty() && (target instanceof Empty);
    }

    /**
     * Déplace le joueur dans la direction donnée.
     *
     * @param direction La direction du déplacement.
     */
    public void move(char direction) {
        if (canMove(direction)) {
            Board.getInstance().moveToward(this, direction);
            changeOrientation(direction);
        } else {
            Board.getInstance().logError("Déplacement impossible");
        }
    }

    /**
     * Change l'orientation du joueur.
     *
     * @param orientation La nouvelle orientation.
     */
    public void changeOrientation(char orientation) {
        if ("oklm".indexOf(orientation) != -1) {
            this.orientation = switch (orientation) {
                case 'o' -> 'z';
                case 'k' -> 'q';
                case 'l' -> 's';
                case 'm' -> 'd';
                default -> throw new IllegalStateException("Unexpected value: " + orientation);
            };
        } else if ("zqsd".indexOf(orientation) != -1) {
            this.orientation = orientation;
        }
    }
}
