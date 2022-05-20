package fr.kmmad.game4j;

import java.io.Serializable;

/**
 * Cette classe représente un personnage : son énergie, sa position, et sa possibilité de revenir en arrière
 * @author Kévin
 * @see Game
 * @see Player#Player(Cell, int)
 */
public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int maxCancelAmount = 6;

	private int initialEnergy, lostEnergy = 0, earnedEnergy = 0;
	private int cancelAmount = 0;
	private Cell cell;
	private int numberBonus = 0;

	/**
	 * Crée un joueur
	 * @author Kévin
	 * @param startCell cellule où le joueur est positionné
	 * @param initialEnergy énergie initiale du joueur
	 */
	public Player(Cell startCell, int initialEnergy) {
		cell = startCell;
		this.initialEnergy = initialEnergy;
		this.numberBonus  = 0;
	}
	public int getNumberBonus() {
		return this.numberBonus;
	}
	public void setNumberBonus(int numberBonus) {
		this.numberBonus = numberBonus;
	}

	/**
	 * Permet de savoir si on peut se déplacer
	 * @author Kévin
	 * @return vrai si un mouvement peut être annulé
	 */
	public boolean canMove() {
		return getEnergy() > 0;
	}

	/**
	 * Permet de savoir si on peut annuler un mouvement
	 * @author Kévin
	 * @return vrai si un mouvement peut être annulé
	 */
	public boolean canCancelMove() {
		return cancelAmount < maxCancelAmount;
	}

	/**
	 * @return cellule où positionné le personnage
	 */
	public Cell getCell() {
		return cell;
	}

	/**
	 * Modifie la cellule où positionné le personnage
	 */
	public void setCell(Cell cell) {
		this.cell = cell;
	}

	/**
	 * @return nombre d'annulation de mouvement déjà utilisées
	 */
	public int getCancelAmount() {
		return cancelAmount;
	}

	/**
	 * @return nombre d'annulation de mouvement restantes
	 */
	public int getAvailableCancelAmount() {
		return maxCancelAmount - cancelAmount;
	}

	/**
	 * augmente le nombre d'annulation de 1
	 */
	public void increaseCancelAmount() {
		cancelAmount++;
	}

	/**
	 * @return nombre d'énergie intiale
	 */
	public int getInitialEnergy() {
		return initialEnergy;
	}

	/**
	 * @return nombre d'énergie gagnée
	 */
	public int getEarnedEnergy() {
		return earnedEnergy;
	}

	/**
	 * @return nombre d'énergie dépensée
	 */
	public int getLostEnergy() {
		return lostEnergy;
	}

	/**
	 * @return nombre d'énergie actuelle
	 */
	public int getEnergy() {
		return initialEnergy+earnedEnergy-lostEnergy;
	}

	/**
	 * ajoute de l'énergie gagnée
	 */
	public void earnEnergy(int energy) {
		earnedEnergy += energy;
	}

	/**
	 * supprime de l'énergie gagnée
	 */
	public void unearnEnergy(int energy) {
		earnedEnergy -= energy;
	}

	/**
	 * ajoute de l'énergie perdue
	 */
	public void loseEnergy(int energy) {
		lostEnergy += energy;
	}

	/**
	 * supprime de l'énergie perdue
	 */
	public void unloseEnergy(int energy) {
		lostEnergy -= energy;
	}

}