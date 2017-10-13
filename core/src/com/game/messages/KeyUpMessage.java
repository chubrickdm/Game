package com.game.messages;

import com.game.objects.GameObject;

public class KeyUpMessage extends GameMessage{
	private int keyCode;
	
	
	public KeyUpMessage (GameObject object, int keyCode){
		this.type = MessageType.keyUp;
		this.keyCode = keyCode;
		this.object = object;
	}
	
	public int getKeyCode (){
		return keyCode;
	}
}
