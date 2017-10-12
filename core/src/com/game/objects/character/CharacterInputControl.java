package com.game.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.MyGame;
import com.game.messages.CharacterChangeMessage;
import com.game.messages.MoveMessage;
import com.game.objects.ObjectManager;
import com.game.screens.MainMenuScreen;


public class CharacterInputControl{
	private float CHARACTER_SPEED;
	private float deltaX;
	private float deltaY;
	private int movementType = 0;
	private ActionType action;
	private Character character;
	
	
	public CharacterInputControl (Character character, float characterSpeed){
		CHARACTER_SPEED = characterSpeed;
		this.character = character;
	}
	
	public void update (){
		deltaY = 0; deltaX = 0;
		int tmpI = movementType;
		movementType = -1;
		action = ActionType.stand;
		if (Gdx.input.isKeyPressed (Input.Keys.D)){
			deltaX = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
			movementType = 2;
		}
		else if (Gdx.input.isKeyPressed (Input.Keys.A)){
			deltaX = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
			movementType = 6;
		}
		if (Gdx.input.isKeyPressed (Input.Keys.W)){
			deltaY = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
			if (movementType == -1){
				movementType = 0;
			}
			else{
				if (movementType == 2){
					movementType = 1;
				}
				else{
					movementType = 7;
				}
			}
		}
		else if (Gdx.input.isKeyPressed (Input.Keys.S)){
			deltaY = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
			if (movementType == -1){
				movementType = 4;
			}
			else{
				if (movementType == 2){
					movementType = 3;
				}
				else{
					movementType = 5;
				}
			}
		}
		if (deltaX != 0 || deltaY != 0){
			action = ActionType.movement;
			character.move (deltaX, deltaY);
			ObjectManager.getInstance ().addMessage (new MoveMessage (character,
					character.getBodyX () - deltaX, character.getBodyY () - deltaY, character.getBodyRectangle ()));
		}
		
		character.setAction (action);
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (character));
		}
		else if (Gdx.input.isKeyJustPressed (Input.Keys.ESCAPE)){
			MyGame.getInstance ().setScreen (new MainMenuScreen ());
		}
		
		if (movementType == -1){
			movementType = tmpI;
		}
	}
	
	public int getMovementType (){
		return movementType;
	}
}
