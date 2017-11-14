package com.game.messages;

public class PlayerLostMessage extends GameMessage{ //создается когда игрок проиграл (уровень не пройден)
	public PlayerLostMessage (){
		this.type = MessageType.playerLost;
	}
}
