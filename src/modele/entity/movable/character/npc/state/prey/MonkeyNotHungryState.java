package modele.entity.movable.character.npc.state.prey;

import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Monkey;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.low.Rock;

/**
 * Classe représentant l'état non-affamé d'un singe (Monkey).
 */
public class MonkeyNotHungryState extends NotHungryState {
    /**
     * Constructeur de la classe MonkeyNotHungryState.
     *
     * @param prey Le personnage proie associé à cet état.
     */
    public MonkeyNotHungryState(Prey prey) {
        super(prey);
    }

    /**
     * Met à jour l'état du singe.
     * Si le compteur de faim est inférieur ou égal à 0, l'état passe à affamé.
     * Le singe essaie également de crier.
     */
    @Override
    public void updateState() {
        if (prey.getHungryCount() <= 0) {
            prey.setCurrentState(new MonkeyHungryState(prey));
        }
        ((Monkey) prey).tryYelling();
    }

    /**
     * Gère le déplacement du singe.
     * Le singe se déplace s'il y a un danger ou une position par défaut.
     */
    @Override
    public void deplacement() {
        final boolean move = getDanger(false) || getDefault(null) != null;
    }

    /**
     * Vérifie si le singe peut se déplacer sur le terrain spécifié.
     *
     * @param terrain Le terrain à vérifier.
     * @return true si le terrain est valide pour le déplacement, sinon false.
     */
    @Override
    public boolean canMove(Terrain terrain) {
        return super.canMove(terrain) || (terrain instanceof Rock && terrain.getEntityOnCase() instanceof Scorpio && ((Scorpio) terrain.getEntityOnCase()).canAttack());
    }
}