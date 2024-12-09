package modele.entity.movable.character.npc;

import modele.entity.stationary.food.Acorn;

public class Squirrel extends NonPlayerCharacter {
    public Squirrel(int x, int y) {
        super(x, y, 5);
        this.representation = "E";
        this.displayName = "Écureuil";
        this.foodPreference = Acorn.class;

    }
}
