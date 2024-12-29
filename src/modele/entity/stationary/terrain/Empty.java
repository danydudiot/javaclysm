package modele.entity.stationary.terrain;

/**
 * Classe représentant une case vide sur la carte.
 */
public class Empty extends Terrain {

    /**
     * Constructeur qui défini la représentation " " et le displayName "Vide".
     *
     * @param x La position verticale de la case.
     * @param y La position horizontale de la case.
     */
    public Empty(int x, int y) {
        super(x, y);
        this.representation = " ";
        this.displayName = "Vide";
        this.highlightColor = "";
    }
}
