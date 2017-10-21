package com.game.objects.body;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.math.BodyRectangle;


public class BodyObject implements Body{
	public float bodyShiftX;
	public float bodyShiftY;
	public Sprite sprite;
	public BodyRectangle bodyRect;
	
	
	public BodyObject (String fileName, float x, float y, float w, float h, float bodyW, float bodyH){
		Texture texture = new Texture (fileName);
		sprite = new Sprite (texture);
		
		sprite.setBounds (x, y, w, h);
		bodyShiftX = (w - bodyW) / 2;
		bodyShiftY = (h - bodyH) / 2;
		bodyRect = new BodyRectangle (x + bodyShiftX, y + bodyShiftY, bodyW, bodyH);
	}
	
	public void setBodyPosition (float x, float y){
		sprite.setPosition (x - bodyShiftX, y - bodyShiftY);
		bodyRect.setPosition (x, y);
	}
	
	public boolean intersects (BodyRectangle bodyRectangle){
		return bodyRect.intersects (bodyRectangle);
	}
	
	public float getBodyX (){
		return bodyRect.getX ();
	}
	
	public float getBodyY (){
		return bodyRect.getY ();
	}
	
	public void move (float deltaX, float deltaY){
		sprite.setPosition (sprite.getX () + deltaX, sprite.getY () + deltaY);
		bodyRect.move (deltaX, deltaY);
	}
	
	public void rotate90 (){
		sprite.rotate90 (true);
		sprite.setSize (sprite.getHeight (), sprite.getWidth ());
		bodyRect = new BodyRectangle (bodyRect.getX () + bodyShiftX, bodyRect.getY () + bodyShiftY,
				sprite.getWidth (), sprite.getHeight ());
	}
}
