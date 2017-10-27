package com.game.mesh.body;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.addition.math.BodyRectangle;


public class BodyObject extends Body{
	private float bodyShiftX;
	private float bodyShiftY;
	
	
	public BodyObject (String fileName, float x, float y, float w, float h, float bodyW, float bodyH){
		Texture texture = new Texture (fileName);
		sprite = new Sprite (texture);
		
		sprite.setBounds (x, y, w, h);
		bodyShiftX = (w - bodyW) / 2;
		bodyShiftY = (h - bodyH) / 2;
		bodyRect = new BodyRectangle (x + bodyShiftX, y + bodyShiftY, bodyW, bodyH);
	}
	
	@Override
	public void setBodyPosition (float x, float y){
		sprite.setPosition (x - bodyShiftX, y - bodyShiftY);
		bodyRect.setPosition (x, y);
	}
	
	@Override
	public void rotate90 (){
		sprite.rotate90 (true);
		sprite.setSize (sprite.getHeight (), sprite.getWidth ());
		bodyRect = new BodyRectangle (bodyRect.getX () + bodyShiftX, bodyRect.getY () + bodyShiftY,
				sprite.getWidth (), sprite.getHeight ());
	}
}
