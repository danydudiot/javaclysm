package modele.interaction;

import modele.Board;
import modele.Inventory;
import modele.entity.Entity;

public interface Interaction {
    public void interact(Inventory inventory, Entity entity);
    public String getDisplayName();

}
