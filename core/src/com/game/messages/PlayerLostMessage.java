package com.game.messages;

public class PlayerLostMessage extends GameMessage{
	public PlayerLostMessage (){
		this.type = MessageType.playerLost;
	}
}
