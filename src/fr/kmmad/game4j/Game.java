package fr.kmmad.game4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.kmmad.game4j.Cell.Type;

public class Game implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int bnsRate;
	private int obsRate;
	private Date date;
	private Map2D map;
	private Player player;
	private List<Direction> path;
	
	public Game(int bnsRate, int obsRate) {
		this.obsRate = obsRate;
		this.bnsRate = bnsRate;
		date = new Date();
		map = new Map2D(bnsRate, obsRate);
		player = new Player(map.getCell(0, 0), 10);
		path = new ArrayList<>();
	}
	
	public static Game load(File inputFile) {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(inputFile));
			Game game = (Game) ois.readObject();
			ois.close();
			return game;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void display() {
		System.out.println("Energie : "+player.getEnergy());
		System.out.println("Annulations : "+player.getAvailableCancelAmount());
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (player.getCell() == map.getCell(i, j))
					System.out.print("* ");
				else switch (map.getCell(i, j).getType()) {
				case BONUS:
					System.out.print("+ ");
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
		if (path.size() == 0)
			return false;
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
	
	public boolean save(File outputFile) {
		try {
			if (!outputFile.exists())
				outputFile.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile));
			oos.writeObject(this);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
