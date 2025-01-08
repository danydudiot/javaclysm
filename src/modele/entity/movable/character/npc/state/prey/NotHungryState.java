package modele.entity.movable.character.npc.state.prey;

import modele.Colors;
import modele.entity.movable.character.npc.prey.Prey;

/**
 * Classe abstraite représentant l'état non-affamé d'un personnage proie.
 */
public abstract class NotHungryState extends PreyState {
    /**
     * Constructeur de la classe NotHungryState.
     *
     * @param prey Le personnage proie associé à cet état.
     */
    public NotHungryState(Prey prey) {
        super(prey);
        if (prey.getHungryCount() <= 0) {
            prey.setHungryCount(prey.hungryCountBase);
        }
    }

    /**
     * Applique un modificateur de couleur à la représentation visuelle du personnage.
     *
     * @return Une chaîne de caractères représentant le personnage avec un modificateur de couleur.
     */
    @Override
    public String applyColorModifier() {
        if (prey.isFriendly()) {
            return Colors.LIGHT_PURPLE + prey.getRepresentation() + Colors.RESET;
        } else {
            // light white
            return Colors.LIGHT_WHITE + prey.getRepresentation() + Colors.RESET;
        }
    }
}
