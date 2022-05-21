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
		homeButtonView.setId("homeButton");
		
		Text saveTitle = new Text("Saves");
		saveTitle.setFont(new Font(50));
		saveTitle.setId("title");
		
		VBox scrollVBox = new VBox();
		scrollVBox.setId("VBoxScroll");
		ScrollPane saveScroll = new ScrollPane();
		saveScroll.setContent(scrollVBox);
		VBox containScroll = new VBox(saveScroll);
		containScroll.setId("contain");
		
		
		Game4j game4j = new Game4j();
		for(Game game : game4j.getAllSaves()) {
			HBox gameHBox = new HBox();
			Text stateText = new Text();
			stateText.setId("state");
			stateText.getStyleClass().add("elements");
			Text dateText = new Text(game.getDate()+"");
			dateText.setId("date");
			dateText.getStyleClass().add("elements");
			if(game.isVictory()) {
				stateText.setText("Victory");
				gameHBox.getChildren().add(stateText);
			}else if(game.isDefeat()) {
				stateText.setText("Defeat");
				gameHBox.getChildren().add(stateText);
			}else {
				stateText.setText("Ongoing");
				gameHBox.getChildren().add(stateText);
			}
			Text levelText = new Text();
			levelText.setId("level");
			levelText.getStyleClass().add("elements");
			if(game.getMap().getSize()<10) {
				levelText.setText("Beginner");
			}else if(game.getMap().getSize()>=12){
				levelText.setText("Expert");
			}else{
				levelText.setText("Normal");
			}
			Text movesText = new Text("Moves : "+game.getPath().size());
			movesText.setId("moves");
			movesText.getStyleClass().add("elements");
			Text energyText = new Text("Energy : "+game.getPlayer().getEnergy());
			energyText.setId("energy");
			energyText.getStyleClass().add("elements");
			ImageView saveButtonView = new ImageView(Main.replayImage);
			saveButtonView.setId("replayButton");
			saveButtonView.getStyleClass().add("elements");
			saveButtonView.setPickOnBounds(true);
			saveButtonView.setOnMouseClicked(event ->{
				switchToScene(new GameScene(game) {
					@Override
					public void switchToHomeScene() {
						SaveScene.this.switchToHomeScene();
					}
				});
			});
			gameHBox.getChildren().add(dateText);
			gameHBox.getChildren().add(levelText);
			gameHBox.getChildren().add(movesText);
			gameHBox.getChildren().add(energyText);
			gameHBox.getChildren().add(saveButtonView);
			gameHBox.setId("gameHBox");
			scrollVBox.getChildren().add(gameHBox);
			scrollVBox.setId("scroll");
		}
		
		VBox menuSave = new VBox();
		menuSave.getChildren().add(homeButtonView);
		menuSave.getChildren().add(saveTitle);
		menuSave.getChildren().add(containScroll);
		menuSave.setId("root");
		setRoot(menuSave);
		
		getStylesheets().add("assets/save.css");
		
		homeButtonView.setOnMouseClicked(event -> {
			switchToHomeScene();
		});
	}
	public abstract void switchToScene(Scene scene);

	protected abstract void switchToHomeScene();

}
