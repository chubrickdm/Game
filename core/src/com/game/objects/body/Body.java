package com.game.objects.body;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.math.BodyRectangle;

public class Body{
	public BodyRectangle bodyRect;
	public Sprite sprite;
	
	
	public void setBodyPosition (float x, float y){ }
	
	public void move (float deltaX, float deltaY){
		sprite.setPosition (sprite.getX () + deltaX, sprite.getY () + deltaY);
		bodyRect.move (deltaX, deltaY);
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
	
	public float getSpriteX (){
		return sprite.getX ();
	}
	
	public float getSpriteY (){
		return sprite.getY ();
	}
	
	public void setScale (float scale){ }
	
	public void setOrigin (float originX, float originY){ }
	
	public void setSpritePosition (float x, float y){
		sprite.setPosition (x, y);
	}
	
	public void rotate90 (){ }
}
