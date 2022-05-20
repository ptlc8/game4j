package fr.kmmad.game4j.javafx;

import java.sql.SQLException;

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

	public HistoScene() throws SQLException {
		super(new VBox(), 1000, 700);
		
		ImageView homeButtonView = new ImageView(Main.homeImage);
		homeButtonView.setPickOnBounds(true);
		
		Text histoTitle = new Text("History");
		histoTitle.setFont(new Font(50));
		
		VBox scrollVBox = new VBox();
		ScrollPane histoScroll = new ScrollPane();
		histoScroll.setContent(scrollVBox);
		
		Game4j game4j = new Game4j();
		for(Game game : game4j.getHistory()) {
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
			Text movesText = new Text(10+""/*game.getPath().size()*/);
			Text energyText = new Text(game.getPlayer().getEnergy()+"");
			ImageView replayButtonView = new ImageView(Main.replayImage);
			replayButtonView.setPickOnBounds(true);
			replayButtonView.setOnMouseClicked(event ->{
				switchToScene(new GameScene(game) {
					@Override
					public void switchToHomeScene() {
						HistoScene.this.switchToHomeScene();
					}
				});
			});
			gameHBox.getChildren().add(gameText);
			gameHBox.getChildren().add(movesText);
			gameHBox.getChildren().add(energyText);
			gameHBox.getChildren().add(replayButtonView);
			scrollVBox.getChildren().add(gameHBox);
		}
		
		VBox menuHisto = new VBox();
		menuHisto.getChildren().add(homeButtonView);
		menuHisto.getChildren().add(histoTitle);
		menuHisto.getChildren().add(histoScroll);
		menuHisto.setId("histo");
		setRoot(menuHisto);
		
		getStylesheets().add("assets/histo.css");
		
		homeButtonView.setOnMouseClicked(event -> {
			switchToHomeScene();
		});
	}
	public abstract void switchToScene(Scene scene);
	
	protected abstract void switchToHomeScene();
}
