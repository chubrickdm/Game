package com.game.messages;


public class GetStartPositionMessage extends GameMessage{ //создается в начале игры, для получения начальных позиций
	public GetStartPositionMessage (){
		this.type = MessageType.getStartPosition;
	}
}
