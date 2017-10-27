package com.game.messages;

import com.game.objects.GameObject;

public class PushOutMessage extends GameMessage{
	public float whereBodyX;
	public float whereBodyY;
	
	
	public PushOutMessage (GameObject object, float whereBodyX, float whereBodyY){
		this.type = MessageType.pushOut;
		this.object = object;
		this.whereBodyX = whereBodyX;
		this.whereBodyY = whereBodyY;
	}
}
