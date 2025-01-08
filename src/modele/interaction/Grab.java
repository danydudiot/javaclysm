package modele.interaction;

import modele.Board;
import modele.Inventory;
import modele.InventoryItem;
import modele.entity.Entity;

public class Grab implements Interaction {
    private final String displayName = "Prendre";

    public void interact(Entity entity){
        if (entity instanceof InventoryItem inventoryItem){
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

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
