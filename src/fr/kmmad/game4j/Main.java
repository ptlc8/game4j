package fr.kmmad.game4j;

import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
			
		Game game = new Game(5,20);
		Scanner sc = new Scanner(System.in);
		game.display();
		String line;
		while ((line = sc.next()) != null) {
			switch (line) {
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
			}
			game.display();
		}
		sc.close();
	}

}
