package fr.kmmad.game4j;

public class Neighbor {

	private Cell cell;
	private int dist;
	
	public Neighbor(Cell cell, int dist) {
		this.cell = cell;
		this.dist = dist;
	}
	
	public int getDist() {
		return this.dist;
	}
	
	public Cell getCell() {
		return this.cell;
	}
}
