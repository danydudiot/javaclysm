package modele.interaction;

import modele.Board;
import modele.Inventory;
import modele.entity.Entity;
import modele.entity.movable.character.Character;

public interface Interaction {
    public void interact(Inventory inventory, Entity entity, Character author);
    public String getDisplayName();

}
