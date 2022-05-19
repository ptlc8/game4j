package fr.kmmad.game4j.javafx;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class OptionsScene extends Scene{

	public OptionsScene(MediaPlayer player) {
		super(new VBox(), 1000, 700);
		
		ImageView homeButtonView = new ImageView(Main.homeImage);
		homeButtonView.setPickOnBounds(true);
		
		Text optionTitle = new Text("OPTIONS");
		optionTitle.setFont(new Font(50));
		
		Text soundTitle = new Text("Sound");
		soundTitle.setFont(new Font(50));
		
		Text musicTitle = new Text("Music");
		musicTitle.setFont(new Font(50));
		
		Slider musicSlider = new Slider();
		musicSlider.setMin(0);
		musicSlider.setMax(1);
		musicSlider.setShowTickLabels(false);
		musicSlider.setValue(player.getVolume());
		
		musicSlider.setOnMouseDragged(event -> {
			player.setVolume(musicSlider.getValue());
		});
		
		Text effectTitle = new Text("Effects");
		effectTitle.setFont(new Font(50));
		
		Slider effectSlider = new Slider();
		effectSlider.setMin(0);
		effectSlider.setMax(100);
		effectSlider.setShowTickLabels(false);
		
		Text clearHisto = new Text("Clear historique");
		clearHisto.setFont(new Font(50));
		
		
		
		VBox optionVBox = new VBox();
		optionVBox.getChildren().add(homeButtonView);
		optionVBox.getChildren().add(optionTitle);
		optionVBox.getChildren().add(soundTitle);
		optionVBox.getChildren().add(musicTitle);
		optionVBox.getChildren().add(musicSlider);
		optionVBox.getChildren().add(effectTitle);
		optionVBox.getChildren().add(effectSlider);
		optionVBox.getChildren().add(clearHisto);
		optionVBox.setAlignment(Pos.CENTER);
		setRoot(optionVBox);
		
		getStylesheets().add("assets/options.css");
		
		//option buttons
		
		homeButtonView.setOnMouseClicked(event -> {
			switchToHomeScene();
		});
		
		clearHisto.setOnMouseClicked(event -> {
			
		});
		
	}

	protected abstract void switchToHomeScene();

}
