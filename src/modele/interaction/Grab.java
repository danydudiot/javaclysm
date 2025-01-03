package modele.interaction;

import modele.Board;
import modele.Inventory;
import modele.InventoryItem;
import modele.entity.Entity;

public class Grab implements Interaction {
    private final String displayName = "Prendre";

    public void interact(Entity entity){
        if (entity instanceof InventoryItem){
            Board board = Board.getInstance();
            try {
                Inventory.getInstance().add((InventoryItem) entity);
                System.out.println("Grab");
                board.clearCase(entity.getX(), entity.getY());
                board.logAction(((InventoryItem) entity).getDisplayName() + " ramassé");
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
