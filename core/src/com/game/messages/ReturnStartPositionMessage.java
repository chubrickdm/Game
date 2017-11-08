package com.game.messages;

import com.game.mesh.objects.GameObject;

public class ReturnStartPositionMessage extends GameMessage{
	public float spriteX;
	public float spriteY;
	public float spriteW;
	public float spriteH;
	
	
	public ReturnStartPositionMessage (GameObject object, float spriteX, float spriteY, float spriteW, float spriteH){
		this.type = MessageType.returnStartPosition;
		this.object = object;
		this.objectType = object.objectType;
		
		this.spriteX = spriteX;
		this.spriteY = spriteY;
		this.spriteW = spriteW;
		this.spriteH = spriteH;
	}
}
