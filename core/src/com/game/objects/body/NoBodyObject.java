package com.game.objects.body;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.math.BodyRectangle;

public class NoBodyObject implements Body{
	public float originX = 0;
	public float originY = 0;
	public Sprite sprite;
	
	
	public NoBodyObject (String fileName, float x, float y, float w, float h){
		Texture texture = new Texture (fileName);
		sprite = new Sprite (texture);
		sprite.setBounds (x, y, w, h);
	}
	
	public void setSpritePosition (float x, float y){
		sprite.setPosition (x - originX, y - originY);
	}
	
	public void setOrigin (float originX, float originY){
		this.originX = originX;
		this.originY = originY;
		setSpritePosition (sprite.getX (), sprite.getY ());
	}
	
	public void setScale (float scale){
		sprite.setScale (scale);
		//setOrigin (originX * scale, originY * scale);
	}
	
	@Override
	public void setBodyPosition (float x, float y){
		setSpritePosition (x, y);
	}
	
	@Override
	public void move (float deltaX, float deltaY){
		sprite.setPosition (sprite.getX () + deltaX, sprite.getY () + deltaY);
	}
	
	@Override
	public boolean intersects (BodyRectangle bodyRectangle){
		return false;
	}
	
	@Override
	public float getBodyX (){
		return sprite.getX ();
	}
	
	@Override
	public float getBodyY (){
		return sprite.getY ();
	}
}
