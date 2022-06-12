package com.darkhills.gameobjects;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.darkhills.gameworld.GameWorld;
import com.darkhills.taphelpers.AssetLoader;

public class ProjectileHandler {
	
	private Projectile[]projs;
	private GameWorld gameworld;	
	private Rectangle line;
	
	private static final int midPointX = 102;
	private static final int PROJECTILE_WIDTH =  20;
	
	public ProjectileHandler(GameWorld gw) {
		gameworld = gw;
		
		
		projs = new Projectile[8];
		//						 (x,y,width,scroll speed, health)
		
		
		projs[0] = new Projectile(-16,10,PROJECTILE_WIDTH,MathUtils.random(10,26),1, true, false);
		projs[1] = new Projectile(-16,40,PROJECTILE_WIDTH,MathUtils.random(10,26),1, true, false);
		projs[2] = new Projectile(-16,70,PROJECTILE_WIDTH,MathUtils.random(10,26),1, true, false);
		projs[3] = new Projectile(-16, 100,PROJECTILE_WIDTH,MathUtils.random(10,26),1, true, false);
		projs[4] = new Projectile(220,10,PROJECTILE_WIDTH,-MathUtils.random(10,26),1, false, false);
		projs[5] = new Projectile(220,40,PROJECTILE_WIDTH,-MathUtils.random(10,26),1, false, false);
		projs[6] = new Projectile(220,70,PROJECTILE_WIDTH,-MathUtils.random(10,26),1, false, false);
		projs[7] = new Projectile(220, 100,PROJECTILE_WIDTH,-MathUtils.random(10,26),1, false, false);
		
		
		line = new Rectangle(midPointX-1.5f, 0, 3,136);
	}
	
	public void update(float delta) {
		for(Projectile p : projs) {
			p.update(delta);
		}

		for(Projectile p : projs) {
			if(p.isDead()) {
				
				if(p.isBomb()) {
					AssetLoader.explosion.play();
					gameworld.endGame();					
				}
				else 
					AssetLoader.hit.play();
					p.respawn();
					gameworld.addScore(1);

			}
		}

    }
	

	public boolean collides() {
		for(Projectile p : projs) {
		if(Intersector.overlaps(p.getHitbox(), line)) {
			if(!p.isBomb()) return true;
			else p.respawn();
		}
		
		}
		
		return false;
	}

	public void stop() {
		for(Projectile p : projs) {
			p.stop();
		}
	}

	public void touchObject(int x, int y) {
		for(Projectile p : projs) {
			if(p.getHitbox().contains(x, y)) p.damage();
		}
	}

	public void onRestart() {
		for(Projectile p : projs) {
			if(p.isFacingRight())
				p.onRestart(-16, MathUtils.random(10,26));
			else
				p.onRestart(220, -MathUtils.random(10,26));
			p.updateHitbox();
		}
	}

	public Projectile[] getProjs() {
		return projs;
	}
}
