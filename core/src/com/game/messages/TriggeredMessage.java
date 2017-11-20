package com.game.messages;

import com.game.mesh.objects.GameObject;

public class TriggeredMessage extends GameMessage{ //создается когда какой-то объект возбудился
	public boolean isTriggered;
	
	
	public TriggeredMessage (GameObject object, boolean isTriggered){
		this.type = MessageType.triggered;
		this.object = object;
		this.objectType = object.objectType;
		
		this.isTriggered = isTriggered;
	}
}
