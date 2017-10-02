package com.game.messages;

import com.game.objects.GameObject;

public class DeleteMessage extends GameMessage{
	public DeleteMessage (GameObject object){
		this.type = MessageType.deleting;
		this.object = object;
	}
}
