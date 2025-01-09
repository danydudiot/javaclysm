package modele.interaction;

import modele.entity.Entity;

/**
 * Interface représentant une interaction.
 */
public interface Interaction {

    /**
     * Interagit avec une entité spécifiée.
     *
     * @param entity L'entité avec laquelle interagir.
     */
    void interact(Entity entity);

    /**
     * Obtient le nom affiché de l'interaction.
     *
     * @return Le nom affiché de l'interaction.
     */
    String getDisplayName();
}
