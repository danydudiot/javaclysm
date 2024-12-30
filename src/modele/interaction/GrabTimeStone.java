package modele.interaction;

import modele.Board;
import modele.Inventory;
import modele.InventoryItem;
import modele.entity.Entity;
import modele.entity.movable.character.Character;
import modele.entity.stationary.TimeStone;

public class GrabTimeStone extends Grab {

	@Override
	public void interact(Inventory inventory, Entity entity, Character author){
		TimeStone stone = (TimeStone) entity;
		Board board = Board.getInstance();
		stone.timeTravel();
		stone.setActive(false);
		try	{
			inventory.add((InventoryItem) stone);
			board.clearCase(stone.getX(), stone.getY());
		} catch (Exception e) {
			board.logError(e.getMessage());
		}
	}

}
