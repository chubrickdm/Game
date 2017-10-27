package com.game.messages;

import com.game.math.BodyRectangle;
import com.game.objects.GameObject;
import com.game.objects.ObjectType;

public class MoveMessage extends GameMessage{
	public float spriteOldX;
	public float spriteOldY;
	public float oldBodyX;
	public float oldBodyY;
	public BodyRectangle bodyRectangle;
	
	
	public MoveMessage (GameObject object, float oldBodyX, float oldBodyY, BodyRectangle bodyRectangle, float spriteOldX,
						float spriteOldY, ObjectType objectType){
		this.type = MessageType.move;
		this.objectType = objectType;
		this.spriteOldX = spriteOldX;
		this.spriteOldY = spriteOldY;
		this.oldBodyX = oldBodyX;
		this.oldBodyY = oldBodyY;
		this.bodyRectangle = bodyRectangle;
		this.object = object;
	}
}
