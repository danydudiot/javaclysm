package modele.entity.movable.character.npc.prey;

import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.stationary.food.Banana;

public class Monkey extends Prey {

    public Monkey(int x, int y) {
        super(x, y, 3);
        this.representation = "S";
        this.displayName = "Singe";
        this.foodPreference = Banana.class;
    }

    @Override
    public boolean isFriendly () {
        return friendLevel >= 2;
    }
}
