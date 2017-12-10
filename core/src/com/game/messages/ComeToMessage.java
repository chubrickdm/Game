package com.game.messages;

import com.game.mesh.objects.GameObject;

public class ComeToMessage extends GameMessage{
	public ComeToMessage (GameObject who){
		this.type = MessageType.comeTo;
		this.object = who;
		this.objectType = object.objectType;
	}
}
