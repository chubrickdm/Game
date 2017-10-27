package com.game.messages;

import com.game.math.BodyRectangle;
import com.game.objects.GameObject;


public class CharacterMoveMessage extends GameMessage{
	public float spriteOldX;
	public float spriteOldY;
	public float oldBodyX;
	public float oldBodyY;
	public BodyRectangle bodyRectangle;
	
	
	public CharacterMoveMessage (GameObject object, float oldBodyX, float oldBodyY, BodyRectangle bodyRectangle, float spriteOldX,
								 float spriteOldY){
		this.spriteOldX = spriteOldX;
		this.spriteOldY = spriteOldY;
		this.type = MessageType.characterMove;
		this.oldBodyX = oldBodyX;
		this.oldBodyY = oldBodyY;
		this.bodyRectangle = bodyRectangle;
		this.object = object;
	}
}
