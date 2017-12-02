package com.game.mesh.objects.character;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.GameSystem;
import com.game.addition.algorithms.aStar.realisation.ConcreteNode;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.CharacterChangeMessage;
import com.game.messages.MoveMessage;

import java.util.ArrayList;

public class CharacterControl extends Character{
	private static final float CHARACTER_SPEED = 80 * ASPECT_RATIO;
	
	private boolean movedByComputer = false;
	private float deltaX = 0;
	private float deltaY = 0;
	private int iterator;
	private ConcreteNode next;
	private ConcreteNode current;
	private Character character;
	private ArrayList <ConcreteNode> path;
	
	
	private void nextIteration (){
		iterator++;
		current.x = character.getBodyX () + character.getBodyW () / 2;
		current.y = character.getBodyY () + character.getBodyH () / 2;
		next.x = (path.get (iterator).x + 0.5f) * GameObject.UNIT + GameSystem.INDENT_BETWEEN_SCREEN_LEVEL;
		next.y = (path.get (iterator).y + 0.5f) * GameObject.UNIT * GameObject.ANGLE;
		//next.x = (path.get (iterator).x - path.get (iterator - 1).x) * GameObject.UNIT + current.x;
		//next.y = (path.get (iterator).y - path.get (iterator - 1).y) * GameObject.UNIT * GameObject.ANGLE + current.y;
	}
	
	private void updatedMoveByComputer (){
		boolean moveX = Math.abs (next.x - current.x) > 5;
		boolean moveY = Math.abs (next.y - current.y) > 5;
		if (moveX){
			if (next.x > current.x){
				keyDPressed ();
			}
			else{
				keyAPressed ();
			}
			current.x += deltaX;
		}
		if (moveY){
			if (next.y > current.y){
				keyWPressed ();
			}
			else{
				keySPressed ();
			}
			current.y += deltaY;
		}
		if (!moveX && !moveY){
			if (iterator == path.size () - 1){
				System.out.println ("End.");
				movedByComputer = false;
			}
			else{
				System.out.println ("Next edge.");
				nextIteration ();
			}
		}
	}
	
	private void keyWPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.S)){
			character.action = ActionType.forwardWalk;
			deltaY = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void keySPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.W)){
			character.action = ActionType.backWalk;
			deltaY = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void keyDPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.A)){
			if (!Gdx.input.isKeyPressed (Input.Keys.W)){
				character.action = ActionType.rightWalk;
			}
			deltaX = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void keyAPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.D)){
			if (!Gdx.input.isKeyPressed (Input.Keys.W)){
				character.action = ActionType.leftWalk;
			}
			deltaX = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void updateControl (){
		character.isMove = false;
		deltaX = 0;
		deltaY = 0;
		if (Gdx.input.isKeyPressed (Input.Keys.W)){
			keyWPressed ();
			movedByComputer = false;
		}
		if (Gdx.input.isKeyPressed (Input.Keys.S)){
			keySPressed ();
			movedByComputer = false;
		}
		if (Gdx.input.isKeyPressed (Input.Keys.D)){
			keyDPressed ();
			movedByComputer = false;
		}
		if (Gdx.input.isKeyPressed (Input.Keys.A)){
			keyAPressed ();
			movedByComputer = false;
		}
		
		if (movedByComputer){
			updatedMoveByComputer ();
		}
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			character.isSelected = false;
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (character));
		}
		else if (deltaX != 0 || deltaY != 0){
			character.isMove = true;
			ObjectManager.getInstance ().addMessage (new MoveMessage (character, deltaX, deltaY, character.getBodyX (), character.getBodyY (), character.getSpriteX (), character.getSpriteY (), character.getBodyW (), character.getBodyH ()));
			character.move (deltaX, deltaY);
		}
	}
	
	
	public CharacterControl (Character character){
		next = new ConcreteNode ();
		current = new ConcreteNode ();
		this.character = character;
	}
	
	public void setPath (ArrayList <ConcreteNode> path){
		this.path = path;
		iterator = 0;
		nextIteration ();
		
		movedByComputer = true;
	}
	
	@Override
	public void update (){
		if (character.isSelected && !character.isFall && !character.isChoke){
			updateControl ();
		}
	}
}
