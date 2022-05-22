package fr.kmmad.game4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * recherche du QI du joueur en fonction de sa partie en faisant du datamining avec data.csv
 * @author Duncan
 */
public class DataMining{
	
	public static final String fileName = "data.csv";
	
	public static void main(String[] args) {
		System.out.println(testData());
	}
	
	public static float testData() {
		// Appel de la methode de lecture de csv 
		ArrayList<Map<String,String>> data = loadCSV(fileName);
		DataMining testDataMining = new DataMining(data.subList(0, (int)(0.8f*data.size())));
		int testDataNumber = data.size() - (int)(0.8f*data.size());
		int success = 0;
		for (int i = (int)(0.8f*data.size()); i < data.size(); i++) {
			int tauxOstacles = (int)Float.parseFloat(data.get(i).get("taux_obstacle"));
			int tauxBonus = (int)Float.parseFloat(data.get(i).get("taux_bonus"));
			boolean victoire = data.get(i).get("victoire").equals("yes");
			String QI = data.get(i).get("QI");
			if (testDataMining.getKnn(tauxOstacles, tauxBonus, victoire).equals(QI))
				success++;
		}
		return success*1f/testDataNumber;
	}
	
	private int [][][] tab3D;
	
	public DataMining() {
		// Appel de la methode de lecture de csv 
		this(loadCSV(fileName));
	}
	
	public DataMining(List<Map<String,String>> data) {
		tab3D = createTab(data);
	}
	
	public int[][][] createTab(List<Map<String,String>> data) {
		// tableau 3D avec taux d'obstacles de bonus et si victoire ou defaites 
		int[][][] tab3D = new int [100][100][2];
		
		for (int i = 0; i<data.size();i++) {
			
			int tauxObs = (int) (Float.parseFloat(data.get(i).get("taux_obstacle"))*100);
			int tauxBonus = (int) (Float.parseFloat(data.get(i).get("taux_bonus"))*100);
			int victory;
			if (data.get(i).get("victoire").equals("yes")) {
				victory = 1;
			}else {
				victory = 0;
			}
			
			if (data.get(i).get("QI").equals("high")) {
				tab3D[tauxObs][tauxBonus][victory]= 3;
			}else if (data.get(i).get("QI").equals("middle")) {
				tab3D[tauxObs][tauxBonus][victory]= 2;			
			}else {
				tab3D[tauxObs][tauxBonus][victory] = 1;
			}
		}
		
		return tab3D;
	}
	
	// methode pour lire un fichier data.csv
	public static ArrayList<Map<String,String>> loadCSV(String fileName) {
		ArrayList<Map<String,String>> data = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			String line = reader.readLine();
			if (line == null) return null;
			String[] headers = line.split(",");
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				Map<String, String> dataLine = new HashMap<>();
				for (int i = 0; i < values.length; i++)
				dataLine.put(headers[i], values[i]);
				data.add(dataLine);
			}
			reader.close();
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// methode qui renvoie le qi en fonction de tes k proches voisins 
	// En entree on a le taux obstacle de bonus de la partie ainsi que si elle a etait gagné 
	public String getKnn(int tauxObsGame, int tauxBonusGame, boolean victoryGame) {
		
		List<Integer> qiHighList = new ArrayList<Integer>();
		List<Integer> qiMiddleList = new ArrayList<Integer>();
		List<Integer> qiLowList = new ArrayList<Integer>();
		
		int k = 30;
		for (int i = Math.max(tauxObsGame-k, 0)  ; i< tauxObsGame + k && i < tab3D.length ;i++) {
			for (int j=Math.max(tauxBonusGame-k, 0)  ; j< tauxBonusGame + k && j < tab3D[i].length ;j++) {
				int l = victoryGame ? 1 : 0;
				if (tab3D[i][j][l] == 3) {
					qiHighList.add(tab3D[i][j][l]);
				}else if (tab3D[i][j][l] == 2) {
					qiMiddleList.add(tab3D[i][j][l]);
				}else if (tab3D[i][j][l] == 1) {
					qiLowList.add(tab3D[i][j][l]);
				}
			}
		}
		int a = qiHighList.size();
		int b = qiMiddleList.size();
		int c = qiLowList.size();
		if (a > b && a > c ) {
			return "high";
		}else if(b > c) {
			return "middle";
		}else {
			return "low";
		}
	}
	
	public String getQI(Game game) {
		return getKnn(game.getMap().getObstacles(), game.getMap().getBonus(), game.isVictory());
	}
		
}

