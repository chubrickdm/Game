package com.game.messages;

import com.game.objects.GameObject;

public class CharacterSelectedMessage extends GameMessage{
	public CharacterSelectedMessage (GameObject object){
		this.type = MessageType.characterSelected;
		this.object = object;
	}
}
