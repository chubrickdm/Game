package com.game.messages;

import com.game.objects.GameObject;

public class KeyDownMessage extends GameMessage{
	private int keyCode;
	
	
	public KeyDownMessage (GameObject object, int keyCode){
		this.type = MessageType.keyDown;
		this.keyCode = keyCode;
		this.object = object;
	}
	
	public int getKeyCode (){
		return keyCode;
	}
}
