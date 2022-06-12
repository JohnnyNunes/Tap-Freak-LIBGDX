package com.darkhills.taphelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture textures, logoTexture, tut1, tut2, continue1;
    public static TextureRegion line, ball, bomb, logo, playButtonUp, playButtonDown, bg, menuScreen, gameoverMenu, star, powerup;
    public static Music playTheme;
    public static Sound hit, powerupS, explosion;
    
    public static BitmapFont font, shadow;
    
    public static Preferences prefs;

    public static Animation birdAnimation;

    public static void load() {
    	
    	continue1 = new Texture(Gdx.files.internal("data/continue1.png"));
    	tut1 = new Texture(Gdx.files.internal("data/tut1.png"));
    	tut2 = new Texture(Gdx.files.internal("data/tut2.png"));
    	
    	hit = Gdx.audio.newSound(Gdx.files.internal("data/hit.wav"));
    	powerupS = Gdx.audio.newSound(Gdx.files.internal("data/powerup.wav"));
    	explosion = Gdx.audio.newSound(Gdx.files.internal("data/explosion.wav"));
    	
    	playTheme = Gdx.audio.newMusic(Gdx.files.internal("data/playTheme.mp3"));
    	playTheme.setLooping(true);
    	playTheme.setVolume(0.3f);
    	  	
    	logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        //logo = new TextureRegion(logoTexture, 0, 0, 800, 450);
            
        textures = new Texture(Gdx.files.internal("data/textures.png"));
        textures.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        ball = new TextureRegion(textures, 156, 458, 100, 100);
        bomb = new TextureRegion(textures, 426, 458, 100, 100); 
        line = new TextureRegion(textures, 22, 457, 91, 450);
        playButtonUp = new TextureRegion(textures, 464, 1053, 195, 53);
		playButtonDown = new TextureRegion(textures, 464, 1126, 195, 53);
		bg = new TextureRegion(textures, 0, 0, 800, 450);
		menuScreen = new TextureRegion(textures, 156, 581, 800, 450);
		gameoverMenu = new TextureRegion(textures, 850, 0, 800, 450);

        font = new BitmapFont(Gdx.files.internal("data/tapfont.fnt"));
        font.getData().setScale(.25f, .25f);

        prefs = Gdx.app.getPreferences("TapFreak");

        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }

    }
    
    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void dispose() {
        textures.dispose();
        logoTexture.dispose();
        font.dispose();
        playTheme.dispose();
    }

}