package com.darkhills.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.darkhills.game.AdsController;
import com.darkhills.game.TapGame;

//clicking starting position gives point after restart
//make bomb objects

public class DesktopLauncher {
	
	private AdsController adsController;
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tap Freak";
	    config.width = 800;
	    config.height = 480;
		new LwjglApplication(new TapGame(), config);
	}
}