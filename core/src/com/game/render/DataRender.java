package com.game.render;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class DataRender{
	public Sprite sprite;
	public LayerType layerType;
	
	public DataRender (Sprite sprite, LayerType layerType){
		this.sprite = sprite;
		this.layerType = layerType;
	}
}
