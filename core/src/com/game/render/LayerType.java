package com.game.render;

public enum LayerType{
	actionWheel (2), character (1), wall (1), floor (0),  unknown (-1);
	
	private int value;
	
	LayerType (int value){
		this.value = value;
	}
	
	public int getValue (){
		return value;
	}
}
