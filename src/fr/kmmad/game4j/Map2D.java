package fr.kmmad.game4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import fr.kmmad.game4j.Cell.Type;

public class Map2D implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Cell[][] matrix;
	Random rn = new Random();
	private int size;

	public Map2D(int size, int bnsRate, int obsRate) {
		this.size = size;
		generateMap(bnsRate, obsRate);
		while (shortPath(getCell(0,0),getCell(size -1,size -1)) == null) { // s'assurer qu'un plus court chemin existe
			generateMap(bnsRate, obsRate);
		}
	}	
	
	private void generateMap(int bnsRate, int obsRate) {
		matrix = new Cell[size][size]; // initialisation du plateau
		for (int i=0; i<matrix.length; i ++) {
			matrix[i] = new Cell[size];
			for (int j=0; j<matrix.length; j ++) {	
				int rand = rn.nextInt(100) + 1;
				if(rand<bnsRate) {
					matrix[i][j] = new Cell(i,j, i*size+j,Type.BONUS);
				}else if(bnsRate<rand && rand<bnsRate+obsRate) {
					matrix[i][j] = new Cell(i,j, i*size+j,Type.OBSTACLE);
				}else {
					matrix[i][j] = new Cell(i,j, i*size+j,Type.EMPTY);
				}
			}
		}
		for (int i=0; i<matrix.length; i ++) {
			for (int j=0; j<matrix.length; j ++) {
				if (i > 0)
					matrix[i][j].setNeighN(new Neighbor(matrix[i-1][j], 1));
				if (j > 0)
					matrix[i][j].setNeighW(new Neighbor(matrix[i][j-1], 1));
				if (i < size-1)
					matrix[i][j].setNeighS(new Neighbor(matrix[i+1][j], 1));
				if (j < size-1)
					matrix[i][j].setNeighE(new Neighbor(matrix[i][j+1], 1));
			}
		}
	}
	
	public Cell getCell(int x,int y) {
		return matrix[x][y];
		
	}
	
	public Cell getCell(int id) {
		return matrix[id/size][id%size];
		
	}
	public int[][] generateMatDist() {
		int[][] matDist = new int[size*size][size*size];
		for (int i=0; i<matrix.length; i ++) {
			for (int j=0; j<matrix.length; j ++) {
				matDist[i*size+j]= new int[size*size];
				for (int k=0; k<matrix.length; k ++) {
					for (int l=0; l<matrix.length; l++) {
						if (matrix[i][j]==matrix[k][l]) {
							matDist[i*size+j][k*size+l]=0;
						}
						
						else {
							matDist[i*size+j][k*size+l]=matrix[i][j].getDist(matrix[k][l]);
						}
					}
				}
			}
		}
		return matDist;
	}
	
	public int[][] generateMatEnergy() { // initialisation de la matrice d'énergie 
		int[][] matEnergy = new int[size][size];
		for (int i=0; i<matrix.length; i ++) {
			matEnergy[i]= new int[size];
			for (int j=0; j<matrix.length; j ++) {
				matEnergy[i][j]=matrix[i][j].getEnergy();	
			}
		}
		return matEnergy;
	}
	
	public ArrayList<Cell> shortPath(Cell c1, Cell c2) { // c1 cell de départ et c2 cell d'arrivée
		int[][] graph = generateMatDist(); // graph représente le plus court chemin
		int[] preced = new int[graph.length];
		int[] distOrigin = new int[graph.length];
		for(int i=0; i<graph.length; i++) {  //initialisation
			distOrigin[i] = Integer.MAX_VALUE;
			preced[i] = -1;
		}
		distOrigin[c1.getId()]=0;
		for(int i=0; i<graph.length; i++) {
			if (!getCell(i).getType().equals(Cell.Type.OBSTACLE)) { // test si la case n'est pas un obstacle
				for(int j=0; j<graph.length; j++) {
					if (!getCell(j).getType().equals(Cell.Type.OBSTACLE)) {
						if (graph[i][j] < Integer.MAX_VALUE && distOrigin[i]<Integer.MAX_VALUE) {
							if (graph[i][j] + distOrigin[i] < distOrigin[j]) {
								distOrigin[j]= graph[i][j] + distOrigin[i];
								preced[j]=i;
							}
						}
					}
				}
			}
		}
		ArrayList<Cell> shortPath = new ArrayList<Cell>();
		shortPath.add(c2);
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
	
	public int getSize() {
		return this.size;
	}
}










