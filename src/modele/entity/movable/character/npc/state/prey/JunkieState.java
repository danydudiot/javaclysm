package modele.entity.movable.character.npc.state.prey;

import modele.Colors;
import modele.entity.movable.character.npc.prey.Prey;

/**
 * Classe abstraite représentant l'état junkie d'un personnage proie.
 */
public abstract class JunkieState extends PreyState {
    /**
     * Constructeur de la classe JunkieState.
     *
     * @param prey Le personnage proie associé à cet état.
     */
    public JunkieState(Prey prey) {
        super(prey);
        prey.setHungryCount(prey.hungryCountBase);
    }

    /**
     * Applique un modificateur de couleur à la représentation visuelle du personnage.
     *
     * @return Une chaîne de caractères représentant le personnage avec un modificateur de couleur rouge.
     */
    @Override
    public String applyColorModifier() {
        return Colors.RED + prey.getRepresentation() + Colors.RESET;
    }
}