package modele.entity.stationary;

import modele.Board;
import modele.Colors;
import modele.InventoryItem;
import modele.clock.Clock;
import modele.interaction.GrabTimeStone;
import modele.interaction.Interactible;
import modele.interaction.Interaction;

import java.awt.*;

public class TimeStone extends StaticEntity implements Interactible, InventoryItem {

	private Interaction[] interactions;
	private int turns;
	private boolean isActive;


	public TimeStone(int x, int y, int turns) {
		super(x, y);
		this.turns = turns;
		this.interactions = new Interaction[1];
		interactions[0] = new GrabTimeStone();
		setActive(true);
	}

	public void setActive(boolean active) {
		isActive = active;
		if (isActive) {
			this.displayName = "Pierre Temporelle";
		} else {
			this.displayName = "Pierre Inutile";
		}
	}

	public void timeTravel() {
		if (isActive) {
			int nbTours = Math.min(Clock.getInstance().getNbTour(), turns);
			for (int i = 0; i < nbTours; ++i) {
				Clock.getInstance().undoLastTurn();
			}
			StringBuilder output = new StringBuilder();
			if (nbTours == 2) {
				output.append(Colors.ANSI_CYAN);
			} else {
				output.append(Colors.ANSI_BLUE);
			}
			output.append("Voyage dans le temps de ")
					.append(nbTours).append(" tours.")
					.append(Colors.ANSI_RESET);
			Board.getInstance().logAction(output.toString());
		}
	}

	@Override
	public Interaction[] getInteractions() {
		return interactions;
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		if (!isActive) {
			output.append(Colors.ANSI_LIGHT_BLACK);
		} else if (turns == 2) {
			output.append(Colors.ANSI_CYAN);
		} else {
			output.append(Colors.ANSI_BLUE);
		}
		output.append(turns);
		output.append(Colors.ANSI_RESET);
		return output.toString();
	}
}
