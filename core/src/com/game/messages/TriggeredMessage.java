package com.game.messages;

import com.game.mesh.objects.GameObject;

public class TriggeredMessage extends GameMessage{ //создается когда какой-то объект возбудился
	public GameObject triggered;
	
	
	public TriggeredMessage (GameObject exciter, GameObject object){
		this.type = MessageType.triggered;
		this.object = exciter;
		this.objectType = object.objectType;
		
		this.triggered = object;
	}
}
