package com.game.mesh.objects.character.first;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.mesh.objects.character.ActionType;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.CharacterChangeMessage;
import com.game.messages.MoveMessage;

public class FirstControl extends FirstBody{
	private static final float CHARACTER_SPEED = 80 * ASPECT_RATIO;
	
	private FirstBody first;
	
	
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
		isMove = false;
		deltaX = 0; deltaY = 0;
		if (Gdx.input.isKeyPressed (Input.Keys.W)) keyWPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.S)) keySPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.D)) keyDPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.A)) keyAPressed ();
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			isSelected = false;
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (first));
		}
		else if (deltaX != 0 || deltaY != 0){
			isMove = true;
			ObjectManager.getInstance ().addMessage (new MoveMessage (first, deltaX, deltaY, bodyFirst.getBodyX (),
					bodyFirst.getBodyY (), bodyFirst.getSpriteX (), bodyFirst.getSpriteY (), bodyFirst.getBodyW (),
					bodyFirst.getBodyH ()));
			bodyFirst.move (deltaX, deltaY);
		}
	}
	
	
	public FirstControl (FirstBody first){
		this.first = first;
	}
	
	@Override
	public void update (){
		if (isSelected && !isFall){
			updateControl ();
		}
	}
}
