package com.game.objects.body;

import com.game.math.BodyRectangle;

public class NoSpriteObject implements Body{
	private float spriteX;
	private float spriteY;
	private float bodyShiftX;
	private float bodyShiftY;
	
	public BodyRectangle bodyRect;
	
	
	public NoSpriteObject (float x, float y, float w, float h, float bodyW, float bodyH){
		spriteX = x;
		spriteY = y;
		bodyShiftX = (w - bodyW) / 2;
		bodyShiftY = (h - bodyH) / 2;
		bodyRect = new BodyRectangle (x + bodyShiftX, y + bodyShiftY, bodyW, bodyH);
	}
	
	public void setBodyPosition (float x, float y){
		spriteX = x - bodyShiftX;
		spriteY = y - bodyShiftY;
		bodyRect.setPosition (x, y);
	}
	
	public float getSpriteX (){
		return spriteX;
	}
	
	public float getSpriteY (){
		return spriteY;
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
		bodyRect.move (deltaX, deltaY);
	}
}
