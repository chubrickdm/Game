package com.game.object.Messages;

import com.game.object.GameObject;

public class Message{
	public MessageType type;
	public GameObject object;
	
	
	public Message (MessageType type, GameObject object){
		this.type = type;
		this.object = object;
	}
}
