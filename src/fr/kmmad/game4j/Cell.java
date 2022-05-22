package fr.kmmad.game4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente case :
 * @author Mattéo
 * @see Game
 * @see Player#Player(Cell, int)
 */
public class Cell implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public enum Type{
		EMPTY,BONUS,OBSTACLE
	}
	
	
	private int y;
	private int x;
	private int id;
	private List<Type> type = new ArrayList<>();
	private Neighbor nn;
	private Neighbor ne;
	private Neighbor nw;
	private Neighbor ns;
	
	public Cell(int x, int y, int id, Type type) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.type.add(type);
	}
	
	public int getId() {
		return id;
	}
	
	public Type getType() {
		return type.get(type.size()-1);
	}
	
	void setNextType(Type type) {
		this.type.add(type);
	}
	
	void resetPreviousType() {
		if (type.size() > 1)
			type.remove(type.size()-1);
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
	
	public Neighbor getNeigh(Direction direction) {
		switch (direction) {
		case EAST:
			return ne;
		case NORTH:
			return nn;
		case SOUTH:
			return ns;
		case WEST:
			return nw;
		}
		return null;
	}
	
	public int getEnergy() {
		if(getType() == Type.BONUS) {
			return 9;
		}else 
		if(getType() == Type.OBSTACLE) {
		return -10;
		}else
		return -1;
	}
	
	public int getInitialEnergy() {
		if(type.get(0) == Type.BONUS) {
			return 9;
		} else if(type.get(0) == Type.OBSTACLE) {
			return -10;
		} else
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
			if(nn!=null && c1 == this.nn.getCell()) {
			return(nn.getDist());
		}else
			if (ne!=null && c1 == this.ne.getCell()) {
			return(ne.getDist());
		}else
			if (nw!=null && c1 == this.nw.getCell()) {
			return(nw.getDist());
		}else 
			if(ns!=null && c1 == this.ns.getCell()) {
			return(ns.getDist());
		}else 
			return Integer.MAX_VALUE;
	}
	
}
