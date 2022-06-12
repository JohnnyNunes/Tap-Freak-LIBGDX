package com.darkhills.taphelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.darkhills.gameobjects.ProjectileHandler;
import com.darkhills.gameworld.GameWorld;
import com.darkhills.taphelpers.AssetLoader;
import com.darkhills.ui.SimpleButton;

public class InputHandler implements InputProcessor {
	
	private ProjectileHandler pj;
	private float scaleFactorX;
	private float scaleFactorY;
	private GameWorld gw;
	
	private List<SimpleButton> menuButtons;

    private SimpleButton playButton;

    public InputHandler(GameWorld gw, ProjectileHandler pj, float scaleX, float scaleY) {
    	scaleFactorX=scaleX;
    	scaleFactorY=scaleY;
    	this.pj=pj;
    	this.gw = gw;
    	
    	menuButtons = new ArrayList<SimpleButton>();
        playButton = new SimpleButton(102 - 20, 68 - 35, 40, 15, AssetLoader.playButtonUp, AssetLoader.playButtonDown);
        menuButtons.add(playButton);
	}

	@Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {  //Y coordinates are inverse
		int scaledX = scaleX(screenX);
		int scaledY = 136 - scaleY(screenY);
		
		if (gw.isMenu()) 
            playButton.isTouchDown(scaledX, scaledY);
		else if (gw.isTutorial())
			gw.nextTut();
		else if (gw.isTutorial2())
			gw.start();
        else if (gw.isReady()) 
        	gw.start();
        else if (gw.isRunning())
        	pj.touchObject(scaledX, scaledY);		
        else if (gw.isGameOver() || gw.isHighScore())
        	gw.restart();
		
		return true;
    }

	@Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    	int scaledX = scaleX(screenX);
		int scaledY = 136 - scaleY(screenY);

        if (gw.isMenu()) 
            if (playButton.isTouchUp(scaledX, scaledY)) {
                gw.tut();
                return true;
            }
        else if (gw.isReady()) 
        	gw.start();
        else if (gw.isRunning())
        	pj.touchObject(scaledX, scaledY);		
        else if (gw.isGameOver() || gw.isHighScore())
        	gw.restart();

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    
    private int scaleX(int screenX) {
		return (int) (screenX / scaleFactorX);
	}

	private int scaleY(int screenY) {
		return (int) (screenY / scaleFactorY);
	}
	
	public List<SimpleButton> getMenuButtons() {
        return menuButtons;
    }

}