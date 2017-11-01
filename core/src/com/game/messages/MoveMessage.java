package com.game.messages;

import com.game.mesh.objects.GameObject;

public class MoveMessage extends GameMessage{
	public float deltaX;
	public float deltaY;
	public float spriteOldX;
	public float spriteOldY;
	public float oldBodyX;
	public float oldBodyY;
	public float bodyW;
	public float bodyH;
	
	
	public MoveMessage (GameObject object, float deltaX, float deltaY, float oldBodyX, float oldBodyY, float spriteOldX,
						float spriteOldY, float bodyW, float bodyH){
		this.type = MessageType.move;
		this.object = object;
		this.objectType = object.objectType;
		
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.oldBodyX = oldBodyX;
		this.oldBodyY = oldBodyY;
		this.spriteOldX = spriteOldX;
		this.spriteOldY = spriteOldY;
		this.bodyW = bodyW;
		this.bodyH = bodyH;
	}
}
