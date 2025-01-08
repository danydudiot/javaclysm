package modele.entity.movable.character.npc.state.prey;

import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Monkey;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.low.Rock;

/**
 * Classe représentant l'état affamé d'un singe (Monkey).
 */
public class MonkeyHungryState extends HungryState {
    /**
     * Constructeur de la classe MonkeyHungryState.
     *
     * @param prey Le personnage proie associé à cet état.
     */
    public MonkeyHungryState(Prey prey) {
        super(prey);
    }

    /**
     * Gère le déplacement du singe.
     * Le singe se déplace s'il y a un danger, de la nourriture ou une position par défaut.
     */
    @Override
    public void deplacement() {
        final boolean move = getDanger(false) || getFood() || getDefault(null) != null;
    }

    /**
     * Met à jour l'état du singe.
     * Si le compteur de faim est supérieur à 0, l'état passe à non-affamé.
     * Le singe essaie également de crier.
     */
    public void updateState() {
        if (prey.getHungryCount() > 0) {
            prey.setCurrentState(new MonkeyNotHungryState(prey));
        }
        ((Monkey) prey).tryYelling();
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