package fr.kmmad.game4j;

public class DataMining{
	
	
	
	public static void getQI(Game game) {
		int qi = 0;
		// on test si le joueur a gagné la partie 
		if ( game.isVictory()== true) {
			qi=qi + 5;
		}else {
			qi=qi+1;
		}
		// on test le pourcentage de bonus pris par le joueur 
		if (game.getBonusRate()/2 <= game.getPlayer().getNumberBonus()) {
			qi=qi+3;
		}else if(game.getBonusRate()/4 <= game.getPlayer().getNumberBonus()) {
			qi = qi + 1;
		}
		//test le pourcentage d'obstacles qui a eu dans la partie
		// on divise par 10 le pourcentage et c'est le nombre de point que l'on donne au qi 
		int percentageObstacles = game.getPlayer().getNumberBonus()/10;
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