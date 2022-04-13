package fr.kmmad.game4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {
	
	private Date date;
	Map2D map;
	Player player;
	List<Direction> path;
	
	public Game() {
		date = new Date();
		map = new Map2D();
		player = new Player(map.getCell(0, 0), 10);
		path = new ArrayList<>();
	}
	
	public void start() {
		
	}
	
	public void stop() {
		
	}
	
	public void display() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (player.getCell() == map.getCell(i, j))
					System.out.print("* ");
				else switch (map.getCell(i, j).getType()) {
				case BONUS:
					System.out.print("N ");
					break;
				case EMPTY:
					System.out.print("_ ");
					break;
				case OBSTACLE:
					System.out.print("X ");
					break;
				}
			}
			System.out.println("");
		}
	}
	
	public void move(Direction direction) {
		player.move(direction);
		path.add(direction);
	}
	
	public Date getDate() {
		return date;
	}
	
}
