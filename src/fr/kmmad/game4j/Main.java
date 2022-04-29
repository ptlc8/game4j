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

import java.io.IOException;

import fr.kmmad.game4j.Cell.Type;



public class Main extends Application {
	
	public static void main(String[] args) throws IOException {
		Application.launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		
		// Text for upper part of the vertical box
		Text title = new Text();
		title.setText("GAME4J");
		title.setFont(new Font(70));

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
		grid_home.add(title, 0, 0);
		grid_home.add(play_button, 0, 1);
		grid_home.add(replay_button, 0, 2);
		grid_home.add(histo_button, 0, 3);
		grid_home.add(option_button, 0, 4);
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setValignment(title, VPos.CENTER);
		GridPane.setHalignment(play_button, HPos.CENTER);
		GridPane.setValignment(play_button, VPos.CENTER);
		GridPane.setHalignment(replay_button, HPos.CENTER);
		GridPane.setValignment(replay_button, VPos.CENTER);
		GridPane.setHalignment(histo_button, HPos.CENTER);
		GridPane.setValignment(histo_button, VPos.CENTER);
		GridPane.setHalignment(option_button, HPos.CENTER);
		GridPane.setValignment(option_button, VPos.CENTER);


		Scene scene_home = new Scene(grid_home);
		scene_home.getStylesheets().add("accueil.css");


		
		
		
		

		// NEW SCENE == IN GAME
		
		Game game = new Game(5,20);
		
		//Text in game
		Text in_game = new Text();
		in_game.setText("Try to escape !");
		in_game.setFont(new Font(25));
		in_game.setX(50);
		in_game.setY(50);
		
		//Text Energy
		Text energy = new Text();
		energy.setText("Energy = "+game.getPlayer().getEnergy());
		energy.setFont(new Font(25));
		energy.setX(50);
		energy.setY(75);
		
		//Button previous movement
		Button previous = new Button();
		previous.setFont(new Font(40));
		previous.setText("Previous !");
		previous.setLayoutX(900);
		previous.setLayoutY(100);
		
		
		//Grid of the game
		GridPane grid_in_game = new GridPane();
		grid_in_game.setId("grid_in_game");
		grid_in_game.setMinSize(1000, 900);
		grid_in_game.setAlignment(Pos.CENTER);
		grid_in_game.setLayoutY(50);
		
		
		int s = 80;
		for(int i = 0; i<10; i++) {
			for(int j = 0; j<10; j++) {
				Rectangle r = new Rectangle(s, s, s, s);
				if (game.getMap().getCell(i,j).getType() == Type.BONUS)
					r.setFill(Color.GREEN);
				else if (game.getMap().getCell(i,j).getType() == Type.OBSTACLE)
					r.setFill(Color.RED);
				else if (game.getPlayer().getCell().getCoordX() == i && game.getPlayer().getCell().getCoordY() == j)
					r.setFill(Color.YELLOW);
				else r.setFill(Color.BLACK);
				grid_in_game.add(r, j, i+1);
			}
		}
		
		
		Group group = new Group();
		group.getChildren().add(in_game);
		group.getChildren().add(energy);
		//group.getChildren().add(previous); // ICI EST LE PROBLEME !!!!
		group.getChildren().add(grid_in_game);		

		Scene scene_in_game = new Scene(group, 1000, 1000);
		scene_in_game.getStylesheets().add("in_game.css");
		
		GetGameEventHandler getGameEvent = new GetGameEventHandler(game, grid_in_game, energy, previous);
		scene_in_game.setOnKeyPressed(getGameEvent);
		scene_in_game.setOnMouseClicked(getGameEvent);
		
		// Home buttons
		
		play_button.setOnMouseClicked(event -> {
			stage.setScene(scene_in_game);
		});

		histo_button.setOnMouseClicked(event -> {
			grid_home.getChildren().remove(title);
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
