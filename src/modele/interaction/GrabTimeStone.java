package modele.interaction;

import modele.Board;
import modele.Inventory;
import modele.InventoryItem;
import modele.entity.Entity;
import modele.entity.stationary.TimeStone;

public class GrabTimeStone extends Grab {

	@Override
	public void interact(Entity entity){
		TimeStone stone = (TimeStone) entity;
		Board board = Board.getInstance();
		stone.timeTravel();
		stone.setActive(false);
		try	{
			Inventory.getInstance().add((InventoryItem) stone);
			System.out.println("grab Timestone");
			board.clearCase(stone.getX(), stone.getY());
		} catch (Exception e) {
			board.logError(e.getMessage());
		}
	}

}
