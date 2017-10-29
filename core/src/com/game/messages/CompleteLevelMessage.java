package com.game.messages;

public class CompleteLevelMessage extends GameMessage{
	public CompleteLevelMessage (){
		this.type = MessageType.levelComplete;
	}
}
