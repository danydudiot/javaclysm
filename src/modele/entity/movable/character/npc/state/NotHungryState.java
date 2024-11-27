package modele.entity.movable.character.npc.state;

import modele.entity.movable.character.npc.NonPlayerCharacter;

public class NotHungryState implements State {

    private NonPlayerCharacter npc;
    public NotHungryState(NonPlayerCharacter npc) {
        this.npc = npc;
        npc.setHungryCount(npc.hungryCountBase);
    }

    public void updateState(){
        npc.setHungryCount(npc.getHungryCount()-1);
        if (npc.getHungryCount() <= 0){
            npc.setCurentState(new HungryState(npc));
        }
    }




}
