package com.game.mesh.body;

import com.game.addition.math.Rectangle;

public class AnimatedObject extends Body{ //спец. класс для анимированных объектов, в котором хранятся координаты спрайта
	private float bodyShiftX;
	private float bodyShiftY;
	private Rectangle sprite;
	
	
	public AnimatedObject (float x, float y, float w, float h, float bodyW, float bodyH){
		bodyShiftX = (w - bodyW) / 2;
		sprite = new Rectangle (x, y, w, h);
		body = new Rectangle (x + bodyShiftX, y + bodyShiftY, bodyW, bodyH);
	}
	
	public AnimatedObject (float x, float y, float w, float h, float bodyW, float bodyH, boolean withShiftY){
		bodyShiftX = (w - bodyW) / 2;
		if (withShiftY){
			bodyShiftY = (h - bodyH) / 2;
		}
		sprite = new Rectangle (x, y, w, h);
		body = new Rectangle (x + bodyShiftX, y + bodyShiftY, bodyW, bodyH);
	}
	
	
	@Override
	public void setSpritePosition (float x, float y){
		sprite.setPosition (x, y);
		body.setPosition (x + bodyShiftX, y + bodyShiftY);
		if (triggeredZone != null){
			triggeredZone.setPosition (x + body.getW () / 2, y + body.getH () / 2);
		}
	}
	
	@Override
	public float getSpriteX (){
		return sprite.getX ();
	}
	
	@Override
	public float getSpriteY (){
		return sprite.getY ();
	}
	
	@Override
	public float getSpriteW (){
		return sprite.getW ();
	}
	
	@Override
	public float getSpriteH (){
		return sprite.getH ();
	}
	
	@Override
	public void move (float deltaX, float deltaY){
		body.move (deltaX, deltaY);
		sprite.move (deltaX, deltaY);
		if (triggeredZone != null){
			triggeredZone.move (deltaX, deltaY);
		}
	}
}
