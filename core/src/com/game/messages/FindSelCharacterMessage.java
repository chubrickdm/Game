package com.game.messages;

import com.game.objects.GameObject;

public class FindSelCharacterMessage extends GameMessage{
	public FindSelCharacterMessage (GameObject object){
		this.type = MessageType.findSelCharacter;
		this.object = object;
	}
}
