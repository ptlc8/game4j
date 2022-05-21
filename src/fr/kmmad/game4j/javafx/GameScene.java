package fr.kmmad.game4j.javafx;

import fr.kmmad.game4j.Game;
import fr.kmmad.game4j.Game4j;

import java.util.List;
import java.util.Random;
import fr.kmmad.game4j.Cell;
import fr.kmmad.game4j.Cell.Type;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public abstract class GameScene extends Scene{
	
	private Text energyAmount, cancelAmount, alertText;
	private GridPane gridInGame;
	private boolean showGamePath = true, showShortestPath = true, showEnergyPath = true;
	private HBox buttonsRight;
	private int replayProgress = 0;
	private boolean historied = false;
	private VBox alertBox, replayControlsVBox, pathVBox;
	private Label alertBoucle;
	private Game4j game4j;
	
	public GameScene(Game game) {
		super(new VBox(), 1000, 700);
		
		historied = game.isFinished();
		game4j = new Game4j();
		
		Text inGameText = new Text("Go home !");
		inGameText.setId("inGameText");
		
		ImageView homeButtonView = new ImageView(Main.homeImage);
		homeButtonView.setPickOnBounds(true);
		homeButtonView.setId("homeButton");
		ImageView cancelButtonView = new ImageView(Main.cancelImage);
		cancelButtonView.setPickOnBounds(true);
		cancelButtonView.setId("cancelButton");
		ImageView saveButtonView = new ImageView(Main.saveImage);
		saveButtonView.setPickOnBounds(true);
		saveButtonView.setId("saveButton");
		ImageView energyImageView = new ImageView(Main.energyImage);
		energyImageView.setId("energyImage");
		ImageView cancelImageView = new ImageView(Main.cancelImage);
		cancelImageView.setId("cancelImage");
		ImageView arrowRightImageView = new ImageView(Main.arrowRight);
		arrowRightImageView.setId("arrowRight");
		ImageView arrowLeftImageView = new ImageView(Main.arrowRight);
		arrowLeftImageView.setId("arrowRight");
		
		CheckBox shortestPathCheckBox = new CheckBox("Shortest path");
		shortestPathCheckBox.setSelected(true);
		shortestPathCheckBox.setBackground(new Background(new BackgroundFill(Color.LIME, null, null)));
		shortestPathCheckBox.setOnAction(event -> {
			showShortestPath = shortestPathCheckBox.isSelected();
			refresh(game);
		});
		
		CheckBox gamePathCheckBox = new CheckBox("My path");
		gamePathCheckBox.setSelected(true);
		gamePathCheckBox.setBackground(new Background(new BackgroundFill(Color.CYAN, null, null)));
		gamePathCheckBox.setOnAction(event -> {
			showGamePath = gamePathCheckBox.isSelected();
			refresh(game);
		});
		
		CheckBox energyPathCheckBox = new CheckBox("Energy path");
		energyPathCheckBox.setSelected(true);
		energyPathCheckBox.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
		energyPathCheckBox.setOnAction(event -> {
			showEnergyPath = energyPathCheckBox.isSelected();
			refresh(game);
		});
		
		pathVBox = new VBox(shortestPathCheckBox, energyPathCheckBox, gamePathCheckBox);
		pathVBox.setId("pathVBox");
		
		alertBoucle = new Label("Attention !\nIl y a une boucle !");
		alertBoucle.setId("alertBoucle");
		alertBoucle.setVisible(false);
		
		//Vertical box left part
		VBox leftPart = new VBox();
		leftPart.getChildren().add(homeButtonView);
		leftPart.getChildren().add(pathVBox);
		leftPart.getChildren().add(alertBoucle);
		leftPart.setId("leftPart");
		
		//Horizontal box buttons right
		buttonsRight = new HBox();
		buttonsRight.getChildren().add(cancelButtonView);
		buttonsRight.getChildren().add(saveButtonView);

		//Text Energy
		energyAmount = new Text();
		energyAmount.setFont(new Font(25));
		
		//Horizontal box energy
		HBox energyHBox = new HBox();
		energyHBox.getChildren().add(energyImageView);
		energyHBox.getChildren().add(energyAmount);

		
		//Text Cancel
		cancelAmount = new Text();
		cancelAmount.setFont(new Font(25));
		
		//Horizontal box Cancel
		HBox cancelHBox = new HBox();
		cancelHBox.getChildren().add(cancelImageView);
		cancelHBox.getChildren().add(cancelAmount);
		
		
		//Vertical box of the Right part in game
		VBox rightPart = new VBox();
		rightPart.getChildren().add(buttonsRight);
		rightPart.getChildren().add(energyHBox);
		rightPart.getChildren().add(cancelHBox);
		rightPart.setId("rightPart");
		
		
		//Grid of the game
		gridInGame = new GridPane();
		gridInGame.setId("gridInGame");
		gridInGame.setAlignment(Pos.CENTER);
		gridInGame.setHgap(1);
		gridInGame.setVgap(1);
		
		// Replay buttons
		ScheduledService<Boolean> replayService = new ScheduledService<Boolean>() {
			@Override
			protected Task<Boolean> createTask() {
				return new Task<Boolean>() {
					@Override
					protected Boolean call() throws Exception {
						if (game.isFinished()) {
							if (replayProgress < game.getPath().size()-1) {
								replayProgress++;
								return true;
							}
							this.cancel();
						}
						return false;
					}
				};
			}
		};
		replayService.setPeriod(Duration.seconds(0.2));
		replayService.setOnSucceeded(e -> {
			if (replayService.getValue())
				refresh(game);
		});
		replayService.start();
		
		Button replayButton = new Button();
		replayButton.setGraphic(new ImageView(Main.replayImage));
		replayButton.setOnAction(event -> {
			if (replayProgress == game.getPath().size()-1)
				replayProgress = 0;
			if (replayService.isRunning())
				replayService.cancel();
			else
				replayService.restart();
		});
		
		Button previousButton = new Button();
		previousButton.setGraphic(new ImageView(Main.arrowLeft));
		previousButton.setOnAction(event -> {
			if (replayProgress > 0)
				replayProgress--;
			refresh(game);
		});
		
		Button nextButton = new Button();
		nextButton.setGraphic(new ImageView(Main.arrowRight));
		nextButton.setOnAction(event -> {
			if (replayProgress < game.getPath().size()-1)
				replayProgress++;
			refresh(game);
		});
		
		MenuButton speedButton = new MenuButton("Vitesse");
		MenuItem slowOption = new MenuItem("Lent");
		slowOption.setOnAction(e -> replayService.setPeriod(Duration.seconds(0.8)));
		speedButton.getItems().add(slowOption);
		MenuItem normalOption = new MenuItem("Normal");
		normalOption.setOnAction(e -> replayService.setPeriod(Duration.seconds(0.2)));
		speedButton.getItems().add(normalOption);
		MenuItem fastOption = new MenuItem("Rapide");
		fastOption.setOnAction(e -> replayService.setPeriod(Duration.seconds(0.05)));
		speedButton.getItems().add(fastOption);

		HBox replayControlsHBox = new HBox();
		replayControlsHBox.getChildren().add(previousButton);
		replayControlsHBox.getChildren().add(replayButton);
		replayControlsHBox.getChildren().add(nextButton);
		replayControlsHBox.setId("replayHBox");
		
		replayControlsVBox = new VBox();
		replayControlsVBox.getChildren().add(speedButton);
		replayControlsVBox.getChildren().add(replayControlsHBox);
		replayControlsVBox.setId("replayVBox");
		
		alertBox = new VBox();
		alertBox.setId("alertBox");
		alertBox.setFillWidth(false);
		alertText = new Text();
		Button alertButton = new Button("OK");
		alertButton.setId("alertButton");
		alertButton.setOnAction(event -> {
			alertBox.setVisible(false);
		});
		alertBox.getChildren().add(alertText);
		alertBox.getChildren().add(alertButton);
		alertBox.setVisible(false);
		
		
		//Pile sur la grille
		StackPane gameStack = new StackPane();
		gameStack.getChildren().add(gridInGame);
		gameStack.getChildren().add(alertBox);
		gameStack.setId("gameStack");
		
		BorderPane parent = new BorderPane();
		parent.setCenter(gameStack);
		parent.setId("inGame");
		parent.setTop(inGameText);
		BorderPane.setAlignment(inGameText, Pos.CENTER);
		parent.setRight(rightPart);
		parent.setLeft(leftPart);
		parent.setBottom(replayControlsVBox);
		
		setRoot(parent);
		
		refresh(game);
		
		GameEventHandler gameEventHandler = new GameEventHandler(game){
			@Override
			public void refresh(Game game) {
				GameScene.this.refresh(game);
			}
		};
		
		getStylesheets().add("assets/inGame.css");
		
		cancelButtonView.setOnMouseClicked(gameEventHandler::cancel);
		setOnKeyPressed(gameEventHandler::move);
		
		//Game Buttons
		
		homeButtonView.setOnMouseClicked(event -> {
			switchToHomeScene();
			replayService.cancel();
		});
		
		saveButtonView.setOnMouseClicked(event -> {
			game4j.addGameSave(game, "uwu"+new Random().nextInt(100));
		});

		parent.requestFocus();
		
	}
	
	public GameScene() {
		this(new Game(10,5,20));
	}
	
	protected abstract void switchToHomeScene();
	
	public void refresh(Game game) {
		energyAmount.setText(""+game.getPlayer().getEnergy());
		cancelAmount.setText(""+ game.getPlayer().getAvailableCancelAmount());
    	gridInGame.getChildren().clear();
		for(int i = 0; i<game.getMap().getSize(); i++) {
			for(int j = 0; j<game.getMap().getSize(); j++) {
				ImageView cellView;
				if (game.getMap().getCell(i,j).getType() == Type.BONUS)
					cellView = new ImageView(Main.carrotOnFieldImage);
				else if (game.getMap().getCell(i,j).getType() == Type.OBSTACLE)
					cellView = new ImageView(Main.cornFieldImage);
				else
					cellView = new ImageView(Main.emptyFieldImage);
				gridInGame.add(cellView, j, i);
			}
		}
		ImageView playerView = new ImageView(Main.rabbitImage);
		ImageView endView = new ImageView(Main.rabbitHouse);
		pathVBox.setVisible(game.isFinished());
		replayControlsVBox.setVisible(game.isFinished());
		List<Cell> loop = game.getLoop();
		if (loop != null) {
			drawPath(loop, Color.RED);
			alertBoucle.setVisible(true);
		}
		if(game.isFinished()) {
			if(!historied) {
				historied = true;
				game4j.addGameHistory(game);
				buttonsRight.setVisible(false);
				if(game.isVictory()) {
					alertText.setText("Well done !");
					alertBox.setVisible(true);
				}else if(game.isDefeat()) {
					alertText.setText("OH noooo ! You loose...");
					alertBox.setVisible(true);
				}
			}
			if (showShortestPath)
				drawPath(game.getMap().shortPath(game.getStartCell(), game.getEndCell()), Color.LIME);
			if (showGamePath)
				drawPath(game.getPath(), Color.CYAN);
			if (showEnergyPath)
				drawPath(game.getMap().shortPathEnergy(game.getStartCell(), game.getEndCell()), Color.YELLOW);
			Cell replayCell = game.getPath().get(replayProgress);
			gridInGame.add(playerView, replayCell.getCoordY(), replayCell.getCoordX());
		} else {
			gridInGame.add(playerView, game.getPlayer().getCell().getCoordY(), game.getPlayer().getCell().getCoordX());
			gridInGame.add(endView, game.getMap().getSize()-1, game.getMap().getSize()-1);
		}
	}
	
	private void drawPath(List<Cell> path, Color color) {
		if (path == null)
			return;
		int thickness = 16;
		for (int i = 0; i < path.size(); i++) {
			Cell cell = path.get(i);
			Rectangle r = new Rectangle(thickness,thickness, color);
			GridPane.setHalignment(r, HPos.CENTER);
			gridInGame.add(r, cell.getCoordY(), cell.getCoordX());
			if (i < path.size()-1) {
				Cell nextCell = path.get(i+1);
				Rectangle rn = new Rectangle(thickness,thickness, color);
				GridPane.setHalignment(rn, nextCell.getCoordY()<cell.getCoordY() ? HPos.LEFT : nextCell.getCoordY()>cell.getCoordY() ? HPos.RIGHT : HPos.CENTER);
				GridPane.setValignment(rn, nextCell.getCoordX()<cell.getCoordX() ? VPos.TOP : nextCell.getCoordX()>cell.getCoordX() ? VPos.BOTTOM : VPos.CENTER);
				gridInGame.add(rn, path.get(i).getCoordY(), path.get(i).getCoordX());
			}
			if (i > 0) {
				Cell previousCell = path.get(i-1);
				Rectangle pn = new Rectangle(thickness,thickness, color);
				GridPane.setHalignment(pn, previousCell.getCoordY()<cell.getCoordY() ? HPos.LEFT : previousCell.getCoordY()>cell.getCoordY() ? HPos.RIGHT : HPos.CENTER);
				GridPane.setValignment(pn, previousCell.getCoordX()<cell.getCoordX() ? VPos.TOP : previousCell.getCoordX()>cell.getCoordX() ? VPos.BOTTOM : VPos.CENTER);
				gridInGame.add(pn, path.get(i).getCoordY(), path.get(i).getCoordX());
			}
		}
	}
	
}
