package fr.kmmad.game4j.javafx;

import fr.kmmad.game4j.Game;
import fr.kmmad.game4j.Game4j;

import java.sql.SQLException;
import java.util.Random;

import fr.kmmad.game4j.Cell.Type;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class GameScene extends Scene{
	
	private Text victoryText, defeatText, energyAmount, cancelAmount;
	private GridPane gridInGame;
	
	public GameScene(Game game) {
		super(new VBox(), 1000, 1000);
		
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
			game4j.addGameSave(game, "uwu"+new Random().nextInt(5));
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
    	gridInGame.getChildren().removeAll();
		int s = 80;
		for(int i = 0; i<game.getMap().getSize(); i++) {
			for(int j = 0; j<game.getMap().getSize(); j++) {
				Rectangle r = new Rectangle(s, s, s, s);
				if (game.getMap().getCell(i,j).getType() == Type.BONUS)
					r.setFill(Color.GREEN);
				else if (game.getMap().getCell(i,j).getType() == Type.OBSTACLE)
					r.setFill(Color.RED);
				else if (game.getPlayer().getCell().getCoordX() == i && game.getPlayer().getCell().getCoordY() == j)
					r.setFill(Color.YELLOW);
				else r.setFill(Color.WHITE);
				gridInGame.add(r, j, i+1);
			}
		}
		if(game.isFinished()) {
		}
	}
	
}
