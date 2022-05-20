package fr.kmmad.game4j.javafx;

import fr.kmmad.game4j.Game;
import fr.kmmad.game4j.Game4j;

import java.util.Random;

import fr.kmmad.game4j.Cell.Type;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class GameScene extends Scene{
	
	private Text victoryText, defeatText, energyAmount, cancelAmount;
	private GridPane gridInGame;
	
	public GameScene(Game game) {
		super(new VBox(), 1000, 700);
		
		Text inGameText = new Text("Try to escape !");
		inGameText.setId("inGameText");
		
		ImageView homeButtonView = new ImageView(Main.homeImage);
		homeButtonView.setPickOnBounds(true);
		ImageView cancelButtonView = new ImageView(Main.cancelImage);
		cancelButtonView.setPickOnBounds(true);
		ImageView saveButtonView = new ImageView(Main.saveImage);
		saveButtonView.setPickOnBounds(true);
		ImageView energyImageView = new ImageView(Main.energyImage);
		ImageView cancelImageView = new ImageView(Main.cancelImage);
		
		Text shortestPathText = new Text("Shortest path");
		CheckBox shortestCheckBox = new CheckBox();
		HBox shortestHBox = new HBox();
		shortestHBox.getChildren().add(shortestPathText);
		shortestHBox.getChildren().add(shortestCheckBox);
		
		
		Text gamePathText = new Text("My path"); 
		CheckBox gameCheckBox = new CheckBox();
		HBox gameHBox = new HBox();
		gameHBox.getChildren().add(gamePathText);
		gameHBox.getChildren().add(gameCheckBox);
		
		//Vertical box left part
		VBox leftPart = new VBox();
		leftPart.getChildren().add(homeButtonView);
		leftPart.getChildren().add(shortestHBox);
		leftPart.getChildren().add(gameHBox);
		
		//Horizontal box buttons right
		HBox buttonsRight = new HBox();
		buttonsRight.getChildren().add(cancelButtonView);
		buttonsRight.getChildren().add(saveButtonView);

		//Text Energy
		energyAmount = new Text();
		energyAmount.setFont(new Font(25));
		
		//Horizontal box energy
		HBox energyHBox = new HBox();
		energyHBox.getChildren().add(energyImageView);
		energyHBox.getChildren().add(energyAmount);

		
		//Text Cancel
		cancelAmount = new Text();
		cancelAmount.setFont(new Font(25));
		
		//Horizontal box Cancel
		HBox cancelHBox = new HBox();
		cancelHBox.getChildren().add(cancelImageView);
		cancelHBox.getChildren().add(cancelAmount);
		
		
		//Vertical box of the Right part in game
		VBox rightPart = new VBox();
		rightPart.getChildren().add(buttonsRight);
		rightPart.getChildren().add(energyHBox);
		rightPart.getChildren().add(cancelHBox);
		
		
		//Grid of the game
		gridInGame = new GridPane();
		gridInGame.setId("gridInGame");
		gridInGame.setAlignment(Pos.CENTER);
		gridInGame.setHgap(1);
		gridInGame.setVgap(1);
		
		
		//Victory text
		victoryText = new Text("Well done you won !");
		victoryText.setFill(Color.BLUE);
	
		
		//Defeat text
		defeatText = new Text("Oh no you lose...");
		defeatText.setFill(Color.BLUE);
		
		refresh(game);
		
		//Pile sur la grille
		StackPane gameStack = new StackPane();
		gameStack.getChildren().add(gridInGame);
		gameStack.getChildren().add(victoryText);
		gameStack.getChildren().add(defeatText);
		
		BorderPane parent = new BorderPane();
		parent.setCenter(gameStack);
		parent.setId("inGame");
		parent.setTop(inGameText);
		BorderPane.setAlignment(inGameText, Pos.CENTER);
		parent.setRight(rightPart);
		parent.setLeft(leftPart);
		
		setRoot(parent);
		
		GameEventHandler gameEventHandler = new GameEventHandler(game){
			@Override
			public void refresh(Game game) {
				GameScene.this.refresh(game);
			}
		};
		
		getStylesheets().add("assets/inGame.css");
		
		cancelButtonView.setOnMouseClicked(gameEventHandler::cancel);
		setOnKeyPressed(gameEventHandler::move);
		
		//Game Buttons
		
		homeButtonView.setOnMouseClicked(event -> {
			switchToHomeScene();
		});
		
		saveButtonView.setOnMouseClicked(event -> {
			Game4j game4j = new Game4j();
			if(game.isFinished()) {
				game4j.addGameHistory(game);
			}else if (!game.isFinished()) {
				game4j.addGameSave(game, "uwu"+new Random().nextInt(5));
			}
		});

		parent.requestFocus();
		
	}
	
	public GameScene() {
		this(new Game(10,5,20));
	}
	
	protected abstract void switchToHomeScene();
	
	public void refresh(Game game) {
		victoryText.setVisible(game.isVictory());
		defeatText.setVisible(game.isDefeat());
		energyAmount.setText(""+game.getPlayer().getEnergy());
		cancelAmount.setText(""+ game.getPlayer().getAvailableCancelAmount());
    	gridInGame.getChildren().clear();
		for(int i = 0; i<game.getMap().getSize(); i++) {
			for(int j = 0; j<game.getMap().getSize(); j++) {
				ImageView cellView;
				if (game.getMap().getCell(i,j).getType() == Type.BONUS)
					cellView = new ImageView(Main.carrotOnFieldImage);
				else if (game.getMap().getCell(i,j).getType() == Type.OBSTACLE)
					cellView = new ImageView(Main.cornFieldImage);
				else
					cellView = new ImageView(Main.emptyFieldImage);
				gridInGame.add(cellView, j, i);
			}
		}
		ImageView playerView = new ImageView(Main.rabbitImage);
		gridInGame.add(playerView, game.getPlayer().getCell().getCoordY(), game.getPlayer().getCell().getCoordX());
		if(game.isFinished()) {
		}
	}
	
}
