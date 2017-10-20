package com.game.render;

public enum LayerType{
	unknown (-1), floor (0), character (1), wall (2), actionWheel (3);
	
	private int value;
	
	LayerType (int value){
		this.value = value;
	}
	
	public int getValue (){
		return value;
	}
}
