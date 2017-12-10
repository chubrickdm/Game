package com.game.messages;

import com.game.mesh.objects.GameObject;

public class GoToMessage extends GameMessage{
	public float whereX;
	public float whereY;
	
	
	public GoToMessage (GameObject who, float whereX, float whereY){
		this.type = MessageType.goTo;
		this.object = who;
		this.objectType = object.objectType;
		
		this.whereX = whereX;
		this.whereY = whereY;
	}
}
