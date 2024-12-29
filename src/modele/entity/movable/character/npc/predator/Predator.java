package modele.entity.movable.character.npc.predator;

import modele.Board;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.state.predator.RestState;

public abstract class Predator extends NonPlayerCharacter {
    public Predator(int x, int y) {
        super(x, y);
    }

    @Override
    public void hit(Character aggressor) {
        if (currentState instanceof RestState){
            Board.getInstance().getAt(x,y).clearEntityOnCase();
        }
    }


    public abstract void afterHit();
}
