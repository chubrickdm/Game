package com.game.objects.body;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class NoBodyObject{
	public float originX = 0;
	public float originY = 0;
	public Sprite sprite;
	
	
	public NoBodyObject (String fileName, float x, float y, float w, float h){
		Texture texture = new Texture (fileName);
		sprite = new Sprite (texture);
		sprite.setBounds (x, y, w, h);
	}
	
	public void setPosition (float x, float y){
		sprite.setPosition (x - originX, y - originY);
	}
	
	public void setOrigin (float originX, float originY){
		this.originX = originX;
		this.originY = originY;
		setPosition (sprite.getX (), sprite.getY ());
	}
}
