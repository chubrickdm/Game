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
		//пришлось делать так, т.к. в LibGDX криво реализован центр спрайта
		sprite.setPosition (x - originX, y - originY);
	}
	
	@Override
	public void setOrigin (float originX, float originY){
		//пришлось делать так, т.к. в LibGDX криво реализован центр спрайта
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
	public void move (float deltaX, float deltaY){
		sprite.setPosition (sprite.getX () + deltaX, sprite.getY () + deltaY);
	}
}
