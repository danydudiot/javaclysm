package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.PredatorAttackCommand;

import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.low.Bush;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant l'état de raid d'un hibou (Owl).
 */
public class OwlRaidState extends RaidState {
    /**
     * Constructeur de la classe OwlRaidState.
     *
     * @param predator Le prédateur associé à cet état.
     */
    public OwlRaidState(Predator predator) {
        super(predator);
    }

    /**
     * Met à jour l'état du prédateur.
     * Cette méthode est actuellement vide et ne fait rien.
     */
    @Override
    public void updateState() {

    }

    /**
     * Gère le déplacement du prédateur.
     * Si une proie est trouvée à proximité (hors des buissons), une commande d'attaque est ajoutée.
     * Sinon, utilise la méthode getDefault2Case pour le déplacement par défaut.
     */
    @Override
    public void deplacement() {
        List<Terrain> around = Board.getInstance().getNear(predator.getX(), predator.getY(), 3);
        List<Prey> preyList = new ArrayList<>();
        for (Terrain terrain : around) {
            if (terrain.getEntityOnCase() instanceof Prey && !(terrain instanceof Bush)) {
                preyList.add((Prey) terrain.getEntityOnCase());
            }
        }

        if (!preyList.isEmpty()) {
            Clock.getInstance().addCommandToTurn(new PredatorAttackCommand(predator, preyList.get(0)));
        } else {
            getDefault2Case();
        }

    }
}