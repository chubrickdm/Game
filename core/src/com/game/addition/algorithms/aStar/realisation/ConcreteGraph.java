package com.game.addition.algorithms.aStar.realisation;

import com.game.GameSystem;

import com.game.addition.algorithms.aStar.Graph;
import com.game.mesh.objects.GameObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class ConcreteGraph implements Graph <ConcreteNode>{
	private boolean withDiagonalNeighbors = true;
	private LinkedList <LinkedList <ConcreteNode>> map;
	
	
	public void setSize (int x, int y){
		map = new LinkedList <> ();
		for (int i = 0; i < x; i++){
			map.add (new LinkedList <> ());
			for (int j = 0; j < y; j++){
				map.get (i).add (new ConcreteNode (i, j, TypeNode.empty));
			}
		}
	}
	
	public void addWall (float x, float y){
		x++;
		y++;
		x -= GameSystem.INDENT_BETWEEN_SCREEN_LEVEL;
		
		int i = (int) (x / GameObject.UNIT);
		int j = (int) (y / (GameObject.UNIT * GameObject.ANGLE));
		map.get (i).remove (j);
		map.get (i).add (j, new ConcreteNode (i, j, TypeNode.wall));
	}
	
	public void addInvisibleWall (float x, float y, float w, float h){
		for (int i = 0; i <= w / GameObject.UNIT; i++){
			for (int j = 0; j < h / (GameObject.UNIT * GameObject.ANGLE); j++){
				addWall (x + i * GameObject.UNIT, y + j * (GameObject.UNIT * GameObject.ANGLE));
			}
		}
	}
	
	public void setWithDiagonalNeighbors (boolean withDiagonalNeighbors){
		this.withDiagonalNeighbors = withDiagonalNeighbors;
	}
	
	@Override
	public double heuristic (ConcreteNode begin, ConcreteNode end){
		return (int) (Math.pow (begin.x - end.x, 2) + Math.pow (begin.y - end.y, 2));
	}
	
	@Override
	public ArrayList <ConcreteNode> neighbors (ConcreteNode current){
		ArrayList <ConcreteNode> list = new ArrayList <> ();
		int x = (int) current.x;
		int y = (int) current.y;
		
		if (withDiagonalNeighbors){
			for (int i = -1; i < 2 && x > 0 && y > 0; i++){
				if (map.get (x + i).get (y - 1).type != TypeNode.wall){
					list.add (map.get (x + i).get (y - 1));
				}
			}
			for (int i = -1; i < 2 && x > 0 && y < map.get (0).size () - 1; i++){
				if (map.get (x + i).get (y + 1).type != TypeNode.wall){
					list.add (map.get (x + i).get (y + 1));
				}
			}
		}
		else{
			if (map.get (x).get (y + 1).type != TypeNode.wall){
				list.add (map.get (x).get (y + 1));
			}
			if (map.get (x).get (y - 1).type != TypeNode.wall){
				list.add (map.get (x).get (y - 1));
			}
		}
		
		if (map.get (x + 1).get (y).type != TypeNode.wall){
			list.add (map.get (x + 1).get (y));
		}
		if (map.get (x - 1).get (y).type != TypeNode.wall){
			list.add (map.get (x - 1).get (y));
		}
		
		return list;
	}
	
	@Override
	public int cost (ConcreteNode current, ConcreteNode next){
		return 1;
	}
}