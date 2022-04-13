package fr.kmmad.game4j;

public class Cell {

	public enum Type{
		EMPTY,BONUS,OBSTACLE
	}
	
	
	private int y;
	private int x;
	private double id;
	protected int energy;
	private Type initCellType;
	private Neighbor nn;
	private Neighbor ne;
	private Neighbor nw;
	private Neighbor ns;
	private Type type;
	
	public Cell(int x, int y, double id,Type initCellType, int energy, Type type) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.initCellType = initCellType;
		this.energy = energy;
		this.type = type;
	}
	
	public Type getType() {
		return this.initCellType;
	}

	public int getCoordY() {
		return this.y;
	}

	public int getCoordX() {
		return this.x;
	}
	
	public Neighbor getNeighN() {
		return this.nn;
	}
	
	public Neighbor getNeighE() {
		return this.nn;
	}
	
	public Neighbor getNeighW() {
		return this.nn;
	}
	
	public Neighbor getNeighS() {
		return this.nn;
	}
	
	public int getEnergy() {
		if(type == Type.BONUS) {
			return 9;
		}else 
		if(type == Type.OBSTACLE) {
		return -10;
		}else
		return -1;
	} 
	
	public void setNeighN(Neighbor nn) {
		this.nn = nn;
	}
	
	public void setNeighE(Neighbor ne) {
		this.ne = ne;
	}
	
	public void setNeighW(Neighbor nw) {
		this.nw = nw;
	}

	public void setNeighS(Neighbor ns) {
		this.ns = ns;
	}
	
	public int getDist(Cell c1) {
		if(c1 == this.nn.getCell()) {
			return(nn.getDist());
		}else
		if (c1 == this.ne.getCell()) {
			return(ne.getDist());
		}else
		if (c1 == this.nw.getCell()) {
			return(nw.getDist());
		}else if(c1 == this.ns.getCell()) {
			return(ns.getDist());
		}else 
			return Integer.MAX_VALUE;
	}
	
}
