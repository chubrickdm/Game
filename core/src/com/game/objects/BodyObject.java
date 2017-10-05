package com.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.math.BodyRectangle;


public class BodyObject{
	private Sprite sprite;
	private BodyRectangle bodyRect;
	
	
	public BodyObject (String fileName, float x, float y, float w, float h){
		Texture texture = new Texture (fileName);
		sprite = new Sprite (texture);
		sprite.setBounds (x, y, w, h);
		bodyRect = new BodyRectangle (x, y, w, h);
	}
	
	public void setPosition (float x, float y){
		sprite.setPosition (x, y);
		bodyRect.setPosition (x, y);
	}
	
	public boolean intersects (BodyObject body){
		return bodyRect.intersects (body.bodyRect);
	}
	
	public float getX (){
		return sprite.getX ();
	}
	
	public float getY (){
		return sprite.getY ();
	}
	
	public void move (float deltaX, float deltaY){
		sprite.setPosition (sprite.getX () + deltaX, sprite.getY () + deltaY);
		bodyRect.move (deltaX, deltaY);
	}
	
	public Sprite getSprite (){
		return sprite;
	}
}
