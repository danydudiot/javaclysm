package modele.entity.movable.character.npc.prey;

import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.stationary.food.Acorn;

public class Squirrel extends Prey {
    public Squirrel(int x, int y) {
        super(x, y, 5);
        this.representation = "E";
        this.displayName = "Écureuil";
        this.foodPreference = Acorn.class;

    }
}
