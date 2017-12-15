package com.game.mesh.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.game.addition.algorithms.aStar.ConcreteNode;
import com.game.mesh.objects.State;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.CharacterChangeMessage;
import com.game.messages.DisconnectMessage;
import com.game.messages.MoveMessage;

import java.util.ArrayList;

public class CharacterControl extends Character{
	protected static final float CHARACTER_SPEED = 80 * ASPECT_RATIO;
	
	protected boolean movedByComputer = false;
	protected float deltaX = 0;
	protected float deltaY = 0;
	protected Character character;
	
	private CharacterComputerControl computerControl;
	
	
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
			computerControl.updatedMoveByComputer ();
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
		
		computerControl.updatePushControl (this);
		
		if (movedByComputer){
			computerControl.updatedMoveByComputer ();
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
		this.character = character;
		computerControl = new CharacterComputerControl (this);
	}
	
	public void setPath (ArrayList <ConcreteNode> path){
		computerControl.setPath (path);
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
		computerControl.clear ();
	}
}