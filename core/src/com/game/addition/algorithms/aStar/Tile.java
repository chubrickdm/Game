package com.game.addition.algorithms.aStar;

import com.game.addition.algorithms.aStar.algorithm.Node;

public class Tile extends Node{
	public float x;
	public float y;
	public TileType type = TileType.empty;
	
	
	public Tile (){
		x = 0;
		y = 0;
	}
	
	public Tile (int x, int y, TileType type){
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals (Object obj){
		if (obj instanceof Tile){
			Tile tmpP = (Tile) obj;
			return tmpP.x == x && tmpP.y == y;
		}
		return false;
	}
}
