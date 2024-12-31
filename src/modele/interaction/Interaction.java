package modele.interaction;

import modele.entity.Entity;

public interface Interaction {
    void interact(Entity entity);
    String getDisplayName();

}
