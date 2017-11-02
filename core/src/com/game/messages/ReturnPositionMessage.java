package com.game.messages;

import com.game.mesh.objects.GameObject;

public class ReturnPositionMessage extends GameMessage{
	public float spriteX;
	public float spriteY;
	
	
	public ReturnPositionMessage (GameObject object, float spriteX, float spriteY){
		this.type = MessageType.returnPosition;
		this.object = object;
		this.objectType = object.objectType;
		
		this.spriteX = spriteX;
		this.spriteY = spriteY;
	}
}
