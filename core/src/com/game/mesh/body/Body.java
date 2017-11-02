package com.game.mesh.body;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.addition.math.BodyRectangle;

public abstract class Body{
	protected BodyRectangle bodyRect;
	protected Sprite sprite;
	
	
	public void setBodyPosition (float x, float y){ }
	
	public void move (float deltaX, float deltaY){
		sprite.setPosition (sprite.getX () + deltaX, sprite.getY () + deltaY);
		bodyRect.move (deltaX, deltaY);
	}
	
	public boolean intersects (float x, float y, float w, float h){
		return bodyRect.intersects (x, y, w, h);
	}
	
	public boolean contains (float x, float y, float w, float h){
		return bodyRect.contains (x, y, w, h);
	}
	
	public float getBodyX (){
		return bodyRect.getX ();
	}
	
	public float getBodyY (){
		return bodyRect.getY ();
	}
	
	public float getBodyW (){
		return bodyRect.getW ();
	}
	
	public float getBodyH (){
		return bodyRect.getH ();
	}
	
	public float getSpriteX (){
		return sprite.getX ();
	}
	
	public float getSpriteY (){
		return sprite.getY ();
	}
	
	public Sprite getSprite (){
		return new Sprite (sprite);
	}
	
	public void setScale (float scale){ }
	
	public void setOrigin (float originX, float originY){ }
	
	public void setSpritePosition (float x, float y){
		sprite.setPosition (x, y);
	}
	
	public void rotate90 (){ }
}
