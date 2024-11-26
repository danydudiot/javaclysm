package modele.entity.movable.character;

import modele.entity.movable.MovableEntity;

public abstract class Character extends MovableEntity {

    public Character(int x, int y) {
        super(x, y);
    }

    public void hit() {}
}
