package modele.interaction;

import modele.Board;
import modele.entity.Entity;
import modele.entity.movable.character.npc.NonPlayerCharacter;

/**
 * Classe représentant l'interaction de frapper une entité.
 */
public class Hit implements Interaction {
    /**
     * Le nom affiché de l'interaction.
     */
    private final String displayName = "Frapper";

    /**
     * Interagit avec une entité pour la frapper si elle est un personnage non-joueur.
     *
     * @param entity L'entité avec laquelle interagir.
     */
    public void interact(Entity entity) {
        if (entity instanceof NonPlayerCharacter npc) {
            Board.getInstance().logAction(entity.getDisplayName() + " à été frappé");
            npc.hit(Board.getInstance().getPlayer());
        }
    }

    /**
     * Obtient le nom affiché de l'interaction.
     *
     * @return Le nom affiché de l'interaction.
     */
    public String getDisplayName() {
        return displayName;
    }
}