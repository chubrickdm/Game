package com.game.mesh.objects.singletons.special;

import com.game.GameSystem;

import com.game.addition.algorithms.aStar.Tile;
import com.game.addition.algorithms.aStar.TileType;
import com.game.addition.algorithms.aStar.algorithm.Graph;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.character.Direction;
import com.game.messages.GameMessage;

import java.util.ArrayList;
import java.util.LinkedList;

public class Level extends GameObject implements Graph <Tile>{
	private boolean withDiagonalNeighbors = true;
	private boolean withIgnoreFinish = false;
	private Tile finish;
	private LinkedList <LinkedList <Tile>> map;
	
	
	private static class LevelHolder{
		private final static Level instance = new Level ();
	}
	
	private Level (){ }
	
	
	public static Level getInstance (){
		return LevelHolder.instance;
	}
	
	public void setSize (int x, int y){
		map = new LinkedList <> ();
		for (int i = 0; i < x; i++){
			map.add (new LinkedList <> ());
			for (int j = 0; j < y; j++){
				map.get (i).add (new Tile (i, j, TileType.empty));
			}
		}
	}
	
	public void addWall (float x, float y){
		x++;
		y++;
		x -= GameSystem.INDENT_BETWEEN_SCREEN_LEVEL;
		
		int i = (int) (x / GameObject.UNIT);
		int j = (int) (y / (GameObject.UNIT * GameObject.ANGLE));
		/*if (i + 1 > map.size ()){
			i = map.size () - 1;
		}
		if (j + 1 > map.get (i).size ()){
			j = map.get (i).size () - 1;
		}*/
		map.get (i).remove (j);
		map.get (i).add (j, new Tile (i, j, TileType.wall));
	}
	
	public void moveBox (float oldX, float oldY, Direction direction){
		oldX++;
		oldY++;
		oldX -= GameSystem.INDENT_BETWEEN_SCREEN_LEVEL;
		int oldI = (int) (oldX / GameObject.UNIT);
		int oldJ = (int) (oldY / (GameObject.UNIT * GameObject.ANGLE));
		int newI = oldI;
		int newJ = oldJ;
		switch (direction){
		case forward:
			newJ++;
			break;
		case right:
			newI++;
			break;
		case back:
			newJ--;
			break;
		case left:
			newI--;
			break;
		}
		
		map.get (oldI).remove (oldJ);
		map.get (oldI).add (oldJ, new Tile (oldI, oldJ, TileType.empty));
		
		map.get (newI).remove (newJ);
		map.get (newI).add (newJ, new Tile (newI, newJ, TileType.wall));
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
	
	public void setFinish (Tile finish){
		this.finish = finish;
	}
	
	public void setWithIgnoreFinish (boolean withIgnoreFinish){
		this.withIgnoreFinish = withIgnoreFinish;
	}
	
	@Override
	public double heuristic (Tile begin, Tile end){
		return (int) (Math.pow (begin.x - end.x, 2) + Math.pow (begin.y - end.y, 2));
	}
	
	@Override
	public ArrayList <Tile> neighbors (Tile current){
		ArrayList <Tile> list = new ArrayList <> ();
		int x = (int) current.x;
		int y = (int) current.y;
		
		
		if (withDiagonalNeighbors){
			if (map.get (x + 1).get (y + 1).type != TileType.wall && map.get (x).get (y + 1).type != TileType.wall
					&& map.get (x + 1).get (y).type != TileType.wall){
				list.add (map.get (x + 1).get (y + 1));
			}
			if (map.get (x - 1).get (y + 1).type != TileType.wall && map.get (x).get (y + 1).type != TileType.wall
					&& map.get (x - 1).get (y).type != TileType.wall){
				list.add (map.get (x - 1).get (y + 1));
			}
			
			if (map.get (x + 1).get (y - 1).type != TileType.wall && map.get (x).get (y - 1).type != TileType.wall
					&& map.get (x + 1).get (y).type != TileType.wall){
				list.add (map.get (x + 1).get (y - 1));
			}
			if (map.get (x - 1).get (y - 1).type != TileType.wall && map.get (x).get (y - 1).type != TileType.wall
					&& map.get (x - 1).get (y).type != TileType.wall){
				list.add (map.get (x - 1).get (y - 1));
			}
		}
		
		if (map.get (x).get (y + 1).type != TileType.wall || (withIgnoreFinish && map.get (x).get (y + 1).equals (finish))){
			list.add (map.get (x).get (y + 1));
		}
		if (map.get (x).get (y - 1).type != TileType.wall || (withIgnoreFinish && map.get (x).get (y - 1).equals (finish))){
			list.add (map.get (x).get (y - 1));
		}
		
		if (map.get (x + 1).get (y).type != TileType.wall || (withIgnoreFinish && map.get (x + 1).get (y).equals (finish))){
			list.add (map.get (x + 1).get (y));
		}
		if (map.get (x - 1).get (y).type != TileType.wall || (withIgnoreFinish && map.get (x - 1).get (y).equals (finish))){
			list.add (map.get (x - 1).get (y));
		}
		
		return list;
	}
	
	@Override
	public int cost (Tile current, Tile next){
		return 1;
	}
	
	@Override
	public void update (){
		super.update ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		
	}
	
	@Override
	public void clear (){
		super.clear ();
	}
}