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

/**
 * Cette classe représente un partie : le joueur, le plateau, le chemin emprunté, ses pourcentages d'obtables et de bonus et sa date
 * @author Kévin
 * @see Game4J
 * @see Game#Game(int, int)
 * @see Game#load(File)
 */
public class Game implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Map2D map;
	private Player player;
	private List<Direction> path;
	private int bnsRate;
	private int obsRate;
	private Date date;
	
	/**
	 * Crée une partie
	 * @author Kévin
	 * @see Game#load(File)
	 * @param bnsRate poucentage de bonus sur le plateau
	 * @param obsRate poucentage d'obstacles sur le plateau
	 */
	public Game(int bnsRate, int obsRate) {
		this.obsRate = obsRate;
		this.bnsRate = bnsRate;
		date = new Date();
		map = new Map2D(bnsRate, obsRate);
		player = new Player(map.getCell(0, 0), 10);
		path = new ArrayList<>();
	}
	
	/**
	 * Charge une partie depuis un fichier
	 * @author Kévin
	 * @see Game#save(File)
	 * @see Game#Game(int, int)
	 * @param inputFile fichier dans lequel est sérialisée la partie
	 * @return la partie chargée ou null si une erreur a eu lieu
	 */
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

	/**
	 * Affiche la partie sommairement dans la console :
	 * les X sont des obstacles,
	 * les + sont des bonus,
	 * les _ sont des cases vides
	 * @author Kévin
	 * @see Game
	 */
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

	/**
	 * Déplace le joueur dans une direction si possible,
	 * pour cela il doit y avoir une case dans la direction précisé qui ne doit pas être un obstable, assez d'énergie
	 * @author Kévin
	 * @param direction direction dans la quel le joueur doit être déplacé
	 * @return true si il a pu être déplacé, sinon false
	 */
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

	/**
	 * Annule le dernier mouvement effectué possible et remet l'état du plateau précédent,
	 * pour cela il faut qu'il y ait un mouvement a annulé et il doit y avoir assez d'annulation
	 * @author Kévin
	 * @return true si un mouvement a pu être annulé, sinon false
	 */
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

	/**
	 * Enregistre la partie dans un fichier si possible
	 * @author Kévin
	 * @see Game#load(File)
	 * @param outputFile fichier dans lequel enregistrer
	 * @return true si la partie à correctement été enregistrée, sinon false
	 */
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
	
	/**
	 * @return date de création de la partie
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * @return pourcentage d'obstacles utilisé pour créer le plateau
	 */
	public int getObstacleRate() {
		return obsRate;
	}
	
	/**
	 * @return pourcentage de bonus utilisé pour créer le plateau
	 */
	public int getBonusRate() {
		return bnsRate;
	}
	
}
