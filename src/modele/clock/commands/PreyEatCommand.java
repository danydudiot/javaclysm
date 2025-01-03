package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;
import modele.entity.movable.character.npc.state.prey.FriendInInventoryState;
import modele.entity.stationary.food.Food;

public class PreyEatCommand implements Command {

	private Food food;
	private State old_state;
	private int old_friendlyLevel;
	private int old_hungryCount;
	private int old_x;
	private int old_y;
	private boolean isPlayerNearby;
	private Prey prey;
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
		if (!(prey.getCurrentState() instanceof FriendInInventoryState)){
			Board.getInstance().moveTo(prey, food.getX(), food.getY());
		} else {
			prey.setPosition(food.getX(), food.getY());
		}
		prey.setHasMoved(true);
	}

	@Override
	public void undoCommand() {
		prey.setFriendLevel(old_friendlyLevel);
		prey.setHungryCount(old_hungryCount);
		if (prey.getCurrentState() instanceof FriendInInventoryState) {
			Board.getInstance().setEntityOnCase(food.getX(), food.getY(), prey);
		}
		Board.getInstance().moveTo(prey, old_x, old_y);
		prey.setCurrentState(old_state);
		Board.getInstance().setEntityOnCase(food.getX(), food.getY(), food);
	}
}
