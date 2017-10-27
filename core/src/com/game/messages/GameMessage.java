package com.game.messages;

import com.game.objects.GameObject;
import com.game.objects.ObjectType;

public abstract class GameMessage{
	public ObjectType objectType;
	public MessageType type;
	public GameObject object;
}
