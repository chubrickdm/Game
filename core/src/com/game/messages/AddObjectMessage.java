package com.game.messages;

import com.game.mesh.objects.GameObject;

public class AddObjectMessage extends GameMessage{
	public AddObjectMessage (GameObject object){
		this.type = MessageType.addObject;
		this.object = object;
		this.objectType = object.objectType;
	}
}
