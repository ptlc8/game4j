package fr.kmmad.game4j;

import fr.kmmad.game4j.Cell.Type;

public class Map2D {
	private Cell[][] matrix;
	
	public Map2D() {
		matrix = new Cell[10][10];
		for (int i=0; i<matrix.length; i ++) {
			matrix[i] = new Cell[10];
			for (int j=0; j<matrix.length; j ++) {
			matrix[i][j] = new Cell(i,j, i*10+j,Type.EMPTY);
			}
		}
	}
	
	public Cell getCell(int x,int y) {
		return matrix[x][y];
		
	}
	
	public void refreshMap() {
		
	}
	public int[][] GenerateMatDist() {
		int[][] matDist = new int[100][100];
		for (int i=0; i<matrix.length; i ++) {
			for (int j=0; j<matrix.length; j ++) {
				matDist[i*10+j]= new int[100];
				for (int k=0; k<matrix.length; k ++) {
					for (int l=0; l<matrix.length; l++) {
						if (matrix[i][j]==matrix[k][l]) {
							matDist[i*10+j][k*10+l]=0;
						}
						
						else {
							matDist[i*10+j][k*10+l]=matrix[i][j].getDist(matrix[k][l]);
						}
					}
				}
			}
		}
		return matDist;
	}
	
	
	
	
	
	
	
	

}
