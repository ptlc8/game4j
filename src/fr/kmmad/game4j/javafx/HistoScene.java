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

public abstract class HistoScene extends Scene{

	public HistoScene(){
		super(new VBox(), 1000, 700);
		
		ImageView homeButtonView = new ImageView(Main.homeImage);
		homeButtonView.setPickOnBounds(true);
		homeButtonView.setId("homeButton");
		
		
		Text histoTitle = new Text("History");
		histoTitle.setFont(new Font(50));
		histoTitle.setId("title");
		
		VBox scrollVBox = new VBox();
		scrollVBox.setId("VBoxScroll");
		ScrollPane histoScroll = new ScrollPane();
		histoScroll.setContent(scrollVBox);
		VBox containScroll = new VBox(histoScroll);
		containScroll.setId("contain");
		
		
		Game4j game4j = new Game4j();
		for(Game game : game4j.getHistory()) {
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
			ImageView replayButtonView = new ImageView(Main.replayImage);
			replayButtonView.setId("replayButton");
			replayButtonView.setPickOnBounds(true);
			replayButtonView.setOnMouseClicked(event ->{
				switchToScene(new GameScene(game) {
					@Override
					public void switchToHomeScene() {
						HistoScene.this.switchToHomeScene();
					}
				});
			});
			gameHBox.getChildren().add(dateText);
			gameHBox.getChildren().add(levelText);
			gameHBox.getChildren().add(movesText);
			gameHBox.getChildren().add(energyText);
			gameHBox.getChildren().add(replayButtonView);
			gameHBox.setId("gameHBox");
			scrollVBox.getChildren().add(gameHBox);
			scrollVBox.setId("scroll");
		}
		
		VBox menuHisto = new VBox();
		menuHisto.getChildren().add(homeButtonView);
		menuHisto.getChildren().add(histoTitle);
		menuHisto.getChildren().add(containScroll);
		menuHisto.setId("root");
		setRoot(menuHisto);
		
		getStylesheets().add("assets/save.css");
		
		homeButtonView.setOnMouseClicked(event -> {
			switchToHomeScene();
		});
	}
	public abstract void switchToScene(Scene scene);
	
	protected abstract void switchToHomeScene();
}
