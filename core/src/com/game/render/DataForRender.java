package com.game.render;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class DataForRender{
	public Sprite sprite;
	public LayerType layerType;
	
	public DataForRender (Sprite sprite, LayerType layerType){
		this.sprite = sprite;
		this.layerType = layerType;
	}
}
