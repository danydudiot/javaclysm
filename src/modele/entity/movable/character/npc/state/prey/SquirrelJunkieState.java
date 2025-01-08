package modele.entity.movable.character.npc.state.prey;

import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;


/**
 * Classe représentant l'état junkie d'un écureuil (Squirrel).
 */
public class SquirrelJunkieState extends JunkieState {
    /**
     * Constructeur de la classe SquirrelJunkieState.
     *
     * @param prey Le personnage proie associé à cet état.
     */
    public SquirrelJunkieState(Prey prey) {
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
     * Utilise la position par défaut pour le déplacement.
     */
    @Override
    public void deplacement() {
        Terrain move = getDefault(null);
        getDefault(move);
    }
}