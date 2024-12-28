package modele.boardFactory;

import modele.boardFactory.generator.GeneratorForest;
import modele.clock.Clock;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.stationary.food.Acorn;
import modele.entity.stationary.food.Mushroom;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.Tree;
import modele.entity.stationary.terrain.low.Bush;


public class BoardFactoryGeneratorForest extends BoardFactoryGenerator {
	Terrain[][] board;

	public BoardFactoryGeneratorForest(int height, int width) {
		super(height, width);
		this.generator = new GeneratorForest();
	}



	/*@Override
	public Terrain generateHighTerrain(int x, int y) {
		return new Tree(x,y);
	}

	@Override
	public Terrain generateLowTerrain(int x, int y) {
		return new Bush(x,y);
	}

	@Override
	public Terrain generateAnimal(int x, int y) {
		Terrain empty = generateEmpty(x,y);
		Squirrel npc = new Squirrel(x,y);
		Clock.getInstance().attacher(npc);
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
	}*/

	@Override
	public char getTheme() {
		return 'F';
	}
}

