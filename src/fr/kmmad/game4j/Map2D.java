package fr.kmmad.game4j;

public class Map2D {
	private Cell[][] matrix;
	
	public Map2D() {
		matrix = new Cell[10][10];
		for (int i=0; i<matrix.length; i ++) {
			matrix[i] = new Cell[10];
			for (int j=0; j<matrix.length; j ++) {
			matrix[i][j] = new Cell();
			}
		}
	}
	
	public Cell getCell(int x,int y) {
		return matrix[x][y];
		
	}
	
	public void refreshMap() {
		
	}
	public int GenerateMatDist() {
		
	}
	
	
	
	
	
	
	
	

}
