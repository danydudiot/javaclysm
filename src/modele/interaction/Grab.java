package modele.interaction;

import modele.Board;
import modele.Inventory;
import modele.InventoryItem;
import modele.entity.Entity;

/**
 * Classe représentant l'interaction de ramassage d'un objet.
 */
public class Grab implements Interaction {
    /**
     * Le nom affiché de l'interaction.
     */
    private final String displayName = "Prendre";

    /**
     * Interagit avec une entité pour la ramasser si elle est un objet d'inventaire.
     *
     * @param entity L'entité avec laquelle interagir.
     */
    public void interact(Entity entity) {
        if (entity instanceof InventoryItem inventoryItem) {
            Board board = Board.getInstance();
            try {
                Inventory.getInstance().add(inventoryItem);
                board.clearCase(entity.getX(), entity.getY());
                board.logAction(inventoryItem.getDisplayName() + " ramassé");
            } catch (Exception e) {
                board.logError(e.getMessage());
            }
        }
    }

    /**
     * Obtient le nom affiché de l'interaction.
     *
     * @return Le nom affiché de l'interaction.
     */
    @Override
    public String getDisplayName() {
        return displayName;
    }
}
