package com.game.messages;

import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;

public abstract class GameMessage{
	public ObjectType objectType;
	public MessageType type;
	public GameObject object;
}
