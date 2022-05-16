package fr.kmmad.game4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import fr.kmmad.game4j.Cell.Type;

public class Map2D implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Cell[][] matrix;
	Random rn = new Random();

	public Map2D(int bnsRate, int obsRate) {
		generateMap(bnsRate, obsRate);
		while (shortPath(getCell(0,0),getCell(9,9)) == null) {
			generateMap(bnsRate, obsRate);
		}
	}	
	
	private void generateMap(int bnsRate, int obsRate) {
		matrix = new Cell[10][10];
		for (int i=0; i<matrix.length; i ++) {
			matrix[i] = new Cell[10];
			for (int j=0; j<matrix.length; j ++) {	
				int rand = rn.nextInt(100) + 1;
				if(rand<bnsRate) {
					matrix[i][j] = new Cell(i,j, i*10+j,Type.BONUS);
				}else if(bnsRate<rand && rand<bnsRate+obsRate) {
					matrix[i][j] = new Cell(i,j, i*10+j,Type.OBSTACLE);
				}else {
					matrix[i][j] = new Cell(i,j, i*10+j,Type.EMPTY);
				}
			}
		}
		for (int i=0; i<matrix.length; i ++) {
			for (int j=0; j<matrix.length; j ++) {
				if (i > 0)
					matrix[i][j].setNeighN(new Neighbor(matrix[i-1][j], 1));
				if (j > 0)
					matrix[i][j].setNeighW(new Neighbor(matrix[i][j-1], 1));
				if (i < 9)
					matrix[i][j].setNeighS(new Neighbor(matrix[i+1][j], 1));
				if (j < 9)
					matrix[i][j].setNeighE(new Neighbor(matrix[i][j+1], 1));
			}
		}
	}
	
	public Cell getCell(int x,int y) {
		return matrix[x][y];
		
	}
	
	public Cell getCell(int id) {
		return matrix[id/10][id%10];
		
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
	
	public int[][] GenerateMatEnergy() {
		int[][] matEnergy = new int[10][10];
		for (int i=0; i<matrix.length; i ++) {
			matEnergy[i]= new int[10];
			for (int j=0; j<matrix.length; j ++) {
				matEnergy[i][j]=matrix[i][j].getEnergy();	
			}
		}
		return matEnergy;
	}
	
	public ArrayList<Cell> shortPath(Cell c1, Cell c2) { // c1 cell de départ et c2 arrivée
		int[][] graph = GenerateMatDist();
		int[] preced = new int[graph.length];
		int[] distOrigin = new int[graph.length];
		for(int i=0; i<matrix.length; i++) {  //initialisation
			distOrigin[i] = Integer.MAX_VALUE;
			preced[i] = -1;
		}
		distOrigin[c1.getId()]=0;
		for(int i=0; i<matrix.length; i++) {
			if (!getCell(i).getType().equals(Cell.Type.OBSTACLE)) { // test si la case est un obstacle
				for(int j=0; j<matrix.length; j++) {
					if (graph[i][j] < Integer.MAX_VALUE) {
						if (graph[i][j] + distOrigin[i] < distOrigin[j]) {
							distOrigin[j]= graph[i][j] + distOrigin[i];
							preced[j]=i;
						}
					}
				}
			}
		}
		ArrayList<Cell> shortPath = new ArrayList<Cell>();
		int idt = c2.getId();
		while (idt != c1.getId()) {
			if (preced[idt] == -1) {
				return null;
			}
			else {
				shortPath.add(getCell(preced[idt]));
				idt = preced[idt];
			}
		}
		return shortPath;
	}

}










