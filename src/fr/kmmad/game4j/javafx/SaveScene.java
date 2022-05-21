package fr.kmmad.game4j.javafx;

import fr.kmmad.game4j.Game;
import fr.kmmad.game4j.Game4j;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class SaveScene extends Scene{
	
	

	public SaveScene() {
		super(new VBox(), 1000, 700);

		ImageView homeButtonView = new ImageView(Main.homeImage);
		homeButtonView.setPickOnBounds(true);
		
		Text saveTitle = new Text("Saves");
		saveTitle.setFont(new Font(50));
		
		VBox scrollVBox = new VBox();
		ScrollPane saveScroll = new ScrollPane();
		saveScroll.setContent(scrollVBox);
		
		Game4j game4j = new Game4j();
		for(Game game : game4j.getAllSaves()) {
			HBox gameHBox = new HBox();
			Text gameText = new Text(game.getDate()+"");
			if(game.isVictory()) {
				Text stateText = new Text("Victory");
				gameHBox.getChildren().add(stateText);
			}else if(game.isDefeat()) {
				Text stateText = new Text("Defeat");
				gameHBox.getChildren().add(stateText);
			}else {
				Text stateText = new Text("Ongoing");
				gameHBox.getChildren().add(stateText);
			}
			Text levelText = new Text();
			if(game.getMap().getSize()<10) {
				levelText.setText("Beginner");
			}else if(game.getMap().getSize()>=12){
				levelText.setText("Expert");
			}else{
				levelText.setText("Normal");
			}
			Text movesText = new Text(game.getPath().size()+"");
			Text energyText = new Text(game.getPlayer().getEnergy()+"");
			ImageView saveButtonView = new ImageView(Main.replayImage);
			saveButtonView.setPickOnBounds(true);
			saveButtonView.setOnMouseClicked(event ->{
				switchToScene(new GameScene(game) {
					@Override
					public void switchToHomeScene() {
						SaveScene.this.switchToHomeScene();
					}
				});
			});
			gameHBox.getChildren().add(gameText);
			gameHBox.getChildren().add(levelText);
			gameHBox.getChildren().add(movesText);
			gameHBox.getChildren().add(energyText);
			gameHBox.getChildren().add(saveButtonView);
			scrollVBox.getChildren().add(gameHBox);
		}
		
		VBox menuSave = new VBox();
		menuSave.getChildren().add(homeButtonView);
		menuSave.getChildren().add(saveTitle);
		menuSave.getChildren().add(saveScroll);
		menuSave.setId("save");
		setRoot(menuSave);
		
		getStylesheets().add("assets/save.css");
		
		homeButtonView.setOnMouseClicked(event -> {
			switchToHomeScene();
		});
	}
	public abstract void switchToScene(Scene scene);

	protected abstract void switchToHomeScene();

}
