package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.movable.character.npc.state.State;

public class PredatorFatalAttackCommand implements Command{

	private Predator predator;
	private State old_predatorState;
	private Prey prey;
	private State old_preyState;
	private int old_preyX;
	private int old_preyY;
	private int canAttack;

	public PredatorFatalAttackCommand(Predator predator, Prey prey) {
		this.predator 			= predator;
		this.old_predatorState 	= predator.getCurrentState();

		this.prey 				= prey;
		this.old_preyState 		= prey.getCurrentState();
		this.old_preyX 			= prey.getX();
		this.old_preyY 			= prey.getY();
		if (predator instanceof Scorpio scorpio){
			this.canAttack = scorpio.getCanAttack();
		}
	}

	@Override
	public void doCommand() {
		//Board.getInstance().clearCase(prey.getX(), prey.getY()); // pas besoin car la prey s'est retirer de la case
		prey.setCurrentState(new DeadState(prey));
		predator.afterHit(true);
		Board.getInstance().logAction(predator.getDisplayName() + " à tué " + prey.getDisplayName());
	}

	@Override
	public void undoCommand() {
		Board.getInstance().setEntityOnCase(old_preyX, old_preyY,prey);
		prey.setCurrentState(old_preyState);
		predator.setCurrentState(old_predatorState);
		if (predator instanceof Scorpio scorpio){
			scorpio.setCanAttack(canAttack);
		}
	}
}
