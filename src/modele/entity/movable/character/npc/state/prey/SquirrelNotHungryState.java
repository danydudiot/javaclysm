package modele.entity.movable.character.npc.state.prey;

import modele.entity.movable.character.npc.prey.Prey;

/**
 * Classe représentant l'état non-affamé d'un écureuil (Squirrel).
 */
public class SquirrelNotHungryState extends NotHungryState {
    /**
     * Constructeur de la classe SquirrelNotHungryState.
     *
     * @param prey Le personnage proie associé à cet état.
     */
    public SquirrelNotHungryState(Prey prey) {
        super(prey);
    }

    /**
     * Met à jour l'état de l'écureuil.
     * Si le compteur de faim est inférieur ou égal à 0, l'état passe à affamé.
     */
    @Override
    public void updateState() {
        if (prey.getHungryCount() <= 0) {
            prey.setCurrentState(new SquirrelHungryState(prey));
        }
    }

    /**
     * Gère le déplacement de l'écureuil.
     * L'écureuil se déplace s'il y a un danger ou une position par défaut.
     */
    @Override
    public void deplacement() {
        final boolean result = getDanger(true) || getDefault(null) != null;
    }
}