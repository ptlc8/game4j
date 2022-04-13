package Game4j;

public class Neighbor {

	private Cell cell;
	private int dist;
	
	public Neighbor(Cell n, int dist) {
		this.n = n;
		this.dist = dist;
	}
	
	public int getDist() {
		return this.dist;
	}
	
	public Cell getCell() {
		return this.cell;
	}
}
