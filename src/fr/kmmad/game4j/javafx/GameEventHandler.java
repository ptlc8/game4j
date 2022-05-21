package fr.kmmad.game4j.javafx;

import fr.kmmad.game4j.*;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public abstract class GameEventHandler{
	private final Game game;

    public GameEventHandler(Game game_in){
        this.game = game_in;
    }
    
    public void move(KeyEvent keyEvent) {
    	if (game.isFinished())
    		return;
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
    	if (game.isFinished())
    		return;
    	game.cancelMove();
    	refresh(game);
    }
    
    public abstract void refresh(Game game);
}


