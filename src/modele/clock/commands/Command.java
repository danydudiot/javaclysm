package modele.clock.commands;

public interface Command {
	void doCommand();
	void undoCommand();
}
