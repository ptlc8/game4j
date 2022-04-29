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
		Text titleHome = new Text();
		titleHome.setText("GAME4J");
		titleHome.setFont(new Font(70));

		// Buttons in horizontal box
		//Button play
		Button playButton = new Button();
		playButton.getStyleClass().add("button");
		playButton.setId("playButton");
		playButton.setFont(new Font(40));
		playButton.setText("Jouer !");

		//Button replay
		Button replayButton = new Button();
		replayButton.getStyleClass().add("button");
		replayButton.setId("replayButton");
		replayButton.setFont(new Font(40));
		replayButton.setText("Reprendre !");

		//Button histo
		Button histoButton = new Button();
		histoButton.getStyleClass().add("button");
		histoButton.setId("histoButton");
		histoButton.setFont(new Font(40));
		histoButton.setText("Historique");

		//Button options
		Button optionButton = new Button();
		optionButton.getStyleClass().add("button");
		optionButton.setId("optionButton");
		optionButton.setFont(new Font(40));
		optionButton.setText("Options");

		//Grid of the menu
		GridPane gridHome = new GridPane();
		gridHome.setId("gridHome");
		gridHome.setMinSize(1000, 700);
		gridHome.setVgap(50);

		gridHome.setAlignment(Pos.CENTER);
		gridHome.add(titleHome, 0, 0);
		gridHome.add(playButton, 0, 1);
		gridHome.add(replayButton, 0, 2);
		gridHome.add(histoButton, 0, 3);
		gridHome.add(optionButton, 0, 4);
		GridPane.setHalignment(titleHome, HPos.CENTER);
		GridPane.setValignment(titleHome, VPos.CENTER);
		GridPane.setHalignment(playButton, HPos.CENTER);
		GridPane.setValignment(playButton, VPos.CENTER);
		GridPane.setHalignment(replayButton, HPos.CENTER);
		GridPane.setValignment(replayButton, VPos.CENTER);
		GridPane.setHalignment(histoButton, HPos.CENTER);
		GridPane.setValignment(histoButton, VPos.CENTER);
		GridPane.setHalignment(optionButton, HPos.CENTER);
		GridPane.setValignment(optionButton, VPos.CENTER);


		Scene sceneHome = new Scene(gridHome);
		sceneHome.getStylesheets().add("accueil.css");


		
		
		
		

		// NEW SCENE == IN GAME

		Game game = new Game(5,20);
		
		//Text in game
		Text inGameText = new Text();
		inGameText.setText("Try to escape !");
		inGameText.setFont(new Font(25));
		inGameText.setX(50);
		inGameText.setY(50);
		
		//Text Energy
		Text energyText = new Text();
		energyText.setText("Energy = "+game.getPlayer().getEnergy());
		energyText.setFont(new Font(25));
		energyText.setX(50);
		energyText.setY(75);
		
		//Button previous movement
		Rectangle previousButton = new Rectangle(50,50,50,50);
		previousButton.setFill(Color.GREEN);
		
		
		/*Button previousButton = new Button();
		previousButton.setFont(new Font(40));
		previousButton.setText("Previous !");
		previousButton.setLayoutX(900);
		previousButton.setLayoutY(100);*/
		
		
		//Grid of the game
		GridPane gridInGame = new GridPane();
		gridInGame.setId("gridInGame");
		gridInGame.setMinSize(800, 800);
		gridInGame.setAlignment(Pos.CENTER);
		gridInGame.setLayoutX(100);
		gridInGame.setLayoutY(100);
		
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
				gridInGame.add(r, j, i+1);
			}
		}
		
		Group group = new Group();
		group.getChildren().add(inGameText);
		group.getChildren().add(energyText);
		group.getChildren().add(gridInGame);	
		group.getChildren().add(previousButton);
			

		Scene sceneInGame = new Scene(group, 1000, 1000);
		sceneInGame.getStylesheets().add("inGame.css");
		
		
		
		GameEventHandler gameEventHandler = new GameEventHandler(game){
			@Override
			public void refresh(Game game) {
				energyText.setText("Energy = "+ game.getPlayer().getEnergy());
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
						gridInGame.add(r, j, i+1);
					}
				}
			}
		};
		
		previousButton.setOnMouseClicked(gameEventHandler::cancel);
		sceneInGame.setOnKeyPressed(gameEventHandler::move);
		
		
		// Home buttons
		
		playButton.setOnMouseClicked(event -> {
			stage.setScene(sceneInGame);
			group.requestFocus();
		});

		histoButton.setOnMouseClicked(event -> {
			gridHome.getChildren().remove(titleHome);
			gridHome.getChildren().remove(playButton);
		});
		
		
		

		Image icon = new Image("icon.png");
		stage.getIcons().add(icon);
		stage.setScene(sceneHome);
		stage.setTitle("GAME4J");
		stage.setResizable(false);
		stage.show();
	}
}
