package fr.kmmad.game4j.javafx;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class ReplayScene extends Scene{

	public ReplayScene() {
		super(new VBox(), 1000, 700);

		ImageView homeButtonView = new ImageView(Main.homeImage);
		homeButtonView.setPickOnBounds(true);
		
		Text replayTitle = new Text("Replay");
		replayTitle.setFont(new Font(50));
		
		VBox menuReplay = new VBox();
		menuReplay.getChildren().add(homeButtonView);
		menuReplay.getChildren().add(replayTitle);
		setRoot(menuReplay);
		
		homeButtonView.setOnMouseClicked(event -> {
			switchToHomeScene();
		});
	}

	protected abstract void switchToHomeScene();

}
