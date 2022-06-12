package com.darkhills.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
	
	protected Vector2 position; //inferior left corner
    protected Vector2 velocity;
    protected int width;
    protected int health;
    protected boolean isDead;
    private Rectangle squareHitbox;
    private Random r;
    private Boolean facingRight;
    private Boolean isBomb;
    private float minVel;
    
    public Projectile(float x, float y, int width, float scrollSpeed, int health, boolean right, boolean isRight) {
        position = new Vector2(x, y);
        velocity = new Vector2(scrollSpeed, 0);
        this.width = width;
        this.health = health;
        isDead = false;
        squareHitbox = new Rectangle(position.x,position.y,width, width);
        facingRight = right;
        r = new Random();
        minVel = 10;
        isBomb=false;
    }

    public void update(float delta) {
    	minVel += 0.0005f;
        position.add(velocity.cpy().scl(delta));
        
        squareHitbox.set(position.x, position.y, width, width);
        
        if (health <= 0 ) {        	
            isDead = true;
        }
    }
    
    public void respawn() {
    	//######test######
    	if(MathUtils.random()>0.1f) isBomb = false;
    	else isBomb = true;
    	//######test######
    	if(facingRight) { 
    		position.x = - width;
    		velocity.x = MathUtils.random(minVel, minVel + 15);
    	}
    	else {
    	position.x = 220;
    	velocity.x = -MathUtils.random(minVel, minVel + 15);
    	}
        isDead = false;
        health = 1;
    }

    public boolean isDead() {
        return isDead;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }
    
    public Rectangle getHitbox() {
    	return squareHitbox;
    }
    
    public void stop() {
    	velocity.x = 0;
    }
    
    public void damage() {
    	--health;
    }
    
    public boolean isFacingRight() {
    	return facingRight;
    }
    
    public boolean isBomb() {
    	return isBomb;
    }
    
    public void onRestart(int x, int v) {
    	position.x = x;
    	velocity.x = v;
    	isBomb = false;
    }

	public void updateHitbox() {
		squareHitbox.set(position.x, position.y, width, width);
	}
}
