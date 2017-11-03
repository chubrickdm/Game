package com.game.mesh.body;

import com.game.addition.math.BodyRectangle;

public class NoSpriteObject extends Body{
	private float spriteX = 0;
	private float spriteY = 0;
	private float bodyShiftX = 0;
	private float bodyShiftY = 0;
	
	
	public NoSpriteObject (float x, float y, float w, float h, float bodyW, float bodyH){
		spriteX = x;
		spriteY = y;
		bodyShiftX = (w - bodyW) / 2;
		//bodyShiftY = (h - bodyH) / 2;
		bodyRect = new BodyRectangle (x + bodyShiftX, y + bodyShiftY, bodyW, bodyH);
	}
	
	public NoSpriteObject (float x, float y, float bodyW, float bodyH){
		spriteX = x;
		spriteY = y;
		bodyShiftX = 0;
		bodyShiftY = 0;
		bodyRect = new BodyRectangle (x, y, bodyW, bodyH);
	}
	
	@Override
	public void setBodyPosition (float x, float y){
		spriteX = x - bodyShiftX;
		spriteY = y - bodyShiftY;
		bodyRect.setPosition (x, y);
	}
	
	@Override
	public float getSpriteX (){
		return spriteX;
	}
	
	@Override
	public float getSpriteY (){
		return spriteY;
	}
	
	@Override
	public void move (float deltaX, float deltaY){
		bodyRect.move (deltaX, deltaY);
		spriteX += deltaX;
		spriteY += deltaY;
	}
}
