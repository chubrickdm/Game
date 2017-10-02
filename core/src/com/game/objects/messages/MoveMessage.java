package com.game.objects.messages;

import com.game.objects.GameObject;

public class MoveMessage extends GameMessage{
	public double oldX;
	public double oldY;
	public double newX;
	public double newY;
	
	public MoveMessage (GameObject object, double oldX, double oldY, double newX, double newY){
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = newX;
		this.newY = newY;
		this.type = MessageType.movement;
		this.object = object;
	}
}
