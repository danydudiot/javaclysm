package modele.boardFactory;

import modele.Board;
import modele.clock.Clock;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.Monkey;
import modele.entity.movable.character.npc.Squirrel;
import modele.entity.stationary.food.Acorn;
import modele.entity.stationary.food.Mushroom;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.Tree;
import modele.entity.stationary.terrain.low.Bush;

import java.util.Arrays;


public class BoardFactoryGeneratorForest extends BoardFactoryGenerator{
	Terrain[][] board;
	public BoardFactoryGeneratorForest(Clock clock, int height, int width) {
		super(clock, height, width);
	}

	public Board generateBoard() {
		board = new Terrain[height][width];
		int clusterAmount = (height*width) / 400;

		int[][] clusters = new int[clusterAmount][2];
		for (int i = 0; i < clusterAmount; ++i) {
			clusters[i][0] = (int) (Math.random() * height);
			clusters[i][1] = (int) (Math.random() * width);
		}

		for (int x = 0; x < height; ++x) {
			for (int y = 0; y < width; ++y) {
				// Create a wall of tree around the map
				if (x == 0 || y == 0 || x == height-1 || y == width-1) {
					if (Math.random() <= .95) {
						board[x][y] = new Tree(y,x);
 					} else {
						board[x][y] = new Bush(y,x);
					}
				} else {
					int closestCluster = 0;
					int closestDistance = distance(x,y,clusters[0][0],clusters[0][1]);
					for (int i = 1; i < clusterAmount; ++i) {
						int distance = distance(x,y,clusters[i][0],clusters[i][1]);
						if (distance < closestDistance) {
							closestDistance = distance;
							closestCluster = i;
						}
					}
					closestDistance = 15-closestDistance;
					if (Math.random()*20 <= closestDistance) {
						// Generating something
						if (closestCluster %2 == 0) {
							if (closestCluster == 0) {
								// Spawn Cluster
								board[x][y] = new Empty(y,x);
							} else {
								// Food Cluster
								board[x][y] = new Empty(y,x);
								if (Math.random() <= .65) {
									board[x][y].setEntityOnCase(new Mushroom(y,x));
								} else {
									board[x][y].setEntityOnCase(new Acorn(y,x));
								}
							}
						} else {
							// Tree Cluster
							if (Math.random() <= .65) {
								board[x][y] = new Tree(y,x);
							} else {
								board[x][y] = new Bush(y,x);
							}
						}
					} else {
						// Generating an empty space
						board[x][y] = new Empty(y,x);
						if (Math.random() <= .03) {
							Squirrel npc = new Squirrel(y,x);
							clock.attacher(npc);
							board[x][y].setEntityOnCase(npc);

						}
					}
				}
			}
		}

		PlayerCharacter player = new PlayerCharacter(clusters[0][1],clusters[0][0]);
		board[clusters[0][0]][clusters[0][1]].setEntityOnCase(player);
		return new Board(clock, 'F', height, width, board, player);
	}
}
