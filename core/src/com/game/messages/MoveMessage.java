package com.game.messages;

import com.game.addition.math.BodyRectangle;
import com.game.mesh.objects.GameObject;

public class MoveMessage extends GameMessage{
	public float deltaY;
	public float spriteOldX;
	public float spriteOldY;
	public float oldBodyX;
	public float oldBodyY;
	public BodyRectangle bodyRectangle;
	
	
	public MoveMessage (GameObject object, float deltaY, float oldBodyX, float oldBodyY, BodyRectangle bodyRectangle,
						float spriteOldX, float spriteOldY){
		this.type = MessageType.move;
		this.objectType = object.objectType;
		this.deltaY = deltaY;
		this.spriteOldX = spriteOldX;
		this.spriteOldY = spriteOldY;
		this.oldBodyX = oldBodyX;
		this.oldBodyY = oldBodyY;
		this.bodyRectangle = bodyRectangle;
		this.object = object;
	}
}
