package com.game.mesh.body;

import com.game.addition.math.BodyRectangle;

public class AnimatedObject extends Body{ //спец. класс для анимированных объектов, в котором хранятся координаты спрайта
	private float spriteX = 0;
	private float spriteY = 0;
	private float bodyShiftX = 0;
	private float spriteW;
	private float spriteH;
	
	
	public AnimatedObject (float x, float y, float w, float h, float bodyW, float bodyH){
		spriteX = x;
		spriteY = y;
		bodyShiftX = (w - bodyW) / 2;
		body = new BodyRectangle (x + bodyShiftX, y, bodyW, bodyH);
		spriteW = w;
		spriteH = h;
	}
	
	@Override
	public void setBodyPosition (float x, float y){
		spriteX = x - bodyShiftX;
		spriteY = y;
		body.setPosition (x, y);
		if (triggeredZone != null){
			triggeredZone.setPosition (x, y);
		}
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
	public float getSpriteW (){
		return spriteW;
	}
	
	@Override
	public float getSpriteH (){
		return spriteH;
	}
	
	@Override
	public void move (float deltaX, float deltaY){
		body.move (deltaX, deltaY);
		spriteX += deltaX;
		spriteY += deltaY;
		if (triggeredZone != null){
			triggeredZone.move (deltaX, deltaY);
		}
	}
}
