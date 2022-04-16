package fr.kmmad.game4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.kmmad.game4j.Cell.Type;

public class Game {
	
	private int bnsRate;
	private int obsRate;
	private Date date;
	Map2D map;
	Player player;
	List<Direction> path;
	
	public Game(int bnsRate, int obsRate) {
		this.obsRate = obsRate;
		this.bnsRate = bnsRate;
		date = new Date();
		map = new Map2D(bnsRate,obsRate);
		player = new Player(map.getCell(0, 0), 10);
		path = new ArrayList<>();
	}
	
	public void start() {
		
	}
	
	public void stop() {
		
	}
	
	public void display() {
		System.out.println("Energie : "+player.getEnergy());
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
	
	public boolean move(Direction direction) {
		if (!player.canMove())
			return false;
		Neighbor neighbor = player.getCell().getNeigh(direction);
		if (neighbor == null)
			return false;
		Cell cell = neighbor.getCell();
		if (cell.getType() == Type.OBSTACLE)
			return false;
		int energy = cell.getEnergy();
		if (energy > 0)
			player.earnEnergy(energy);
		else
			player.loseEnergy(-energy);
		if (cell.getType() == Type.BONUS)
			cell.setNextType(Type.EMPTY);
		else cell.setNextType(cell.getType());
		player.setCell(cell);
		path.add(direction);
		return true;
	}
	
	public boolean cancelMove() {
		if (!player.canCancelMove())
			return false;
		player.increaseCancelAmount();
		Direction direction = path.remove(path.size()-1);
		Cell cell = player.getCell();
		player.setCell(cell.getNeigh(direction.getOpposite()).getCell());
		cell.resetPreviousType();
		int energy = cell.getEnergy();
		if (energy > 0)
			player.unearnEnergy(energy);
		else
			player.unloseEnergy(-energy);
		return true;
	}
	
	public Date getDate() {
		return date;
	}
	
}
