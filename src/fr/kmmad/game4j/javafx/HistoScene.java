package fr.kmmad.game4j.javafx;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class HistoScene extends Scene{

	public HistoScene() {
		super(new VBox(), 1000, 700);
		
		ImageView homeButtonView = new ImageView(Main.homeImage);
		homeButtonView.setPickOnBounds(true);
		
		//Histo title
		Text histoTitle = new Text("Historique");
		histoTitle.setFont(new Font(50));
		
		VBox histoMenu = new VBox();
		histoMenu.getChildren().add(homeButtonView);
		histoMenu.getChildren().add(histoTitle);	
		setRoot(histoMenu);
		
		homeButtonView.setOnMouseClicked(event -> {
			switchToHomeScene();
		});	
	}
	protected abstract void switchToHomeScene();
}
