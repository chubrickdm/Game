package com.game.messages;

import com.game.objects.GameObject;

public class PushOutMessage extends GameMessage{
	public float whereX;
	public float whereY;
	
	
	public PushOutMessage (GameObject object, float whereX, float whereY){
		this.type = MessageType.pushOut;
		this.object = object;
		this.whereX = whereX;
		this.whereY = whereY;
	}
}
