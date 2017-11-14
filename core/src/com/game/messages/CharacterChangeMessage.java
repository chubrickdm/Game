package com.game.messages;

import com.game.mesh.objects.GameObject;

public class CharacterChangeMessage extends GameMessage{ //сообщение создается когда персонажи меняются
	public CharacterChangeMessage (GameObject object){ //передается персонаж который был
		this.type = MessageType.characterChange;
		this.object = object;
	}
}