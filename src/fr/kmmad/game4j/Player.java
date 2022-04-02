package fr.kmmad.game4j;

/**
 * Cette classe représente un personnage : son énergie, sa position, et sa possibilité de revenir en arrière
 * @author Kévin
 * @see Game
 */
public class Player {
	
	public static final int maxCancelAmount = 10;
	private int initialEnergy, lostEnergy = 0, earnedEnergy = 0;
	private int cancelAmount = 0;
	private Cell cell;
	
	public Player(Cell startCell, int initialEnergy) {
		cell = startCell;
		this.initialEnergy = initialEnergy;
	}
	
	/**
	 * Déplace le personnage sur une case adjacente
	 * @param direction Direction souhaitée
	 * @return vrai si l'action à pu être effectuée
	 * @author Kévin
	 * @see Player
	 */
	public boolean move(Direction direction) {
		Cell nextCell = cell.getNeighbor(direction);
		if (nextCell == null)
			return false;
		lostEnergy += nextCell.getEnergy();
		cell = nextCell;
	}
	
	/**
	 * Permet de savoir si on peut annuler un mouvement
	 * @author Kévin
	 * @see Player
	 * @return vrai si un mouvement peut être annulé
	 */
	public boolean canCancel() {
		return cancelAmount < maxCancelAmount;
	}
	
}
