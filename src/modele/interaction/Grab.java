package modele.interaction;

import modele.Board;
import modele.Inventory;
import modele.InventoryItem;
import modele.entity.Entity;
import modele.entity.movable.character.Character;

public class Grab implements Interaction {
    private final String displayName = "Prendre";

    public void interact(Inventory inventory, Entity entity, Character author){
        if (entity instanceof InventoryItem){
            Board board = Board.getInstance();
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
