package fr.kmmad.game4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConsoleMain {
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		Game game = null;
		while (true) {
			System.out.println("1 : Nouvelle partie");
			System.out.println("2 : Charger la sauvegarde");
			String line = sc.next();
			if (line.equals("1"))
				game = new Game(5, 20);
			else if (line.equals("2")) {
				game = Game.loadSave(new String(new FileInputStream(new File("save")).readAllBytes()));
			} if (game != null)
				break;
		}
		System.out.println("Contrôles :");
		System.out.println("     ↑     ");
		System.out.println("     8     ");
		System.out.println(" ← 4   6 → ");
		System.out.println("     2      s sauvegarder");
		System.out.println("     ↓      0 annuler");
		System.out.println("Nombre de déplacements pour le plus court chemin possible : "+game.getMap().shortPath(game.getMap().getCell(0), game.getMap().getCell(99)).size());
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
					PrintWriter writer = new PrintWriter(new FileOutputStream(new File("save")));
					writer.write(save);
					writer.flush();
					writer.close();
					System.out.println("Sauvegarde réussi !");
				}
				break;
			}
			game.display();
		}
		sc.close();
	}

}