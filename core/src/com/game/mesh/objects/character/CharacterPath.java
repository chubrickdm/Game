package com.game.mesh.objects.character;

import com.game.GameSystem;
import com.game.addition.algorithms.aStar.realisation.ConcreteNode;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.ComeToMessage;

import java.util.ArrayList;

public class CharacterPath extends CharacterControl{
	private int iterator;
	private Direction direction;
	private ConcreteNode next;
	private ConcreteNode current;
	private CharacterControl control;
	private ArrayList<ConcreteNode> path;
	
	
	private static class CharacterPathHolder{
		private final static CharacterPath instance = new CharacterPath ();
	}
	
	private CharacterPath (){
		direction = Direction.forward;
	}
	
	
	public static CharacterPath getInstance (){
		return CharacterPathHolder.instance;
	}
	
	public void setPath (ArrayList <ConcreteNode> path, CharacterControl control){
		this.control = control;
		iterator = 0;
		nextIteration ();
	}
	
	public Direction nextIteration (){
		iterator++;
		current.x = control.character.getBodyX () + control.character.getBodyW () / 2;
		current.y = control.character.getBodyY () + control.character.getBodyH () / 2;
		next.x = (path.get (iterator).x + 0.5f) * GameObject.UNIT + GameSystem.INDENT_BETWEEN_SCREEN_LEVEL;
		next.y = (path.get (iterator).y + 0.5f) * GameObject.UNIT * GameObject.ANGLE;
		
		boolean moveX = Math.abs (next.x - current.x) > 5;
		boolean moveY = Math.abs (next.y - current.y) > 5;
		if (moveX){
			if (next.x > current.x){
				keyDPressed ();
			}
			else{
				keyAPressed ();
			}
			current.x += control.deltaX;
		}
		if (moveY){
			if (next.y > current.y){
				keyWPressed ();
			}
			else{
				keySPressed ();
			}
			current.y += control.deltaY;
		}
		if (!moveX && !moveY){
			if (iterator == path.size () - 1){
				control.movedByComputer = false;
				if (control.character.goToObject){
					control.character.goToObject = false;
					ObjectManager.getInstance ().addMessage (new ComeToMessage (control.character));
				}
			}
			else{
				nextIteration ();
			}
		}
		
		return direction;
	}
}
