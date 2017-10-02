package com.game.messages;

import com.game.objects.GameObject;
import com.badlogic.gdx.math.Rectangle;

public class MoveMessage extends GameMessage{
	public double oldX;
	public double oldY;
	public double newX;
	public double newY;
	public Rectangle rectangle;
	
	
	public MoveMessage (GameObject object, double oldX, double oldY, Rectangle rectangle){
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = rectangle.x;
		this.newY = rectangle.y;
		this.rectangle = rectangle;
		this.type = MessageType.movement;
		this.object = object;
	}
}
