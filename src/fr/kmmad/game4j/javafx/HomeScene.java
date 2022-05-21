package fr.kmmad.game4j.javafx;

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
		
		Text titleHome = new Text("GAME4J");
		titleHome.setFont(new Font(70));

		Button playButton = new Button("Play");
		playButton.getStyleClass().add("button");
		playButton.setOnAction(event -> {
			switchToScene(new LevelScene() {
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

		Button saveButton = new Button("Saves");
		saveButton.getStyleClass().add("button");
		saveButton.setOnAction(event -> {
			switchToScene(new SaveScene() {
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

		Button histoButton = new Button("History");
		histoButton.getStyleClass().add("button");
		histoButton.setOnAction(event -> {
			switchToScene(new HistoScene() {
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

		Button optionButton = new Button("Options");
		optionButton.getStyleClass().add("button");
		optionButton.setOnAction(event -> {
			switchToScene(new OptionsScene(player) {
				@Override
				protected void switchToHomeScene() {
					switchToScene(HomeScene.this);
				}
			});
		});

		GridPane gridHome = new GridPane();
		gridHome.setId("gridHome");
		gridHome.setVgap(50);

		gridHome.add(titleHome, 0, 0);
		gridHome.add(playButton, 0, 1);
		gridHome.add(saveButton, 0, 2);
		gridHome.add(histoButton, 0, 3);
		gridHome.add(optionButton, 0, 4);
		setRoot(gridHome);

		getStylesheets().add("assets/home.css");
		
	}
	
	public abstract void switchToScene(Scene scene);
	
}
