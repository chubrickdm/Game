package com.game.addition.algorithms.aStar.realisation;

import com.game.addition.algorithms.aStar.Node;

public class ConcreteNode extends Node{
	public float x;
	public float y;
	public TypeNode type = TypeNode.empty;
	
	
	public ConcreteNode (){
		x = 0;
		y = 0;
	}
	
	public ConcreteNode (int x, int y, TypeNode type){
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals (Object obj){
		if (obj instanceof ConcreteNode){
			ConcreteNode tmpP = (ConcreteNode) obj;
			return tmpP.x == x && tmpP.y == y;
		}
		return false;
	}
}
