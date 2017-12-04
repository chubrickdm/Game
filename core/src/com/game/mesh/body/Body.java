package com.game.mesh.body;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.game.addition.math.BodyRectangle;
import com.game.mesh.TriggeredZone;

public abstract class Body{
	protected BodyRectangle body;
	protected TriggeredZone triggeredZone = null;
	protected Sprite sprite;
	
	
	public void setBodyPosition (float x, float y){ }
	
	public void setBodyBounds (float x, float y, float w, float h){
		body.setPosition (x, y);
		body.setSize (w, h);
	}
	
	public void move (float deltaX, float deltaY){
		sprite.setPosition (sprite.getX () + deltaX, sprite.getY () + deltaY);
		body.move (deltaX, deltaY);
	}
	
	public boolean intersects (float x, float y, float w, float h){
		return body.intersects (x, y, w, h);
	}
	
	public boolean contains (float x, float y, float w, float h){
		return body.contains (x, y, w, h);
	}
	
	public float getBodyX (){
		return body.getX ();
	}
	
	public float getBodyY (){
		return body.getY ();
	}
	
	public float getBodyW (){
		return body.getW ();
	}
	
	public float getBodyH (){
		return body.getH ();
	}
	
	public float getSpriteX (){
		return sprite.getX ();
	}
	
	public float getSpriteY (){
		return sprite.getY ();
	}
	
	public float getSpriteW (){
		return sprite.getWidth ();
	}
	
	public float getSpriteH (){
		return sprite.getHeight ();
	}
	
	public Sprite getSprite (){
		return sprite;
	}
	
	public void setScale (float scale){ }
	
	public void setOrigin (float originX, float originY){ }
	
	public void setSpritePosition (float x, float y){
		sprite.setPosition (x, y);
		body.setPosition (x, y);
	}
	
	public void setTriggeredZone (TriggeredZone zone){
		triggeredZone = zone;
		triggeredZone.setPosition (body.getX () + body.getW () / 2, body.getY () + body.getH () / 2);
	}
	
	public boolean checkTriggered (float x, float y, float w, float h){
		if (triggeredZone != null){
			return triggeredZone.isTriggered (x, y, w, h);
		}
		return false;
	}
}