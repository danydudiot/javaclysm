package modele.entity.movable.character.npc.state.prey;

import modele.entity.movable.character.npc.prey.Prey;

/**
 * Classe représentant l'état affamé d'un écureuil (Squirrel).
 */
public class SquirrelHungryState extends HungryState {
    /**
     * Constructeur de la classe SquirrelHungryState.
     *
     * @param npc Le personnage proie associé à cet état.
     */
    public SquirrelHungryState(Prey npc) {
        super(npc);
    }

    /**
     * Gère le déplacement de l'écureuil.
     * L'écureuil se déplace s'il y a de la nourriture, un danger ou une position par défaut.
     */
    @Override
    public void deplacement() {
        final boolean move = getFood() || getDanger(true) || getDefault(null) != null;
    }

    /**
     * Met à jour l'état de l'écureuil.
     * Si le compteur de faim est supérieur à 0, l'état passe à non-affamé.
     */
    public void updateState() {
        if (prey.getHungryCount() > 0) {
            prey.setCurrentState(new SquirrelNotHungryState(prey));
        }
    }
}