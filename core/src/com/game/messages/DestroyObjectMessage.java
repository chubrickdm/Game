package com.game.messages;

import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;

public class DestroyObjectMessage extends GameMessage{ //создается когда объект умирает, уничтожается
	public ObjectType destroyer;
	
	
	public DestroyObjectMessage (GameObject object, GameObject destroyer){
		this.type = MessageType.destroyObject;
		this.object = object;
		this.objectType = object.objectType;
		
		this.destroyer = destroyer.objectType;
	}
}
