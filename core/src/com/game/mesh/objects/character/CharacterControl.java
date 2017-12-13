package com.game.mesh.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.game.GameSystem;
import com.game.addition.algorithms.aStar.realisation.ConcreteNode;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.State;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.CharacterChangeMessage;
import com.game.messages.ComeToMessage;
import com.game.messages.DisconnectMessage;
import com.game.messages.MoveMessage;

import java.util.ArrayList;

public class CharacterControl extends Character{
	private static final float CHARACTER_SPEED = 80 * ASPECT_RATIO;
	
	protected boolean movedByComputer = false;
	protected float deltaX = 0;
	protected float deltaY = 0;
	private int iterator;
	private ConcreteNode next;
	private ConcreteNode current;
	protected Character character;
	private ArrayList <ConcreteNode> path;
	
	
	private void nextIteration (){
		iterator++;
		current.x = character.getBodyX () + character.getBodyW () / 2;
		current.y = character.getBodyY () + character.getBodyH () / 2;
		next.x = (path.get (iterator).x + 0.5f) * GameObject.UNIT + GameSystem.INDENT_BETWEEN_SCREEN_LEVEL;
		next.y = (path.get (iterator).y + 0.5f) * GameObject.UNIT * GameObject.ANGLE;
	}
	
	private void updatedMoveByComputer (){
		boolean moveX = Math.abs (next.x - current.x) > CHARACTER_SPEED * 0.03;
		boolean moveY = Math.abs (next.y - current.y) > CHARACTER_SPEED * 0.03;
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
				path.clear ();
				movedByComputer = false;
				if (character.goToObject){
					character.goToObject = false;
					ObjectManager.getInstance ().addMessage (new ComeToMessage (character));
				}
				else{
					deltaX += next.x - current.x;
					deltaY += next.y - current.y;
				}
			}
			else{
				nextIteration ();
			}
		}
	}
	
	protected void keyWPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.S)){
			character.currentDirection = Direction.forward;
			deltaY = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	protected void keySPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.W)){
			character.currentDirection = Direction.back;
			deltaY = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	protected void keyDPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.A)){
			if (!Gdx.input.isKeyPressed (Input.Keys.W)){
				character.currentDirection = Direction.right;
			}
			deltaX = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	protected void keyAPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.D)){
			if (!Gdx.input.isKeyPressed (Input.Keys.W)){
				character.currentDirection = Direction.left;
			}
			deltaX = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void updateMoveControl (){
		character.state = State.stand;
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
		
		if (!movedByComputer && character.goToObject){
			character.goToObject = false;
		}
		
		if (movedByComputer){
			updatedMoveByComputer ();
		}
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			movedByComputer = false;
			character.isSelected = false;
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (character));
		}
		else if (deltaX != 0 || deltaY != 0){
			character.state = State.move;
			ObjectManager.getInstance ().addMessage (new MoveMessage (character, deltaX, deltaY, character.getBodyX (), character.getBodyY (), character.getSpriteX (), character.getSpriteY (), character.getBodyW (), character.getBodyH ()));
			character.move (deltaX, deltaY);
		}
	}
	
	private void updatePushControl (){
		character.state = State.abut;
		deltaX = 0;
		deltaY = 0;
		
		switch (character.currentDirection){
		case forward:
			if (!movedByComputer && Gdx.input.isKeyPressed (Input.Keys.W)){
				movedByComputer = true;
				iterator = -1;
				current.x = character.getBodyX () + character.getBodyW () / 2;
				current.y = character.getBodyY () + character.getBodyH () / 2;
				next.x = current.x;
				next.y = current.y + GameObject.UNIT * GameObject.ANGLE;
			}
			break;
		case right:
			if (!movedByComputer && Gdx.input.isKeyPressed (Input.Keys.D)){
				movedByComputer = true;
				iterator = -1;
				current.x = character.getBodyX () + character.getBodyW () / 2;
				current.y = character.getBodyY () + character.getBodyH () / 2;
				next.x = current.x + GameObject.UNIT;
				next.y = current.y;
			}
			break;
		case back:
			if (!movedByComputer && Gdx.input.isKeyPressed (Input.Keys.S)){
				movedByComputer = true;
				iterator = -1;
				current.x = character.getBodyX () + character.getBodyW () / 2;
				current.y = character.getBodyY () + character.getBodyH () / 2;
				next.x = current.x;
				next.y = current.y - GameObject.UNIT * GameObject.ANGLE;
			}
			break;
		case left:
			if (!movedByComputer && Gdx.input.isKeyPressed (Input.Keys.A)){
				movedByComputer = true;
				iterator = -1;
				current.x = character.getBodyX () + character.getBodyW () / 2;
				current.y = character.getBodyY () + character.getBodyH () / 2;
				next.x = current.x - GameObject.UNIT;
				next.y = current.y;
			}
			break;
		}
		
		if (movedByComputer){
			updatedMoveByComputer ();
		}
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			character.isSelected = false;
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (character));
		}
		else if (Gdx.input.isKeyJustPressed (Input.Keys.E) && !movedByComputer){
			character.state = State.stand;
			ObjectManager.getInstance ().addMessage (new DisconnectMessage (character));
		}
		else if (deltaX != 0 || deltaY != 0){
			character.state = State.push;
			ObjectManager.getInstance ().addMessage (new MoveMessage (character, deltaX, deltaY, character.getBodyX (), character.getBodyY (), character.getSpriteX (), character.getSpriteY (), character.getBodyW (), character.getBodyH ()));
			character.move (deltaX, deltaY);
		}
	}
	
	
	public CharacterControl (){}
	
	public CharacterControl (Character character){
		next = new ConcreteNode ();
		current = new ConcreteNode ();
		this.character = character;
	}
	
	public void setPath (ArrayList <ConcreteNode> path){
		movedByComputer = true;
		
		this.path = path;
		iterator = 0;
		nextIteration ();
	}
	
	@Override
	public void update (){
		if (character.isSelected && (character.state == State.stand || character.state == State.move)){
			updateMoveControl ();
		}
		else if (character.isSelected && (character.state == State.abut || character.state == State.push)){
			updatePushControl ();
		}
	}
	
	@Override
	public void clear (){
		movedByComputer = false;
		path = null;
	}
}