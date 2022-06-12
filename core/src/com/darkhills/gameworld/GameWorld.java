package com.darkhills.gameworld;

import com.darkhills.gameobjects.ProjectileHandler;
import com.darkhills.taphelpers.AssetLoader;

public class GameWorld {
	
	private ProjectileHandler projH;
	private int score;
	private float runTime = 0;
	
	private GameState currentState;
	
	public enum GameState {

	   MENU, TUTORIAL, TUTORIAL2, READY, RUNNING, GAMEOVER, HIGHSCORE

	}

	public GameWorld(int midPointX) {
		projH = new ProjectileHandler(this);
		score = 0;
		currentState = GameState.MENU;
	}

    public void updateRunning(float delta) {
        projH.update(delta);
        if(projH.collides()) 
        	endGame();
        	
    }
    
    public void endGame() {
    	AssetLoader.playTheme.stop();
    	projH.stop();
    	currentState = GameState.GAMEOVER;;
    	
    	 if (score > AssetLoader.getHighScore()) {
             AssetLoader.setHighScore(score);
             //currentState = GameState.HIGHSCORE;
         }
    }
    
    public void update(float delta) {

    	//runTime += delta;

        switch (currentState) {
        case READY:
        case MENU:
            updateReady(delta);
            break;

        case RUNNING:
            updateRunning(delta);
            break;
        default:
            break;
        }

    }
    
    private void updateReady(float delta) {
    	//MENU ANIMATIONS
        //projH.updateReady(delta);
    }
    
    public void restart() {
    	AssetLoader.playTheme.play();
        currentState = GameState.READY;
        score = 0;
        projH.onRestart();
        currentState = GameState.READY;
    }
    
    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public void start() {
    	AssetLoader.playTheme.play();
        currentState = GameState.RUNNING;
    }
    
    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }
    
    public boolean isMenu() {
    	return currentState == GameState.MENU;
    }
    
    public ProjectileHandler getProj() {
    	return projH;
    }
    
    public int getScore() {
        return score;
    }
    
    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public void addScore(int increment) {
        score += increment;
    }

	public void ready() {
		currentState = GameState.READY;
		
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

	public boolean isTutorial() {
		return currentState == GameState.TUTORIAL;
	}

	public boolean isTutorial2() {
		return currentState == GameState.TUTORIAL2;
	}

	public void nextTut() {
		currentState = GameState.TUTORIAL2;
	}

	public void tut() {
		currentState = GameState.TUTORIAL;
	}
    
}
