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
		
		Game4j game4j = new Game4j();
		
		ImageView homeButtonView = new ImageView(Main.homeImage);
		homeButtonView.setPickOnBounds(true);
		
		//Histo title
		Text histoTitle = new Text("Historique");
		histoTitle.setFont(new Font(50));
		
		VBox scrollVBox = new VBox();
		ScrollPane histoScroll = new ScrollPane();
		histoScroll.setContent(scrollVBox);
		
		for(Game game : game4j.getHistory()) {
			HBox gameHBox = new HBox();
			Text gameText = new Text(game.getDate()+"");
			if(game.isVictory()) {
				Text stateText = new Text("Victory");
				gameHBox.getChildren().add(stateText);
			}else if(game.isDefeat()) {
				Text stateText = new Text("Defeat");
				gameHBox.getChildren().add(stateText);
			}
			scrollVBox.getChildren().add(gameHBox);
		}
		
		VBox histoMenu = new VBox();
		histoMenu.getChildren().add(homeButtonView);
		histoMenu.getChildren().add(histoTitle);	
		histoMenu.getChildren().add(histoScroll);	
		setRoot(histoMenu);
		
		homeButtonView.setOnMouseClicked(event -> {
			switchToHomeScene();
		});	
	}
	protected abstract void switchToHomeScene();
}
