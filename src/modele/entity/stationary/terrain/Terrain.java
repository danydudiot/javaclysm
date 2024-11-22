package modele.entity.stationary.terrain;

import modele.entity.Entity;
import modele.entity.stationary.StaticEntity;

public abstract class Terrain extends StaticEntity {
    protected Entity entityOnCase;

    public Terrain(int x, int y) {
        super(x,y);
        this.entityOnCase = null;
    }

    public void setEntityOnCase(Entity entity){
        this.entityOnCase = entity;
    }

    @Override
    public String toString() {
        if (entityOnCase == null) {
            return representation;
        } else {
            return entityOnCase.toString();
        }
    }
}
