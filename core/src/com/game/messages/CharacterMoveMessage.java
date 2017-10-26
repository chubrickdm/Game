package com.game.messages;

import com.game.math.BodyRectangle;
import com.game.objects.GameObject;


public class CharacterMoveMessage extends GameMessage{
	public float spriteOldX;
	public float spriteOldY;
	public float oldX;
	public float oldY;
	public BodyRectangle bodyRectangle;
	
	
	public CharacterMoveMessage (GameObject object, float oldX, float oldY, BodyRectangle bodyRectangle, float spriteOldX,
								 float spriteOldY){
		this.spriteOldX = spriteOldX;
		this.spriteOldY = spriteOldY;
		this.type = MessageType.characterMove;
		this.oldX = oldX;
		this.oldY = oldY;
		this.bodyRectangle = bodyRectangle;
		this.object = object;
	}
}
