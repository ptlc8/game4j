package fr.kmmad.game4j;

import java.util.Scanner;

public class ConsoleMain {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Game4j game4j = new Game4j();
		Game game = null;
		while (true) {
			System.out.println("1 : New game");
			System.out.println("2 : Load a save");
			String line = sc.next();
			if (line.equals("1"))
				game = new Game(10,5,20);
			else if (line.equals("2")) {
				game = game4j.getSave("save1");
			} if (game != null)
				break;
		}
		System.out.println("Controls :");
		System.out.println("     ↑     ");
		System.out.println("     8     ");
		System.out.println(" ← 4   6 → ");
		System.out.println("     2      s save");
		System.out.println("     ↓      0 cancel");
		System.out.println("Number of moves for the shortest path: "+game.getMap().shortPath(game.getStartCell(), game.getEndCell()).size());
		System.out.println("Number of moves for the shortest path with energy: "+game.getMap().shortPathEnergy(game.getStartCell(), game.getEndCell()).size());
		game.display();
		String line;
		while ((line = sc.next()) != null) {
			switch (line.toLowerCase()) {
			case "8": 
				game.move(Direction.NORTH);
				break;
			case "4":
				game.move(Direction.WEST);
				break;
			case "2":
				game.move(Direction.SOUTH);
				break;
			case "6":
				game.move(Direction.EAST);
				break;
			case "0":
				game.cancelMove();
				break;
			case "s":
				String save = game.createSave();
				if (save != null) {
					game4j.addGameSave(game, "save1");
					System.out.println("Successfully saved !");
				}
				break;
			}
			game.display();
			if (game.isFinished())
				break;
		}
		sc.close();
		System.out.println("Your IQ is "+new DataMining().getQI(game));
		
	}

}