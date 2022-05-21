package fr.kmmad.game4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import fr.kmmad.game4j.Cell.Type;

/**
 * Cette classe représente un partie : le joueur, la carte, le chemin emprunté, ses pourcentages d'obtables et de bonus et sa date
 * @author Kévin
 * @see Game4J
 * @see Game#Game(int, int)
 * @see Game#load(String)
 */
public class Game implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Map2D map;
	private Player player;
	private List<Cell> path;
	private int bnsRate;
	private int obsRate;
	private Date date;
	
	/**
	 * Crée une partie
	 * @author Kévin
	 * @see Game#load(String)
	 * @param bnsRate poucentage de bonus sur la carte
	 * @param obsRate poucentage d'obstacles sur la carte
	 */
	public Game(int size, int bnsRate, int obsRate) {
		this.obsRate = obsRate;
		this.bnsRate = bnsRate;
		date = new Date();
		map = new Map2D(size, bnsRate, obsRate);
		player = new Player(map.getCell(0, 0), 10);
		path = new ArrayList<>();
		path.add(map.getCell(0, 0));
		new ArrayList<>();
	}
	
	/**
	 * Charge une sauvegarde de partie en décodant base64 puis en désérialisant si possible
	 * @author Kévin
	 * @see Game#save()
	 * @see Game#Game(int, int)
	 * @param save la sauvegarde sous forme de chaîne de caractères
	 * @return la partie chargée ou null si une erreur a eu lieu
	 */
	public static Game loadSave(String save) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(save)));
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
	 * les _ sont des cases vides,
	 * le * est l'emplacement du joueur
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
		{
			cell.setNextType(Type.EMPTY);
			player.setNumberBonus(player.getNumberBonus()+1);
		}
		else cell.setNextType(cell.getType());
		player.setCell(cell);
		if(path.contains(cell))
		{
			System.out.println("Attention, boucle détécté !");
		}
		path.add(cell);
		return true;
	}

	
	/**
	 * Annule le dernier mouvement effectué possible et remet l'état de la carte précédent,
	 * pour cela il faut qu'il y ait un mouvement a annulé et il doit y avoir assez d'annulation
	 * @author Kévin
	 * @return true si un mouvement a pu être annulé, sinon false
	 */
	public boolean cancelMove() {
		if (path.size() <= 1)
			return false;
		if (!player.canCancelMove())
			return false;
		player.increaseCancelAmount();
		Cell cell = path.remove(path.size()-1);
		if (cell.getType().equals(Type.BONUS)) {
			player.setNumberBonus(player.getNumberBonus()-1);
		}
		player.setCell(path.get(path.size()-1));
		cell.resetPreviousType();
		int energy = cell.getEnergy();
		if (energy > 0)
			player.unearnEnergy(energy);
		else
			player.unloseEnergy(-energy);
		return true;
	}

	/**
	 * Crée une sauvegarde de la partie en sérialisant si possible puis en encodant base64
	 * @author Kévin
	 * @see Game#load(String)
	 * @return la sauvegarde sous forme de chaîne de caractère, sinon null
	 */
	public String createSave() {
		try {
			ByteArrayOutputStream bytesStream = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bytesStream);
			oos.writeObject(this);
			oos.close();
			return Base64.getEncoder().encodeToString(bytesStream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @author Mattéo
	 * @return une liste contenant la toutes les cell de la boucle détcté
	 */
	public List<Cell> getLoop() {
		List<Cell> loop = new ArrayList<Cell>();
		int loopstart = 0;
		int loopend = 0;
		int lastCell = path.size()-1;
		loop:for(int i = lastCell; i >= 0; i--)
		{
			for (int j = i-1; j >= 0; j--)
			{
				if (path.get(i).getId()==path.get(j).getId()) {
					loopstart = j;
					loopend = i;
					break loop;
				}
			}
		}
		if (loopend == loopstart)
			return null;
		for(int j = loopstart; j <= loopend; j++)
		{
			loop.add(path.get(j));
		}
		return loop;
	}
	
	/**
	 * @return carte du jeu
	 */
	public Map2D getMap() {
		return map;
	}
	
	/**
	 * @return date de création de la partie
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * @return pourcentage d'obstacles utilisé pour créer la carte
	 */
	public int getObstacleRate() {
		return obsRate;
	}
	
	/**
	 * @return pourcentage de bonus utilisé pour créer la carte
	 */
	public int getBonusRate() {
		return bnsRate;
	}

	/**
	 * @return true si la partie est terminée sinon false
	 */
	public boolean isFinished() {
		return isVictory() || isDefeat();
	}
	
	/**
	 * @return true si la partie est gangée sinon false
	 */
	public boolean isVictory() {
		return player.getCell() == map.getCell(map.getSize()-1, map.getSize()-1);
	}
	
	/**
	 * @return true si la partie est perdue sinon false
	 */
	public boolean isDefeat() {
		return player.getEnergy() == 0 && player.getAvailableCancelAmount() == 0;
	}
	
	/**
	 * @return le joueur
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * @return case de départ
	 */
	public Cell getStartCell() {
		return map.getCell(0, 0);
	}
	
	/**
	 * @return case d'arrivée
	 */
	public Cell getEndCell() {
		return map.getCell(map.getSize()-1, map.getSize()-1);
	}
	
	/**
	 * @return le chemin parcouru par le joueur
	 */
	public List<Cell> getPath() {
		return Collections.unmodifiableList(path);
	}

}
