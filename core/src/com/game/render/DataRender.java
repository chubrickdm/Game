package com.game.render;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class DataRender{
	public LayerType layerType;
	public Sprite sprite;
	
	public DataRender (Sprite sprite, LayerType layerType){
		this.sprite = sprite;
		this.layerType = layerType;
	}
}
