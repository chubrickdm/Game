package com.game.messages;

import com.game.math.BodyRectangle;
import com.game.objects.body.BodyObject;
import com.game.objects.GameObject;


public class MoveMessage extends GameMessage{
	public float oldX;
	public float oldY;
	public BodyRectangle bodyRectangle;
	
	
	public MoveMessage (GameObject object, float oldX, float oldY, BodyRectangle bodyRectangle){
		this.type = MessageType.movement;
		this.oldX = oldX;
		this.oldY = oldY;
		this.bodyRectangle = bodyRectangle;
		this.object = object;
	}
}
