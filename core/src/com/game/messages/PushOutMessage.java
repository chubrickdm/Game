package com.game.messages;

import com.game.mesh.objects.GameObject;

public class PushOutMessage extends GameMessage{
	public float whereBodyX;
	public float whereBodyY;
	
	
	public PushOutMessage (GameObject object, float whereBodyX, float whereBodyY){
		this.type = MessageType.pushOut;
		this.object = object;
		this.objectType = object.objectType;
		
		this.whereBodyX = whereBodyX;
		this.whereBodyY = whereBodyY;
	}
}
