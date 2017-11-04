package com.game.messages;

import com.game.mesh.objects.GameObject;

public class ReturnPositionMessage extends GameMessage{
	public float spriteX;
	public float spriteY;
	public float spriteW;
	public float spriteH;
	
	
	public ReturnPositionMessage (GameObject object, float spriteX, float spriteY, float spriteW, float spriteH){
		this.type = MessageType.returnPosition;
		this.object = object;
		this.objectType = object.objectType;
		
		this.spriteX = spriteX;
		this.spriteY = spriteY;
		this.spriteW = spriteW;
		this.spriteH = spriteH;
	}
}
