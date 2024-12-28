package modele.entity;

/**
 * Classe abstraite représentant tous les types d'entité.
 */
public abstract class Entity {
    /**
     * Position vertical sur la carte.
     */
    protected int x;

    /**
     * Position horizontale sur la carte.
     */
    protected int y;

    /**
     * Representation sur la carte.
     */
    protected String representation;

    /**
     * Nom d'affichage.
     */
    protected String displayName;

    /**
     * Constructeur abstrait.
     *
     * @param x La position vertical.
     * @param y La position horizontal.
     */
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retour la position sur la carte.
     *
     * @return La position sur la carte [x,y].
     */
    public int[] getPosition() {
        return new int[]{x, y};
    }

    /**
     * Permet de modifier la position.
     *
     * @param x La position vertical.
     * @param y La position horizontal.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Permet d'obtenir la représentation de l'entité.
     *
     * @return La représentation de l'entité.
     */
    public String getRepresentation() { // Existe, car on a besoin de la représentation brut dans les états.
        return representation;
    }

    /**
     * Permet d'obtenir la représentation de l'entité.
     *
     * @return La représentation de l'entité.
     */
    @Override
    public String toString() {
        return representation;
    }

    /**
     * Permet d'obtenir le nom de l'entité.
     *
     * @return Le nom de l'entité.
     */
    public String getDisplayName() {
        return displayName;
    }
}
