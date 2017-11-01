package com.game.messages;


public class GetPositionMessage extends GameMessage{
	public GetPositionMessage (){
		this.type = MessageType.getPosition;
	}
}
