package com.game.mesh.body;

import com.game.addition.math.Rectangle;
import com.introfog.primitiveIsometricEngine.World;

public class AnimatedObject extends Body{ //спец. класс для анимированных объектов, в котором хранятся координаты спрайта
	private float bodyShiftX;
	private float bodyShiftY;
	private Rectangle sprite;
	private com.introfog.primitiveIsometricEngine.Body PIEBody;
	
	
	public AnimatedObject (float x, float y, float w, float h, float bodyW, float bodyH){
		bodyShiftX = (w - bodyW) / 2;
		sprite = new Rectangle (x, y, w, h);
		body = new Rectangle (x + bodyShiftX, y + bodyShiftY, bodyW, bodyH);
		
		PIEBody = new com.introfog.primitiveIsometricEngine.Body (x + bodyShiftX, y + bodyShiftY, bodyW, bodyH);
		World.getInstance ().addObject (PIEBody);
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
		
		PIEBody.setPosition (x + bodyShiftX, y + bodyShiftY);
	}
	
	@Override
	public float getSpriteX (){
		return PIEBody.getX () - bodyShiftX;
	}
	
	@Override
	public float getSpriteY (){
		return PIEBody.getY () - bodyShiftY;
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
		
		PIEBody.move (deltaX, deltaY);
	}
	
	@Override
	public float getBodyX (){
		return PIEBody.getX ();
	}
	
	@Override
	public float getBodyY (){
		return PIEBody.getY ();
	}
	
	@Override
	public float getBodyW (){
		return PIEBody.getW ();
	}
	
	@Override
	public float getBodyH (){
		return PIEBody.getH ();
	}
}
