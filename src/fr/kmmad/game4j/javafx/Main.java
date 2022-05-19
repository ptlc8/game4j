package fr.kmmad.game4j.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.*;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {
	
	public static void main(String[] args) throws IOException {
		Application.launch(args);
	}
	
	public final  static Image homeImage = (loadImage("/assets/homeButton.png", 50, 50));
	public final  static Image cancelImage = (loadImage("/assets/cancelButton.png", 50, 50));
	public final  static Image saveImage = (loadImage("/assets/saveButton.png", 50, 50));
	public final  static Image energyImage = (loadImage("/assets/boltImage.png", 50, 50));
	
	@Override
	public void start(Stage stage) throws Exception {
		
		String ssoundFrigidaire = new File("bin/assets/frigidaire.mp3").toURI().toString();
		Media soundFrigidaire = new Media(ssoundFrigidaire);
		MediaPlayer playerFrigidaire = new MediaPlayer(soundFrigidaire);
		playerFrigidaire.setCycleCount(MediaPlayer.INDEFINITE);
		playerFrigidaire.play();
		
		
		stage.setScene(new HomeScene(playerFrigidaire) {
			@Override
			public void switchToScene(Scene scene) {
				stage.setScene(scene);
			}
		});
		
		Image icon = new Image("assets/icon.png");
		stage.getIcons().add(icon);
		stage.setTitle("GAME4J");
		stage.setResizable(false);
		stage.show();
		
	}
	
	public static Image loadImage(String imageString, int w, int h) {
		InputStream stream = Main.class.getResourceAsStream(imageString);
		return new Image(stream, w,h,true, true);
	}
	
}
