package modele.clock.commands;

public interface Command {
	public void doCommand();
	public void undoCommand();
}
