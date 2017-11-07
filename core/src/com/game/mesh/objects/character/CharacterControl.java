package com.game.mesh.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.CharacterChangeMessage;
import com.game.messages.MoveMessage;

public class CharacterControl extends CharacterAddition{
	private static final float CHARACTER_SPEED = 80 * GameObject.ASPECT_RATIO;
	
	private float deltaX = 0;
	private float deltaY = 0;
	private ActionType action;
	
	
	private void keyWPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.S)){
			action = ActionType.forwardWalk;
			deltaY = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void keySPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.W)){
			action = ActionType.backWalk;
			deltaY = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void keyDPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.A)){
			if (!Gdx.input.isKeyPressed (Input.Keys.W)){
				action = ActionType.rightWalk;
			}
			deltaX = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void keyAPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.D)){
			if (!Gdx.input.isKeyPressed (Input.Keys.W)){
				action = ActionType.leftWalk;
			}
			deltaX = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void updateControl (){
		deltaX = 0; deltaY = 0;
		if (Gdx.input.isKeyPressed (Input.Keys.W)) keyWPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.S)) keySPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.D)) keyDPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.A)) keyAPressed ();
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (character));
		}
		else if (deltaX != 0 || deltaY != 0){
			ObjectManager.getInstance ().addMessage (new MoveMessage (this, deltaX, deltaY, body.getBodyX (),
					body.getBodyY (), body.getSpriteX (), body.getSpriteY (), body.getBodyW (), body.getBodyH ()));
			body.move (deltaX, deltaY);
		}
	}
	
	
	public CharacterControl (Character character){
		this.character = character;
	}
	
	@Override
	public void update (){
		updateControl ();
	}
}
