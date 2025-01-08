package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.stationary.terrain.Terrain;

/**
 * Annule la commande pour reprendre l'entité déposée.
 */
public class PlayerMoveCommand implements Command {
    /**
     * Le personnage joueur à déplacer.
     */
    private PlayerCharacter playerCharacter;
    /**
     * La direction du déplacement.
     */
    private final char direction;
    /**
     * L'ancienne orientation du joueur.
     */
    private final char old_orientation;
    /**
     * Indique si le joueur a pu se déplacer avec succès.
     */
    private boolean hasMove;

    /**
     * Constructeur de la classe PlayerMoveCommand.
     * Initialise les attributs de la commande.
     *
     * @param p         Le personnage joueur à déplacer.
     * @param direction La direction du déplacement.
     */
    public PlayerMoveCommand(PlayerCharacter p, char direction) {
        this.direction = direction;
        this.playerCharacter = p;
        this.old_orientation = p.getOrientation();
    }

    /**
     * Exécute la commande pour déplacer le joueur.
     */
    @Override
    public void doCommand() {
        hasMove = playerCharacter.canMove(direction);
        if (hasMove) {
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

    /**
     * Annule la commande pour remettre le joueur à sa position initiale.
     */
    @Override
    public void undoCommand() { // canMove check avant l'appel de la commande
        if (hasMove) {
            playerCharacter.move(playerCharacter.getInverseDirection(direction));
            playerCharacter.changeOrientation(old_orientation);
        }
    }
}
