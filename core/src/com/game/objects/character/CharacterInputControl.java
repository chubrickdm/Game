package com.game.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.messages.CharacterChangeMessage;
import com.game.messages.MoveMessage;
import com.game.objects.ObjectManager;

import static com.game.objects.character.Character.CHARACTER_SPEED;

public class CharacterInputControl{
	private float deltaX;
	private float deltaY;
	private int movementType = 0;
	private Character character;
	
	
	public CharacterInputControl (Character character){
		this.character = character;
	}
	
	public void update (){
		deltaY = 0; deltaX = 0;
		int tmpI = movementType;
		movementType = -1;
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
				movementType--;
			}
		}
		else if (Gdx.input.isKeyPressed (Input.Keys.S)){
			deltaY = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
			if (movementType == -1){
				movementType = 4;
			}
			else{
				movementType++;
			}
		}
		if (deltaX != 0 || deltaY != 0){
			character.body.move (deltaX, deltaY);
			ObjectManager.getInstance ().addMessage (new MoveMessage (character,
					character.body.getX () - deltaX, character.body.getY () - deltaY, character.body));
		}
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (character));
		}
		
		if (movementType == -1){
			movementType = tmpI;
		}
	}
	
	public int getMovementType (){
		return movementType;
	}
}
