package com.game.messages;

import com.game.objects.GameObject;

public class PushOutMessage extends GameMessage{
	public double whereX;
	public double whereY;
	
	
	public PushOutMessage (GameObject object, double whereX, double whereY){
		this.type = MessageType.pushOut;
		this.object = object;
		this.whereX = whereX;
		this.whereY = whereY;
	}
}
