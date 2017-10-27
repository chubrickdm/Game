package com.game.mesh.body;

import com.game.addition.math.BodyRectangle;

public class NoSpriteObject extends Body{
	private float spriteX;
	private float spriteY;
	private float bodyShiftX;
	private float bodyShiftY;
	
	
	public NoSpriteObject (float x, float y, float w, float h, float bodyW, float bodyH){
		spriteX = x;
		spriteY = y;
		bodyShiftX = (w - bodyW) / 2;
		bodyShiftY = (h - bodyH) / 2;
		bodyRect = new BodyRectangle (x + bodyShiftX, y + bodyShiftY, bodyW, bodyH);
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
