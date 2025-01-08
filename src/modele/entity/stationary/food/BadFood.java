package modele.entity.stationary.food;

/**
 * Classe abstraite représentant la mauvaise nourriture qui rend junkie.
 */
public abstract class BadFood extends Food {
    /**
     * Constructeur abstrait.
     *
     * @param x La position vertical.
     * @param y La position horizontal.
     */
    public BadFood(int x, int y) {
        super(x, y);
    }
}
