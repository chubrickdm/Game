package com.game.addition.algorithms.aStar.realisation;

import com.game.addition.algorithms.aStar.Node;

public class ConcreteNode extends Node{
	public int x;
	public int y;
	public TypeNode type = TypeNode.empty;
	
	
	public ConcreteNode (int x, int y){
		this.x = x;
		this.y = y;
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
