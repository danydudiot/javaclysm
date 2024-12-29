package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.food.Food;

public class EatPreyCommand implements Command {

	Food food;
	int old_friendlyLevel;
	int old_hungryCount;
	int old_x;
	int old_y;
	boolean isPlayerNearby;
	Prey prey;
	public EatPreyCommand(Prey prey, Food food, boolean isPlayerNearby) {
		this.prey 				= prey;
		this.food 				= food;
		this.old_x 				= food.getPosition()[0];
		this.old_y				= food.getPosition()[1];
		this.isPlayerNearby 	= isPlayerNearby;
		this.old_hungryCount 	= prey.getHungryCount();
		this.old_friendlyLevel 	= prey.getFriendLevel();
	}

	@Override
	public void doCommand() {
		prey.eat(isPlayerNearby, food);
	}

	@Override
	public void undoCommand() {
		prey.setFriendLevel(old_friendlyLevel);
		prey.setHungryCount(old_hungryCount);
		Board.getInstance().getAt(old_x, old_y).setEntityOnCase(food);
	}
}
