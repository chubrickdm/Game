package com.game.mesh.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.CharacterChangeMessage;
import com.game.messages.MoveMessage;

public class CharacterControl extends Character{
	private static final float CHARACTER_SPEED = 80 * ASPECT_RATIO;
	
	private float deltaX = 0;
	private float deltaY = 0;
	private Character character;
	
	
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
		deltaX = 0; deltaY = 0;
		if (Gdx.input.isKeyPressed (Input.Keys.W)) keyWPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.S)) keySPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.D)) keyDPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.A)) keyAPressed ();
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			character.isSelected = false;
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (character));
		}
		else if (deltaX != 0 || deltaY != 0){
			character.isMove = true;
			ObjectManager.getInstance ().addMessage (new MoveMessage (character, deltaX, deltaY, character.getBodyX (),
					character.getBodyY (), character.getSpriteX (), character.getSpriteY (), character.getBodyW (),
					character.getBodyH ()));
			character.move (deltaX, deltaY);
		}
	}
	
	
	public CharacterControl (Character character){
		this.character = character;
	}
	
	@Override
	public void update (){
		if (character.isSelected && !character.isFall){
			updateControl ();
		}
	}
}
