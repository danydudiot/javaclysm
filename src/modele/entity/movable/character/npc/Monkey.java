package modele.entity.movable.character.npc;

import modele.entity.stationary.food.Banana;

public class Monkey extends NonPlayerCharacter{


    public Monkey(int x, int y) {
        super(x, y, 3);
        this.representation = "M";
        this.foodPreference = Banana.class;
    }

    @Override
    public boolean isFriendly () {
        return friendLevel <= 2;
    }
}
