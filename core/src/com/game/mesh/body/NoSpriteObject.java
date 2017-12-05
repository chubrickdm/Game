package com.game.mesh.body;

import com.game.addition.math.Rectangle;

public class NoSpriteObject extends Body{
	public NoSpriteObject (float x, float y, float bodyW, float bodyH){
		body = new Rectangle (x, y, bodyW, bodyH);
	}
}
