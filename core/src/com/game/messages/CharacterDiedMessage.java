package com.game.messages;

import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;

public class CharacterDiedMessage extends GameMessage{
	public ObjectType killer;
	
	
	public CharacterDiedMessage (GameObject object, GameObject killer){
		this.type = MessageType.characterDied;
		this.object = object;
		this.objectType = object.objectType;
		
		this.killer = killer.objectType;
	}
}
