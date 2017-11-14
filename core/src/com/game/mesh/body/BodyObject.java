package com.game.mesh.body;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.game.addition.math.BodyRectangle;

public class BodyObject extends Body{
	private float bodyShiftX = 0;
	private float bodyShiftY = 0;
	
	
	public BodyObject (String fileName, float x, float y, float w, float h, float bodyW, float bodyH){
		Texture texture = new Texture (fileName);
		sprite = new Sprite (texture);
		
		sprite.setBounds (x, y, w, h);
		bodyShiftX = (w - bodyW) / 2;
		body = new BodyRectangle (x + bodyShiftX, y + bodyShiftY, bodyW, bodyH);
	}
	
	public BodyObject (String fileName, boolean withShiftY, float x, float y, float w, float h, float bodyW, float bodyH){
		Texture texture = new Texture (fileName);
		sprite = new Sprite (texture);
		
		sprite.setBounds (x, y, w, h);
		bodyShiftX = (w - bodyW) / 2;
		if (withShiftY){
			bodyShiftY = (h - bodyH) / 2;
		}
		body = new BodyRectangle (x + bodyShiftX, y + bodyShiftY, bodyW, bodyH);
	}
	
	@Override
	public void setBodyPosition (float x, float y){
		sprite.setPosition (x - bodyShiftX, y - bodyShiftY);
		body.setPosition (x, y);
	}
}
