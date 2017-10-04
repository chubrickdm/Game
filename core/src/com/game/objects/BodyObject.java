package com.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.awt.Rectangle;

public class BodyObject{
	public Sprite sprite;
	public Rectangle objectBase;
	
	
	
	
	
	BodyObject (String fileName, float x, float y, float w, float h){
		Texture texture = new Texture (fileName);
		sprite = new Sprite (texture);
		sprite.setBounds (x, y, w, h);
		objectBase = new Rectangle ((int) x, (int) y, (int) w, (int) h);
	}
	
	void setPosition (float x, float y){
		sprite.setPosition (x, y);
		objectBase.setLocation ((int) x, (int) y);
	}
	
	boolean intersects (BodyObject body){
		return objectBase.intersects (body.objectBase);
	}
	
	float getX (){
		return sprite.getX ();
	}
	
	float getY (){
		return sprite.getY ();
	}
	
	void move (float deltaX, float deltaY){
		sprite.setPosition (sprite.getX () + deltaX, sprite.getY () + deltaY);
		objectBase.setLocation ((int) (sprite.getX () + deltaX), (int) (sprite.getY () + deltaY));
	}
}
