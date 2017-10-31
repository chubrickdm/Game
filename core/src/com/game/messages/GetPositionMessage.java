package com.game.messages;

import com.game.mesh.objects.GameObject;

public class GetPositionMessage extends GameMessage{
	public GetPositionMessage (GameObject object){
		this.type = MessageType.getPosition;
		this.objectType = object.objectType;
		this.object = object;
	}
}
