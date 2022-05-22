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
	
	private int [][][] tab3D;
	
	public DataMining() {
		// tableau 3D avec taux d'obstacles de bonus et si victoire ou defaites 
		tab3D = new int [100][100][2];
		
		// Appel de la methode de lecture de csv 
		ArrayList<Map<String,String>> data = loadCSV("data.csv");
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
	}
	
	// methode pour lire un fichier data.csv
	public static ArrayList<Map<String,String>> loadCSV(String fileName) {
		ArrayList<Map<String,String>> data = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			String line = reader.readLine();
			line = line.substring(0, line.length()-2);
			if (line == null) return null;
			String[] headers = line.split(",");
			while ((line = reader.readLine()) != null) {
				line = line.substring(0, line.length()-2);
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
					System.out.println("ijl "+i+" "+j+" "+l);
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
			return ("QI High");
		}else if(b > a && b>c) {
			return ("QI Middle");
		}else {
			return ("QI Low");
		}
	}
	
	public String getQI(Game game) {
		return getKnn(game.getObstacleRate(), game.getBonusRate(), game.isVictory());
	}
		
}

