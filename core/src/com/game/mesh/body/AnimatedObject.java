package com.game.mesh.body;

import com.game.addition.math.BodyRectangle;

public class AnimatedObject extends Body{ //спец. класс для анимированных объектов, в котором хранятся координаты спрайта
	private float spriteX;
	private float spriteY;
	private float spriteW;
	private float spriteH;
	private float bodyShiftX;
	private float bodyShiftY;
	
	
	public AnimatedObject (float x, float y, float w, float h, float bodyW, float bodyH){
		spriteX = x;
		spriteY = y;
		bodyShiftX = (w - bodyW) / 2;
		body = new BodyRectangle (x + bodyShiftX, y + bodyShiftY, bodyW, bodyH);
		spriteW = w;
		spriteH = h;
	}
	
	public AnimatedObject (float x, float y, float w, float h, float bodyW, float bodyH, boolean withSfitY){
		spriteX = x;
		spriteY = y;
		bodyShiftY = (h - bodyH) / 2;
		if (withSfitY){
			bodyShiftX = (w - bodyW) / 2;
		}
		body = new BodyRectangle (x + bodyShiftX, y + bodyShiftY, bodyW, bodyH);
		spriteW = w;
		spriteH = h;
	}
	
	@Override
	public void setBodyPosition (float x, float y){
		spriteX = x - bodyShiftX;
		spriteY = y - bodyShiftY;
		body.setPosition (x, y);
		if (triggeredZone != null){
			triggeredZone.setPosition (x, y);
		}
	}
	
	@Override
	public void setSpritePosition (float x, float y){
		spriteX = x;
		spriteY = y;
		body.setPosition (x + bodyShiftX, y + bodyShiftY);
		if (triggeredZone != null){
			triggeredZone.setPosition (x + body.getW () / 2, y + body.getH () / 2);
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
