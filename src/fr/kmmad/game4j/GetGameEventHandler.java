package fr.kmmad.game4j;

import fr.kmmad.game4j.Cell.Type;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GetGameEventHandler implements EventHandler<Event>{
	private final Game game;
	private final GridPane grid;
	private final Text energy;
	private final Button previous;

    public GetGameEventHandler(Game game_in, GridPane grid_in, Text power, Button avant){
        this.game = game_in;
        this.grid = grid_in; 
        this.energy = power;
        this.previous = avant;
    }
    
    public void handleKeyEvent(KeyEvent keyEvent) {
    	switch (keyEvent.getCode()) {
    	case LEFT:
    		game.move(Direction.WEST);
    		System.out.println("gauche");
			break;
    		
    	case UP:
    		game.move(Direction.NORTH);
    		System.out.println("haut");
			break;
    		
    	case RIGHT:
    		game.move(Direction.EAST);
    		System.out.println("droite");
			break;
    		
    	case DOWN:
    		game.move(Direction.SOUTH);
    		System.out.println("bas");
			break;
    	        
    	default: 
    	}

		energy.setText("Energy = "+ game.getPlayer().getEnergy());
    	int s = 80;
		for(int i = 0; i<10; i++) {
			for(int j = 0; j<10; j++) {
				Rectangle r = new Rectangle(s, s, s, s);
				if (game.getMap().getCell(i,j).getType() == Type.BONUS)
					r.setFill(Color.GREEN);
				else if (game.getMap().getCell(i,j).getType() == Type.OBSTACLE)
					r.setFill(Color.RED);
				else if (game.getPlayer().getCell().getCoordX() == i && game.getPlayer().getCell().getCoordY() == j)
					r.setFill(Color.YELLOW);
				else r.setFill(Color.BLACK);
				this.grid.add(r, j, i+1);
			}
		}
    }
 
    public void handleMouseEvent(MouseEvent mouseEvent) {
    	previous.setOnMouseClicked(e ->{
    		game.cancelMove();
    		System.out.println("previous !!");
    	});
    }
    
    @Override
    public void handle(Event event) {
    	if(event instanceof KeyEvent){
    		handleKeyEvent((KeyEvent)event);
    	}
    	else if(event instanceof MouseEvent) {
    		handleMouseEvent((MouseEvent)event);
    	}
    	
    }
    	
}


