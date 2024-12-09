package modele.boardFactory;

import modele.Board;
import modele.clock.Clock;
import modele.entity.movable.character.PlayerCharacter;
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
			clusters[i][0] = (int) (Math.random() * (height -2) +1);
			clusters[i][1] = (int) (Math.random() * (width -2) +1);
		}

		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				// Create a wall of tree around the map
				if (y == 0 || x == 0 || y == height-1 || x == width-1) {
					if (Math.random() <= .95) {
						board[y][x] = new Tree(x,y);
 					} else {
						board[y][x] = new Bush(x,y);
					}
				} else {
					int closestCluster = 0;
					int closestDistance = distance(y,x,clusters[0][0],clusters[0][1]);
					for (int i = 1; i < clusterAmount; ++i) {
						int distance = distance(y,x,clusters[i][0],clusters[i][1]);
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
								board[y][x] = new Empty(x,y);
							} else {
								// Food Cluster
								board[y][x] = new Empty(x,y);
								if (Math.random() <= .65) {
									board[y][x].setEntityOnCase(new Mushroom(x,y));
								} else {
									board[y][x].setEntityOnCase(new Acorn(x,y));
								}
							}
						} else {
							// Tree Cluster
							if (Math.random() <= .65) {
								board[y][x] = new Tree(x,y);
							} else {
								board[y][x] = new Bush(x,y);
							}
						}
					} else {
						// Generating an empty space
						board[y][x] = new Empty(x,y);
						if (Math.random() <= .03) {
							Squirrel npc = new Squirrel(x,y);
							clock.attacher(npc);
							board[y][x].setEntityOnCase(npc);

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
