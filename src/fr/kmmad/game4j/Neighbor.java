package fr.kmmad.game4j;

import java.io.Serializable;

public class Neighbor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
