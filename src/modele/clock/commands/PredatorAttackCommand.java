package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;

public class PredatorAttackCommand implements Command{

	private Predator predator;
	private State old_predatorState;
	private int old_predatorX;
	private int old_predatorY;
	private Prey prey;
	private State old_preyState;
	private int old_preyX;
	private int old_preyY;
	private boolean wasKilled;
	private int canAttack;
	private int hungryCount;

	public PredatorAttackCommand(Predator predator, Prey prey) {
		this.predator 			= predator;
		this.old_predatorState 	= predator.getCurrentState();
		this.old_predatorX 		= predator.getX();
		this.old_predatorY 		= predator.getY();
		this.prey 				= prey;
		this.hungryCount 		= prey.getHungryCount()+1;
		this.old_preyState 		= prey.getCurrentState();
		this.old_preyX 			= prey.getX();
		this.old_preyY 			= prey.getY();
		if (predator instanceof Scorpio scorpio){
			this.canAttack = scorpio.getCanAttack();
		}
	}

	@Override
	public void doCommand() {
		this.wasKilled = prey.hit(predator);
		if (wasKilled) {
			Board.getInstance().logAction(predator.getDisplayName() + " à tué " + prey.getDisplayName());
		}
		prey.setHasMoved(true);
	}

	@Override
	public void undoCommand() {
		predator.setCurrentState(old_predatorState);
		Board.getInstance().moveTo(predator, old_predatorX, old_predatorY);
		if (wasKilled) {
			Board.getInstance().setEntityOnCase(old_preyX, old_preyY, prey);
		}
		prey.setCurrentState(old_preyState);
		prey.setHungryCount(hungryCount);
		if (predator instanceof Scorpio scorpio){
			scorpio.setCanAttack(canAttack);
		}
	}
}
