package com.game.messages;

import com.game.mesh.objects.GameObject;

public class CharacterChangeMessage extends GameMessage{
	public CharacterChangeMessage (GameObject object){
		this.type = MessageType.characterChange;
		this.object = object;
	}
}