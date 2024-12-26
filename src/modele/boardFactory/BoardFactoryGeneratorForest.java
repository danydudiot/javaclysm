package modele.boardFactory;

import modele.clock.Clock;
import modele.entity.movable.character.npc.Squirrel;
import modele.entity.stationary.food.Acorn;
import modele.entity.stationary.food.Mushroom;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.Tree;
import modele.entity.stationary.terrain.low.Bush;


public class BoardFactoryGeneratorForest extends BoardFactoryGenerator {
	Terrain[][] board;

	public BoardFactoryGeneratorForest(Clock clock, int height, int width) {
		super(clock, height, width);
	}

	@Override
	public Terrain generateHighTerrain(int x, int y) {
		return new Tree(x,y);
	}

	@Override
	public Terrain generateLowTerrain(int x, int y) {
		return new Bush(x,y);
	}

	@Override
	public Terrain generateAnimal(int x, int y, Clock clock) {
		Terrain empty = generateEmpty(x,y);
		Squirrel npc = new Squirrel(x,y);
		clock.attacher(npc);
		empty.setEntityOnCase(npc);
		return empty;
	}

	@Override
	public Terrain generateFood(int x, int y, boolean isMushroom) {
		Terrain empty = generateEmpty(x,y);
		if (isMushroom) {
			empty.setEntityOnCase(new Mushroom(x,y));
		} else {
			empty.setEntityOnCase(new Acorn(x,y));
		}
		return empty;
	}

	@Override
	public char getTheme() {
		return 'F';
	}
}

