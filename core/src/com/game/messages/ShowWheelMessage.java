package com.game.messages;

import com.game.math.BodyRectangle;
import com.game.objects.GameObject;


public class ShowWheelMessage extends GameMessage{
	public BodyRectangle bodyRectangle;
	
	
	public ShowWheelMessage (GameObject object, BodyRectangle bodyRectangle){
		this.type = MessageType.showWheel;
		this.bodyRectangle = bodyRectangle;
		this.object = object;
	}
}
