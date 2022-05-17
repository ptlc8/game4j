package fr.kmmad.game4j;

/**
 * Représente les 4 directions d'adjacence des cellules
 * @author Kévin
 * @see //Cell
 * @see //Map
 */
public enum Direction {
	NORTH, SOUTH, EAST, WEST;
	
	Direction getOpposite() {
		if (this == SOUTH)
			return NORTH;
		if (this == NORTH)
			return SOUTH;
		if (this == EAST)
			return WEST;
		if (this == WEST)
			return EAST;
		return null;
	}
}
