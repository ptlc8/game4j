package fr.kmmad.game4j.javafx;

import fr.kmmad.game4j.Game;
import fr.kmmad.game4j.Game4j;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
			Label dateText = new Label(game.getDate()+"");
			dateText.setId("date");
			dateText.getStyleClass().add("elements");
			Label levelText = new Label();
			levelText.setId("level");
			levelText.getStyleClass().add("elements");
			if(game.getMap().getSize()<10) {
				levelText.setText("Beginner");
			}else if(game.getMap().getSize()>=12){
				levelText.setText("Expert");
			}else{
				levelText.setText("Normal");
			}
			Label movesText = new Label("Moves : "+game.getPath().size());
			movesText.setId("moves");
			movesText.getStyleClass().add("elements");
			Label energyText = new Label("Energy : "+game.getPlayer().getEnergy());
			energyText.setId("energy");
			energyText.getStyleClass().add("elements");
			ImageView replayButtonView = new ImageView(Main.replayImage);
			replayButtonView.setId("replayButton");
			replayButtonView.getStyleClass().add("elements");
			replayButtonView.setPickOnBounds(true);
			replayButtonView.setOnMouseClicked(event ->{
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
			gameHBox.getChildren().add(replayButtonView);
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
