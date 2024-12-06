package modele.interaction;

import modele.Board;
import modele.Inventory;
import modele.InventoryItem;
import modele.entity.Entity;

public class Grab implements Interaction {
    private final String displayName = "Prendre";

    public void interact(Inventory inventory, Board board, Entity entity){
        if (entity instanceof InventoryItem){
            try {
                inventory.add((InventoryItem) entity);
                board.clearCase(entity.getPosition()[0], entity.getPosition()[1]);
                board.logAction(((InventoryItem) entity).getDisplayName() + " ramassé");
            } catch (Exception e) {
                board.logAction(e.getMessage());
            }
        }

    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
