package fr.kmmad.game4j.javafx;

import fr.kmmad.game4j.Game4j;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

public abstract class OptionsScene extends Scene{

	public OptionsScene(MediaPlayer player) {
		super(new VBox(), 1000, 700);
		
		Game4j game4j = new Game4j();
		
		ImageView homeButtonView = new ImageView(Main.homeImage);
		homeButtonView.setPickOnBounds(true);
		homeButtonView.setId("homeButton");
		
		Label optionTitle = new Label("Options");
		optionTitle.setFont(new Font(50));
		optionTitle.setId("titleOption");
		
		Label musicTitle = new Label("Music");
		musicTitle.setFont(new Font(50));
		musicTitle.getStyleClass().add("title");
		
		Slider musicSlider = new Slider();
		musicSlider.setMin(0);
		musicSlider.setMax(1);
		musicSlider.setShowTickLabels(false);
		musicSlider.setValue(player.getVolume());
		musicSlider.getStyleClass().add("slider");
		
		musicSlider.valueProperty().addListener(event -> {
			player.setVolume(musicSlider.getValue());
		});
		
		VBox musicVBox = new VBox();
		musicVBox.getChildren().add(musicTitle);
		musicVBox.getChildren().add(musicSlider);
		musicVBox.getStyleClass().add("boxSlider");
		
		Label effectTitle = new Label("Effects");
		effectTitle.setFont(new Font(50));
		effectTitle.getStyleClass().add("title");
		
		Slider effectSlider = new Slider();
		effectSlider.setMin(0);
		effectSlider.setMax(100);
		effectSlider.setShowTickLabels(false);
		effectSlider.getStyleClass().add("slider");
		
		VBox effectVBox = new VBox();
		musicVBox.getChildren().add(effectTitle);
		musicVBox.getChildren().add(effectSlider);
		musicVBox.getStyleClass().add("boxSlider");
		
		Button clearHisto = new Button("Clear History");
		clearHisto.getStyleClass().add("button");
		
		Button clearSave = new Button("Clear Saves");
		clearSave.getStyleClass().add("button");
		
		VBox buttonVBox = new VBox();
		buttonVBox.getChildren().add(clearHisto);
		buttonVBox.getChildren().add(clearSave);
		buttonVBox.setId("boxButton");
		
		Label done = new Label();
		done.setId("done");
		Button ok = new Button("OK");
		ok.setId("ok");
		VBox okVBox = new VBox();
		okVBox.getChildren().add(ok);
		okVBox.setId("okVBox");
		
		VBox alert = new VBox();
		alert.getChildren().add(done);
		alert.getChildren().add(okVBox);
		alert.setVisible(false);
		alert.setId("alert");
		
		ok.setOnAction(event ->{
			alert.setVisible(false);
		});
		
		clearHisto.setOnAction(event -> {
			done.setText("History cleared");
			alert.setVisible(true);
		});
		
		clearSave.setOnAction(event -> {
			done.setText("Saves cleared");
			alert.setVisible(true);
		});
		
		VBox optionVBox = new VBox();
		optionVBox.getChildren().add(homeButtonView);
		optionVBox.getChildren().add(optionTitle);
		optionVBox.getChildren().add(musicVBox);
		optionVBox.getChildren().add(effectVBox);
		optionVBox.getChildren().add(buttonVBox);
		optionVBox.setAlignment(Pos.CENTER);
		optionVBox.setId("option");
		
		StackPane buttonStack = new StackPane();
		buttonStack.getChildren().add(optionVBox);
		buttonStack.getChildren().add(alert);
		setRoot(buttonStack);
		
		getStylesheets().add("assets/options.css");
		
		//option buttons
		
		homeButtonView.setOnMouseClicked(event -> {
			switchToHomeScene();
		});
		
		clearHisto.setOnMouseClicked(event -> {
			game4j.deleteHistory();
		});
		
		clearSave.setOnMouseClicked(event -> {
			game4j.deleteSave();
		});
		
	}

	protected abstract void switchToHomeScene();

}
