package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.PlayerCharacter;

public class PlayerMoveCommand implements Command{
	PlayerCharacter playerCharacter;
	final char direction;
	final char old_orientation;

	public PlayerMoveCommand(PlayerCharacter p, char direction) {
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
