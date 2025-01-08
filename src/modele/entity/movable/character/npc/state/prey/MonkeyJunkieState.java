package modele.entity.movable.character.npc.state.prey;

import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.low.Rock;

/**
 * Classe représentant l'état junkie d'un singe (Monkey).
 */
public class MonkeyJunkieState extends JunkieState {
    /**
     * Constructeur de la classe MonkeyJunkieState.
     *
     * @param prey Le personnage proie associé à cet état.
     */
    public MonkeyJunkieState(Prey prey) {
        super(prey);
    }

    /**
     * Met à jour l'état du singe.
     * Si le compteur de faim est inférieur ou égal à 0, l'état passe à affamé.
     */
    @Override
    public void updateState() {
        if (prey.getHungryCount() <= 0) {
            prey.setCurrentState(new MonkeyHungryState(prey));
        }
    }

    /**
     * Gère le déplacement du singe.
     * Utilise la position par défaut pour le déplacement.
     */
    @Override
    public void deplacement() {
        getDefault(null);
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