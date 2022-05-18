package fr.kmmad.game4j;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import fr.kmmad.game4j.Cell.Type;



public class Main extends Application {
	
	public static void main(String[] args) throws IOException {
		Application.launch(args);
		

	}

	@Override
	public void start(Stage stage) throws Exception {
		
		
		//NEW SCENE == MENU
		
		
		//Music theme
		String ssoundFrigidaire = new File("bin/assets/frigidaire.mp3").toURI().toString();
		Media soundFrigidaire = new Media(ssoundFrigidaire);
		MediaPlayer playerFrigidaire = new MediaPlayer(soundFrigidaire);
		MediaView viewPlayer = new MediaView(playerFrigidaire);
		playerFrigidaire.play();
		
		
		// Text for upper part of the vertical box
		Text titleHome = new Text("GAME4J");
		titleHome.setFont(new Font(70));

		//Button play
		Button playButton = new Button("Play");
		playButton.getStyleClass().add("button");
		playButton.setId("playButton");
		playButton.setFont(new Font(40));

		//Button replay
		Button replayButton = new Button("Replay");
		replayButton.getStyleClass().add("button");
		replayButton.setId("replayButton");
		replayButton.setFont(new Font(40));

		//Button histo
		Button histoButton = new Button("Historique");
		histoButton.getStyleClass().add("button");
		histoButton.setId("histoButton");
		histoButton.setFont(new Font(40));

		//Button options
		Button optionButton = new Button("Options");
		optionButton.getStyleClass().add("button");
		optionButton.setId("optionButton");
		optionButton.setFont(new Font(40));

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
		sceneHome.getStylesheets().add("assets/accueil.css");


		
		
		
		

		// NE == IN GAME

		Game game = new Game(5,20);
		
		
		//Text in game
		Text inGameText = new Text("Try to escape !");
		inGameText.setFont(new Font(25));
		inGameText.setId("inGameText");
		
		
		//Home button
		Rectangle homeButton = new Rectangle(50,50,50,50);
		homeButton.setFill(Color.BROWN);
		homeButton.setId("homeButton");
		
		//Vertical box left part
		VBox leftPart = new VBox();
		leftPart.getChildren().add(homeButton);		
		
		
		//Cancel move button
		Rectangle previousButton = new Rectangle(50,50,50,50);
		previousButton.setFill(Color.GREEN);
		previousButton.setId("previousButton");
		
		//Save button
		Rectangle saveButton = new Rectangle(50,50,50,50);
		saveButton.setFill(Color.RED);
		saveButton.setId("saveButton");
		
		//Horizontal box buttons right
		HBox buttonsRight = new HBox();
		buttonsRight.getChildren().add(previousButton);
		buttonsRight.getChildren().add(saveButton);
		
		
		//Image Energy
		Rectangle energyImage = new Rectangle(50,50,50,50);
		energyImage.setFill(Color.RED);
		energyImage.setId("energyImage");

		//Text Energy
		Text energyAmount = new Text(""+game.getPlayer().getEnergy());
		energyAmount.setFont(new Font(25));
		
		//Horizontal box energy
		HBox energyHBox = new HBox();
		energyHBox.getChildren().add(energyImage);
		energyHBox.getChildren().add(energyAmount);
		
		
		//Image Cancel
		Rectangle cancelImage = new Rectangle(50,50,50,50);
		cancelImage.setFill(Color.RED);
		cancelImage.setId("cancelImage");
		
		//Text Cancel
		Text cancelAmount = new Text(""+game.getPlayer().getAvailableCancelAmount());
		cancelAmount.setFont(new Font(25));
		
		//Horizontal box Cancel
		HBox cancelHBox = new HBox();
		cancelHBox.getChildren().add(cancelImage);
		cancelHBox.getChildren().add(cancelAmount);
		
		
		//Vertical box of the Right part in game
		VBox rightPart = new VBox();
		rightPart.getChildren().add(buttonsRight);
		rightPart.getChildren().add(energyHBox);
		rightPart.getChildren().add(cancelHBox);
		
		
		//Grid of the game
		GridPane gridInGame = new GridPane();
		gridInGame.setId("gridInGame");
		gridInGame.setAlignment(Pos.CENTER);
		
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
		
		//Victory text
		Text victoryText = new Text("Well done you won !");
		victoryText.setFill(Color.BLUE);
		victoryText.setVisible(false);
	
		
		//Defeat text
		Text defeatText = new Text("Oh no you lose...");
		defeatText.setFill(Color.BLUE);
		defeatText.setVisible(false);
		
		//Pile sur la grille
		StackPane gameStack = new StackPane();
		gameStack.getChildren().add(gridInGame);
		gameStack.getChildren().add(victoryText);
		gameStack.getChildren().add(defeatText);
		
		
		BorderPane borderPane = new BorderPane(gameStack);
		borderPane.setTop(inGameText);
		BorderPane.setAlignment(inGameText, Pos.CENTER);
		borderPane.setRight(rightPart);
		borderPane.setLeft(leftPart);
			

		Scene sceneInGame = new Scene(borderPane, 1000, 1000);
		sceneInGame.getStylesheets().add("assets/inGame.css");
		
		
		GameEventHandler gameEventHandler = new GameEventHandler(game){
			@Override
			public void refresh(Game game) {
				if(game.isVictory()) {
					victoryText.setVisible(true);
				}
				if(game.isDefeat()) {
					defeatText.setVisible(true);
				}
				energyAmount.setText(""+game.getPlayer().getEnergy());
				cancelAmount.setText(""+ game.getPlayer().getAvailableCancelAmount());
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
		
		//Game Buttons
		
		homeButton.setOnMouseClicked(event -> {
			stage.setScene(sceneHome);
		});
		
		
		
		//NEW scene == REPLAY
		
		//Home button
		Rectangle homeButton2 = new Rectangle(50,50,50,50);
		homeButton2.setFill(Color.BROWN);
		homeButton2.setId("homeButton");

		
		Text replayTitle = new Text("Replay");
		replayTitle.setFont(new Font(50));
		
		VBox menuReplay = new VBox();
		menuReplay.getChildren().add(homeButton2);
		menuReplay.getChildren().add(replayTitle);
		
		Scene sceneReplay = new Scene(menuReplay, 1000, 700);
		
		homeButton2.setOnMouseClicked(event -> {
			stage.setScene(sceneHome);
		});
		
		
		
		
		// NEW SCENE == HISTO
		
		//Home button
		Rectangle homeButton3 = new Rectangle(50,50,50,50);
		homeButton3.setFill(Color.BROWN);
		homeButton3.setId("homeButton");
		
		//Histo title
		Text histoTitle = new Text("Historique");
		histoTitle.setFont(new Font(50));
		
		
		VBox histoMenu = new VBox();
		histoMenu.getChildren().add(homeButton3);
		histoMenu.getChildren().add(histoTitle);
		
		Scene sceneHisto = new Scene(histoMenu, 1000, 700);
		
		
		homeButton3.setOnMouseClicked(event -> {
			stage.setScene(sceneHome);
		});
		
		
		// NEW SCENE == OPTIONS
		
		
		//Home button
		Rectangle homeButton4 = new Rectangle(50,50,50,50);
		homeButton4.setFill(Color.BROWN);
		homeButton4.setId("homeButton");
		
		Text optionTitle = new Text("OPTIONS");
		optionTitle.setFont(new Font(50));
		
		Text soundTitle = new Text("Sound");
		soundTitle.setFont(new Font(50));
		
		Text musicTitle = new Text("Music");
		musicTitle.setFont(new Font(50));
		
		Slider musicSlider = new Slider();
		musicSlider.setMin(0);
		musicSlider.setMax(100);
		musicSlider.setShowTickLabels(false);
		
		
		
		Text effectTitle = new Text("Effects");
		effectTitle.setFont(new Font(50));
		
		Slider effectSlider = new Slider();
		effectSlider.setMin(0);
		effectSlider.setMax(100);
		effectSlider.setShowTickLabels(false);
		
		Text clearHisto = new Text("Clear historique");
		clearHisto.setFont(new Font(50));
		
		
		
		VBox optionVBox = new VBox();
		optionVBox.getChildren().add(homeButton4);
		optionVBox.getChildren().add(optionTitle);
		optionVBox.getChildren().add(soundTitle);
		optionVBox.getChildren().add(musicTitle);
		optionVBox.getChildren().add(musicSlider);
		optionVBox.getChildren().add(effectTitle);
		optionVBox.getChildren().add(effectSlider);
		optionVBox.getChildren().add(clearHisto);
		optionVBox.setAlignment(Pos.CENTER);
		
		
		Scene sceneOptions = new Scene(optionVBox, 1000, 700);
		sceneInGame.getStylesheets().add("assets/options.css");
		
		//option buttons
		
		homeButton4.setOnMouseClicked(event -> {
			stage.setScene(sceneHome);
		});
		
		clearHisto.setOnMouseClicked(event -> {
			
		});
		
		
		
		// Home buttons
		
		playButton.setOnMouseClicked(event -> {
			stage.setScene(sceneInGame);
			borderPane.requestFocus();
		});
		
		replayButton.setOnMouseClicked(event -> {
			stage.setScene(sceneReplay);
		});

		histoButton.setOnMouseClicked(event -> {
			stage.setScene(sceneHisto);
		});
		
		optionButton.setOnMouseClicked(event -> {
			stage.setScene(sceneOptions);
		});

		Image icon = new Image("assets/icon.png");
		stage.getIcons().add(icon);
		stage.setScene(sceneHome);
		stage.setTitle("GAME4J");
		stage.setResizable(false);
		stage.show();
	}
}
