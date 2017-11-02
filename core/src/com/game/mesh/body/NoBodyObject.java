package com.game.mesh.body;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class NoBodyObject extends Body{
	private float originX = 0;
	private float originY = 0;
	
	
	public NoBodyObject (String fileName, float x, float y, float w, float h){
		Texture texture = new Texture (fileName);
		sprite = new Sprite (texture);
		sprite.setBounds (x, y, w, h);
	}
	
	@Override
	public void setSpritePosition (float x, float y){
		sprite.setPosition (x - originX, y - originY);
	}
	
	@Override
	public void setOrigin (float originX, float originY){
		this.originX = originX;
		this.originY = originY;
		setSpritePosition (sprite.getX (), sprite.getY ());
	}
	
	@Override
	public void setScale (float scale){
		sprite.setScale (scale);
		sprite.setOriginCenter ();
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
	public float getBodyX (){
		return sprite.getX ();
	}
	
	@Override
	public float getBodyY (){
		return sprite.getY ();
	}
}
