package com.darkhills.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.darkhills.gameobjects.ProjectileHandler;
import com.darkhills.gameworld.GameRenderer;
import com.darkhills.gameworld.GameWorld;
import com.darkhills.taphelpers.InputHandler;

public class GameScreen implements Screen {
    
    private GameWorld world;
    private GameRenderer renderer;
    private float runTime = 0;
    
 public GameScreen() {
        
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();    
        float gameWidth = 204;
        float gameHeight = 136;
        
        int midPointX = (int) (gameWidth / 2);
        

        world = new GameWorld(midPointX);
        ProjectileHandler pj = world.getProj();
        Gdx.input.setInputProcessor(new InputHandler(world, pj, screenWidth / gameWidth, screenHeight / gameHeight));
        renderer = new GameRenderer(world, midPointX);
   
    }

    @Override
    public void render(float delta) {
    	runTime += delta;
        world.update(delta);
        renderer.render(delta, runTime);
    }

    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void show() {
   
    }

    @Override
    public void hide() {
            
    }

    @Override
    public void pause() {
               
    }

    @Override
    public void resume() {
              
    }

    @Override
    public void dispose() {
        // Leave blank
    }

}