package modele.interaction;

import modele.Board;
import modele.Inventory;
import modele.entity.Entity;
import modele.entity.movable.character.Character;

public class Hit implements Interaction {
    private final String displayName = "Frapper";

    public void interact(Inventory inventory, Board board, Entity entity){
        if (entity instanceof Character){
            ((modele.entity.movable.character.Character) entity).hit();
            board.logAction(entity.getDisplayName() + " à été frappe");
        }
    }

    public String getDisplayName() {
        return displayName;
    }
}
