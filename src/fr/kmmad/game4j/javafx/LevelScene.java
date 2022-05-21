package fr.kmmad.game4j.javafx;

import fr.kmmad.game4j.Game;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class LevelScene extends Scene{

	public LevelScene() {
		super(new VBox(), 1000, 700);
		
		ImageView homeButtonView = new ImageView(Main.homeImage);
		homeButtonView.setPickOnBounds(true);
		homeButtonView.setId("homeButton");
		homeButtonView.setOnMouseClicked(event ->{
			switchToHomeScene();
		});
		
		Text titleLevel = new Text("Chose your level");
		titleLevel.setFont(new Font(70));
		
		Button beginnerButton = new Button("Beginner");
		beginnerButton.getStyleClass().add("button");
		beginnerButton.setOnAction(event -> {
			switchToScene(new GameScene(new Game(8, 8, 20)) {
				@Override
				public void switchToHomeScene() {
					LevelScene.this.switchToHomeScene();
				}
			});
		});
		
		Button normalButton = new Button("Normal");
		normalButton.getStyleClass().add("button");
		normalButton.setOnAction(event -> {
			switchToScene(new GameScene(new Game(10, 5, 30)) {
				@Override
				public void switchToHomeScene() {
					LevelScene.this.switchToHomeScene();
				}
			});
		});
		
		Button expertButton = new Button("Expert");
		expertButton.getStyleClass().add("button");
		expertButton.setOnAction(event -> {
			switchToScene(new GameScene(new Game(12, 3, 40)) {
				@Override
				public void switchToHomeScene() {
					LevelScene.this.switchToHomeScene();
				}
			});
		});
		
		GridPane levelMenu = new GridPane();
		levelMenu.setVgap(50);
		levelMenu.setId("levelMenu");
		levelMenu.add(titleLevel, 0, 0);
		levelMenu.add(beginnerButton, 0, 1);
		levelMenu.add(normalButton, 0, 2);
		levelMenu.add(expertButton, 0, 3);
		setRoot(levelMenu);
		
		getStylesheets().add("assets/level.css");
		
	}
	
	public abstract void switchToScene(Scene scene);
	
	protected abstract void switchToHomeScene();

}
