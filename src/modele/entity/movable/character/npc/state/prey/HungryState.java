package modele.entity.movable.character.npc.state.prey;

import modele.Colors;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Terrain;

/**
 * Classe abstraite représentant l'état affamé d'un personnage proie.
 */
public abstract class HungryState extends PreyState {
    /**
     * Constructeur de la classe HungryState.
     * @param prey Le personnage proie associé à cet état.
     */
    public HungryState(Prey prey) {
        super(prey);
    }

    /**
     * Vérifie si le personnage peut se déplacer sur le terrain spécifié.
     * @param terrain Le terrain à vérifier.
     * @return true si le terrain contient de la nourriture ou est vide, sinon false.
     */
    @Override
    public boolean canMove(Terrain terrain) {
        return terrain != null && (terrain.getEntityOnCase() instanceof Food || terrain.isEmpty());
    }

    /**
     * Applique un modificateur de couleur à la représentation visuelle du personnage.
     * @return Une chaîne de caractères représentant le personnage avec un modificateur de couleur.
     */
    @Override
    public String applyColorModifier() {
        if (prey.isFriendly()) {
            return Colors.PURPLE + prey.getRepresentation() + Colors.RESET;
        } else {
            return Colors.WHITE + prey.getRepresentation() + Colors.RESET;
        }
    }
}