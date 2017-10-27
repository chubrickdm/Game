package com.game.messages;

import com.game.mesh.objects.GameObject;

public class CharacterSelectedMessage extends GameMessage{
	public CharacterSelectedMessage (GameObject object){
		this.type = MessageType.characterSelected;
		this.object = object;
	}
}
