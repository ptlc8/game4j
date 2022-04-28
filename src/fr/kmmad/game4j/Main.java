package fr.kmmad.game4j;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;



public class Main extends Application {
	
	public static void main(String[] args) throws IOException {
		Application.launch(args);
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

	@Override
	public void start(Stage stage) throws Exception {

		// Text for upper part of the vertical box
		Text game = new Text();
		game.setText("GAME4J");
		game.setFont(new Font(70));

		// Buttons in horizontal box
		//Button play
		Button play_button = new Button();
		play_button.getStyleClass().add("button");
		play_button.setId("play_button");
		play_button.setFont(new Font(40));
		play_button.setText("Jouer !");

		//Button replay
		Button replay_button = new Button();
		replay_button.getStyleClass().add("button");
		replay_button.setId("replay_button");
		replay_button.setFont(new Font(40));
		replay_button.setText("Reprendre !");

		//Button histo
		Button histo_button = new Button();
		histo_button.getStyleClass().add("button");
		histo_button.setId("histo_button");
		histo_button.setFont(new Font(40));
		histo_button.setText("Historique");

		//Button options
		Button option_button = new Button();
		option_button.getStyleClass().add("button");
		option_button.setId("option_button");
		option_button.setFont(new Font(40));
		option_button.setText("Options");

		//Grid of the menu
		GridPane grid_home = new GridPane();
		grid_home.setId("grid_home");
		grid_home.setMinSize(1000, 700);
		grid_home.setVgap(50);

		grid_home.setAlignment(Pos.CENTER);
		grid_home.add(game, 0, 0);
		grid_home.add(play_button, 0, 1);
		grid_home.add(replay_button, 0, 2);
		grid_home.add(histo_button, 0, 3);
		grid_home.add(option_button, 0, 4);
		grid_home.setHalignment(game, HPos.CENTER);
		grid_home.setValignment(game, VPos.CENTER);
		grid_home.setHalignment(play_button, HPos.CENTER);
		grid_home.setValignment(play_button, VPos.CENTER);
		grid_home.setHalignment(replay_button, HPos.CENTER);
		grid_home.setValignment(replay_button, VPos.CENTER);
		grid_home.setHalignment(histo_button, HPos.CENTER);
		grid_home.setValignment(histo_button, VPos.CENTER);
		grid_home.setHalignment(option_button, HPos.CENTER);
		grid_home.setValignment(option_button, VPos.CENTER);


		Scene scene_home = new Scene(grid_home);
		scene_home.getStylesheets().add("accueil.css");



		// NEW SCENE == IN GAME
		
		//Text in game
		Text in_game = new Text();
		in_game.setText("Welcome inside the game !");
		in_game.setFont(new Font(50));
		in_game.setX(50);
		in_game.setY(50);

		//Grid of the game
		GridPane grid_in_game = new GridPane();
		grid_in_game.setId("grid_in_game");
		grid_in_game.setMinSize(1000, 900);
		grid_in_game.setAlignment(Pos.CENTER);
		grid_in_game.setLayoutY(50);
		
		
		int count = 0;
		int s = 80;
		for(int i = 1; i<11; i++) {
			count++;
			for(int j = 0; j<10; j++) {
				Rectangle r = new Rectangle(s, s, s, s);
				if (count % 2 == 0)
		          r.setFill(Color.WHITE);
		        grid_in_game.add(r, j, i);
		        count++;
			}
		}
		
		
		Group group = new Group();
		group.getChildren().add(in_game);
		group.getChildren().add(grid_in_game);		

		Scene scene_in_game = new Scene(group, 1000, 1000);
		scene_in_game.getStylesheets().add("in_game.css");

		play_button.setOnMouseClicked(event -> {
			stage.setScene(scene_in_game);
		});

		histo_button.setOnMouseClicked(event -> {
			grid_home.getChildren().remove(game);
			grid_home.getChildren().remove(play_button);
		});

		Image icon = new Image("icon.png");
		stage.getIcons().add(icon);
		stage.setScene(scene_home);
		stage.setTitle("GAME4J");
		stage.setResizable(false);
		stage.show();
	}
}
