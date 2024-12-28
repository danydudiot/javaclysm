package modele.entity.stationary.terrain;

import modele.Colors;
import modele.entity.Entity;
import modele.entity.stationary.StaticEntity;


/**
 * Classe abstraite représentant un case sur la carte.
 */
public abstract class Terrain extends StaticEntity {
    /**
     * Potentielle entité sur cette case (null si vide)
     */
    protected Entity entityOnCase;


    /**
     * Constructeur de la classe Terrain.
     *
     * @param x La position verticale de la case.
     * @param y La position horizontale de la case.
     */
    public Terrain(int x, int y) {
        super(x, y);
        this.entityOnCase = null;
    }

    /**
     * Définit l'entité présente sur cette case.
     *
     * @param entity L'entité a placé sur la case.
     */
    public void setEntityOnCase(Entity entity) {
        this.entityOnCase = entity;
    }

    /**
     * Supprime l'entité présente sur cette case.
     */
    public void clearEntityOnCase() {
        this.entityOnCase = null;
    }

    /**
     * Retourne l'entité présente sur cette case.
     *
     * @return L'entité présente sur la case, ou null si la case est vide.
     */
    public Entity getEntityOnCase() {
        return entityOnCase;
    }

    /**
     * Vérifie si la case est vide.
     *
     * @return true si la case est vide, false sinon.
     */
    public boolean isEmpty() {
        return entityOnCase == null;
    }


    /**
     * Retourne une représentation de la case ou de son contenu avec un fond jaune.
     *
     * @return Une représentation de la case ou de son contenu avec un fond jaune.
     */
    @Override
    public String toString() {
        if (entityOnCase == null) {
            return representation;
        } else {
            return entityOnCase.toString();
        }
    }
}
