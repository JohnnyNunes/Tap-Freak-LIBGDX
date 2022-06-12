package com.darkhills.gameworld;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.darkhills.TweenAccessors.Value;
import com.darkhills.TweenAccessors.ValueAccessor;
import com.darkhills.gameobjects.Projectile;
import com.darkhills.gameobjects.ProjectileHandler;
import com.darkhills.taphelpers.AssetLoader;
import com.darkhills.taphelpers.InputHandler;
import com.darkhills.ui.SimpleButton;

public class GameRenderer {

private GameWorld myWorld;
private OrthographicCamera cam;
private ShapeRenderer shapeRenderer;
private SpriteBatch batcher;
private int midPointX;

private TextureRegion line, ball, bomb, bg;
private Texture continue1;

private ProjectileHandler proj;
private Projectile[] projs;

// Tween stuff
private TweenManager manager;
private Value alpha = new Value();

// Buttons
private List<SimpleButton> menuButtons;
    
    public GameRenderer(GameWorld world, int midPointX) {
        myWorld = world;
        
        this.midPointX = midPointX;
        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();
        
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 204, 136);
        
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        
        initGameObjects();
        initGameAssets();
        setupTweens();
    }
    
    private void setupTweens() {
    	Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad)
                .start(manager);
	}

	public void render(float delta, float runTime) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        batcher.begin();
        
        batcher.disableBlending();
        batcher.draw(bg, 0, 0, 204, 136);   
        batcher.enableBlending();
        
        batcher.draw(line, midPointX - 13, 0, 26, 136);
        
        if (myWorld.isRunning()) {
        	drawBalls();
        	//testHitbox();
            drawScore();
        } else if (myWorld.isTutorial()) { 
        	batcher.draw(AssetLoader.tut1,0,0,204,136);
        } else if (myWorld.isTutorial2()) { 
        	batcher.draw(AssetLoader.tut2,0,0,204,136);
        } else if (myWorld.isReady()) {           
            drawScore();
            batcher.draw(continue1,0,0,204,136);
            //testHitbox();
        } else if (myWorld.isMenu()) {
            drawMenuUI();
        } else if (myWorld.isGameOver()) {
        	drawBalls();
            drawScore();
            drawGameover();
        } else if (myWorld.isHighScore()) {
        	drawBalls();
            drawScore();
        }

        batcher.end();
        drawTransition(delta);
    }
	
	private void testHitbox() {
		batcher.end();
		Rectangle a = projs[0].getHitbox();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.rect(a.x, a.y, a.width, a.height);
		shapeRenderer.end();
		batcher.begin();
	}
	
	private void drawGameover() {
		batcher.draw(AssetLoader.gameoverMenu, 0, 0, 204, 136);
		AssetLoader.font.draw(batcher, "" + myWorld.getScore(), 94,65);
		AssetLoader.font.draw(batcher, "" + AssetLoader.getHighScore(), 88,49);
	}
	
	private void drawMenuUI() {
        batcher.draw(AssetLoader.menuScreen, 0, 0, 204, 136);

        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }

    }

    private void drawScore() {
        int length = ("" + myWorld.getScore()).length();
        AssetLoader.font.draw(batcher, "" + myWorld.getScore(), 1, 136);
    }
    
    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }
    
    private void drawBalls() {
    	for(Projectile p : projs) {
    		if(!p.isBomb())
    		batcher.draw(ball, p.getX(), p.getY(), p.getWidth(), p.getWidth());
    		else batcher.draw(bomb, p.getX(), p.getY(), p.getWidth(), p.getWidth());
		}
	}

	private void initGameObjects() {
    	proj = myWorld.getProj();
    	projs = proj.getProjs();
    }
    
    private void initGameAssets() {
    	line = AssetLoader.line;
    	ball = AssetLoader.ball;
    	bomb = AssetLoader.bomb;
    	bg = AssetLoader.bg;
    	continue1 = AssetLoader.continue1;
    }

}
