package com.game.objects.body;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.math.BodyRectangle;


public class BodyObject{
	public Sprite sprite;
	public BodyRectangle bodyRect;
	
	
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
}
