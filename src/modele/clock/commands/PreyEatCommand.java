package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.food.Food;

public class PreyEatCommand implements Command {

	Food food;
	State old_state;
	int old_friendlyLevel;
	int old_hungryCount;
	int old_x;
	int old_y;
	boolean isPlayerNearby;
	Prey prey;
	public PreyEatCommand(Prey prey, Food food) {
		this.prey 				= prey;
		this.old_state 			= prey.getCurrentState();
		this.food 				= food;
		this.old_x 				= prey.getX();
		this.old_y				= prey.getY();
		this.isPlayerNearby 	= food.isPlayerNearby();
		this.old_hungryCount 	= prey.getHungryCount();
		this.old_friendlyLevel 	= prey.getFriendLevel();
	}

	@Override
	public void doCommand() {
		prey.eat(isPlayerNearby, food);
		Board.getInstance().clearCase(food.getX(), food.getY());
		Board.getInstance().moveTo(prey, food.getX(), food.getY());
		prey.setHasMoved(true);
	}

	@Override
	public void undoCommand() {
		prey.setFriendLevel(old_friendlyLevel);
		prey.setHungryCount(old_hungryCount);
		prey.setCurrentState(old_state);
		Board.getInstance().moveTo(prey, old_x, old_y);
		Board.getInstance().getAt(food.getX(), food.getY()).setEntityOnCase(food);
	}
}
