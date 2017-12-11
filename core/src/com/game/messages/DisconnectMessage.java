package com.game.messages;

import com.game.mesh.objects.GameObject;

public class DisconnectMessage extends GameMessage{
	public DisconnectMessage (GameObject who){
		this.type = MessageType.disconnect;
		this.object = who;
		this.objectType = object.objectType;
	}
}
