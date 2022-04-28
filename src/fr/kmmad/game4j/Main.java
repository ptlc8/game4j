package fr.kmmad.game4j;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		Application.launch(args);
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

		//Text in game
		Text in_game = new Text();
		in_game.setText("Welcome inside the game !");
		in_game.setFont(new Font(50));

		//Grid of the game
		GridPane grid_in_game = new GridPane();
		grid_in_game.setId("grid_in_game");
		grid_in_game.setMinSize(1000, 700);
		grid_in_game.setVgap(50);

		grid_in_game.setAlignment(Pos.CENTER);
		grid_in_game.add(in_game, 0, 0);
		grid_in_game.setHalignment(in_game, HPos.CENTER);
		grid_in_game.setValignment(in_game, VPos.CENTER);


		Scene scene_in_game = new Scene(grid_in_game);
		scene_in_game.getStylesheets().add("in_game.css");

		play_button.setOnMouseClicked(event -> {
			stage.setScene(scene_in_game);
		});

		histo_button.setOnMouseClicked(event -> {
			grid_home.getChildren().remove(game);
			grid_home.getChildren().remove(play_button);
		});

		stage.setScene(scene_home);
		stage.setResizable(false);
		stage.show();
	}
}
