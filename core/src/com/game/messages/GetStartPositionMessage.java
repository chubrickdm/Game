package com.game.messages;


public class GetStartPositionMessage extends GameMessage{
	public GetStartPositionMessage (){
		this.type = MessageType.getStartPosition;
	}
}
