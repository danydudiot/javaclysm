package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.stationary.terrain.Terrain;

public class PlayerMoveCommand implements Command{
	private PlayerCharacter playerCharacter;
	private final char direction;
	private final char old_orientation;
	private boolean hasMove;

	public PlayerMoveCommand(PlayerCharacter p, char direction) {
		this.direction 		 = direction;
		this.playerCharacter = p;
		this.old_orientation = p.getOrientation();
	}

	@Override
	public void doCommand() {
		hasMove = playerCharacter.canMove(direction);
		if (hasMove){
			playerCharacter.move(direction);
			Board.getInstance().logAction("Joueur bouge vers " + switch (direction) {
				case 'z' -> "le haut";
				case 's' -> "le bas";
				case 'q' -> "la gauche";
				case 'd' -> "la droite";
				default -> "nulle part ???? ceci est une erreur ?";
			});
		}
	}

	@Override
	public void undoCommand() { // canMove check avant l'appel de la commande
		if (hasMove){
			playerCharacter.move(playerCharacter.getInverseDirection(direction));
			playerCharacter.changeOrientation(old_orientation);
		}
	}
}
