package fr.kmmad.game4j;

import fr.kmmad.game4j.Cell.Type;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public abstract class GameEventHandler{
	private final Game game;

    public GameEventHandler(Game game_in){
        this.game = game_in;
    }
    
    public void move(KeyEvent keyEvent) {
    	switch (keyEvent.getCode()) {
    	case Q, LEFT:
    		game.move(Direction.WEST);
    		System.out.println("gauche");
			break;
    		
    	case Z, UP:
    		game.move(Direction.NORTH);
    		System.out.println("haut");
			break;
    		
    	case D, RIGHT:
    		game.move(Direction.EAST);
    		System.out.println("droite");
			break;
    		
    	case S, DOWN:
    		game.move(Direction.SOUTH);
    		System.out.println("bas");
			break;
    	        
    	default: 
    	}
    	refresh(game);
    }
    
    
 
    public void cancel(MouseEvent event) {
    	game.cancelMove();
    	refresh(game);
    	
    }
    
    public abstract void refresh(Game game);
}


