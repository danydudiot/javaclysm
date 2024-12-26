package modele.clock.commands;

import modele.entity.movable.character.PlayerCharacter;

public class MovePlayerCommand implements Command{
	PlayerCharacter playerCharacter;
	char direction;
	char old_orientation;

	public MovePlayerCommand(PlayerCharacter p, char direction) {
		this.direction 		 = direction;
		this.playerCharacter = p;
		this.old_orientation = p.getOrientation();
	}

	@Override
	public void doCommand() {
		playerCharacter.move(direction);
	}

	@Override
	public void undoCommand() {
		playerCharacter.move(playerCharacter.getInverseDirection(direction));
		playerCharacter.changeOrientation(old_orientation);
	}
}
