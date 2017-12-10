package com.game.messages;

import com.game.mesh.objects.GameObject;

public class ChangeStateMessage extends GameMessage{
	public GameObject fromWhom;
	
	
	public ChangeStateMessage (GameObject who, GameObject fromWhom){
		this.type = MessageType.changeState;
		this.object = who;
		this.objectType = object.objectType;
		
		this.fromWhom = fromWhom;
	}
}
