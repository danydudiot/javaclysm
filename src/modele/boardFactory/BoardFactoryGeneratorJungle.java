package modele.boardFactory;

import modele.clock.Clock;
import modele.entity.movable.character.npc.Monkey;
import modele.entity.stationary.food.Banana;
import modele.entity.stationary.food.Mushroom;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.PalmTree;
import modele.entity.stationary.terrain.low.Rock;


public class BoardFactoryGeneratorJungle extends BoardFactoryGenerator{
	Terrain[][] board;
	public BoardFactoryGeneratorJungle(Clock clock, int height, int width) {
		super(clock, height, width);
	}

	@Override
	public Terrain generateHighTerrain(int x, int y) {
		return new PalmTree(x,y);
	}

	@Override
	public Terrain generateLowTerrain(int x, int y) {
		return new Rock(x,y);
	}

	@Override
	public Terrain generateAnimal(int x, int y, Clock clock) {
		Terrain empty = generateEmpty(x,y);
		Monkey npc = new Monkey(x,y);
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
			empty.setEntityOnCase(new Banana(x,y));
		}
		return empty;
	}

	@Override
	public char getTheme() {
		return 'J';
	}


}
