package com.game.mesh.body;

import com.game.addition.math.BodyRectangle;

public class NoSpriteObject extends Body{
	public NoSpriteObject (float x, float y, float bodyW, float bodyH){
		bodyRect = new BodyRectangle (x, y, bodyW, bodyH);
	}
}
