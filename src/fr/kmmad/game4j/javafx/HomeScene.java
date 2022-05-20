package fr.kmmad.game4j.javafx;

import java.sql.SQLException;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class HomeScene extends Scene{
	
	public HomeScene(MediaPlayer player) {
		super(new VBox(), 1000, 700);
		
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
		setRoot(gridHome);

		getStylesheets().add("assets/accueil.css");
		
		// Home buttons

		playButton.setOnMouseClicked(event -> {
			switchToScene(new GameScene() {
				@Override
				public void switchToHomeScene() {
					switchToScene(HomeScene.this);
				}
			});
		});
		
		replayButton.setOnMouseClicked(event -> {
			switchToScene(new ReplayScene() {
				@Override
				public void switchToHomeScene() {
					switchToScene(HomeScene.this);
				}
				@Override
				public void switchToScene(Scene scene) {
					HomeScene.this.switchToScene(scene);
				}
			});
		});

		histoButton.setOnMouseClicked(event -> {
			try {
				switchToScene(new HistoScene() {
					@Override
					public void switchToHomeScene() {
						switchToScene(HomeScene.this);
					}
				});
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		optionButton.setOnMouseClicked(event -> {
			switchToScene(new OptionsScene(player) {
				@Override
				protected void switchToHomeScene() {
					switchToScene(HomeScene.this);
					
				}
			});
		});

	}
	
	public abstract void switchToScene(Scene scene);
	
	
}
