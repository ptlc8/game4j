package fr.kmmad.game4j;

public class DataMining{
	
	public void getQI(Game nameSave) {
		int qi = 0;
		// on test si le joueur a gagné la partie 
		if ( nameSave.isVictory()== true) {
			qi=qi + 5;
		}else {
			qi=qi+1;
		}
		// on test le pourcentage de bonus pris par le joueur 
		if (nameSave.getBonusRate()/2 <= nameSave.getPlayer().getNumberBonus()) {
			qi=qi+3;
		}else if(nameSave.getBonusRate()/4 <= nameSave.getPlayer().getNumberBonus()) {
			qi = qi + 1;
		}
		//test le pourcentage d'obstacles qui a eu dans la partie
		// on divise par 10 le pourcentage et c'est le nombre de point que l'on donne au qi 
		int percentageObstacles = nameSave.getPlayer().getNumberBonus()/10;
		qi = qi + percentageObstacles;

		if (qi >= 8 ){
			System.out.println("QI HAUT");
		}else if (qi >= 5) {
			System.out.println("QI MOYEN");
		}else {
			System.out.println("QI BAS");

		}
	}

}