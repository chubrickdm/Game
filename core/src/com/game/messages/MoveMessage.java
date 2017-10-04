package com.game.messages;

import com.game.objects.BodyObject;
import com.game.objects.GameObject;


public class MoveMessage extends GameMessage{
	public float oldX;
	public float oldY;
	public BodyObject body;
	
	
	public MoveMessage (GameObject object, float oldX, float oldY, BodyObject body){
		this.type = MessageType.movement;
		this.oldX = oldX;
		this.oldY = oldY;
		this.body = body;
		this.object = object;
	}
}
