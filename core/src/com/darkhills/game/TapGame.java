package com.darkhills.game;

import com.badlogic.gdx.Game;
import com.darkhills.screens.SplashScreen;
import com.darkhills.taphelpers.AssetLoader;

public class TapGame extends Game {
	    
	@Override
    public void create() {
        AssetLoader.load();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }

}
