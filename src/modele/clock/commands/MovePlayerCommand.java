package modele.clock.commands;

import modele.Board;
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
		Board.getInstance().logAction("Joueur bouge vers " + switch (direction) {
			case 'z' -> "le haut";
			case 's' -> "le bas";
			case 'q' -> "la gauche";
			case 'd' -> "la droite";
			default -> "nulle part ???? ceci est une erreur ?";
		});
	}

	@Override
	public void undoCommand() {
		playerCharacter.move(playerCharacter.getInverseDirection(direction));
		playerCharacter.changeOrientation(old_orientation);
	}
}
