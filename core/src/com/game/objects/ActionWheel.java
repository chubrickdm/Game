package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.messages.*;
import com.game.objects.body.NoBodyObject;
import com.game.objects.character.Character;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;


public class ActionWheel implements GameObject{
	public static final float WHEEL_W = 128 * ASPECT_RATIO;
	public static final float WHEEL_H = 128 * ASPECT_RATIO;
	
	private boolean useAnimation = false;
	private boolean isFirstPressure = true;
	private boolean isVisibale = false;
	private float percentIncrease = 1;
	private DataRender dataRender;
	private NoBodyObject body;
	private Character currCharacter;
	
	
	private void updateIncreaseAnimation (){
		if ((Gdx.input.isKeyPressed (Input.Keys.F)) && isFirstPressure){
			useAnimation = true;
		}
		else if (isFirstPressure){
			percentIncrease = 1;
			body.sprite.setScale (percentIncrease / 100);
		}
		
		if (useAnimation){
			if (percentIncrease >= 100){
				body.sprite.setScale (1);
				useAnimation = false;
				percentIncrease = 1;
			}
			else{
				body.sprite.setScale (percentIncrease / 100);
				percentIncrease += 1;
			}
		}
	}
	
	private void updateControl (){
		isVisibale = false;
		if (Gdx.input.isKeyPressed (Input.Keys.F)){
			if (isFirstPressure){
				ObjectManager.getInstance ().addMessage (new FindSelCharacterMessage (this));
				isFirstPressure = false;
			}
			isVisibale = true;
		}
		else{
			isFirstPressure = true;
		}
	}
	
	private static class ActionWheelHolder{
		private final static ActionWheel instance = new ActionWheel ();
	}
	
	private ActionWheel (){
		body = new NoBodyObject ("core/assets/images/action_wheel.png", 0, 0, WHEEL_W, WHEEL_H);
		body.setOrigin (WHEEL_H / 2, WHEEL_H / 2);
		body.sprite.setScale (percentIncrease / 100);
		dataRender = new DataRender (body.sprite, LayerType.actionWheel);
	}
	
	
	public static ActionWheel getInstance (){
		return ActionWheelHolder.instance;
	}
	
	@Override
	public void update (){
		//именно в таком порядке должны быть функции
		updateIncreaseAnimation ();
		updateControl ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.characterMove){
			currCharacter = (Character) message.object;
			body.setPosition (currCharacter.getSpriteX () + currCharacter.CHARACTER_W / 2,
					currCharacter.getSpriteY () + currCharacter.CHARACTER_H / 2);
		}
		else if (message.type == MessageType.characterSelected){
			currCharacter = (Character) message.object;
			body.setPosition (currCharacter.getSpriteX () + currCharacter.CHARACTER_W / 2,
					currCharacter.getSpriteY () + currCharacter.CHARACTER_H / 2);
		}
	}
	
	@Override
	public void draw (){
		if (isVisibale){
			Render.getInstance ().addDataForRender (dataRender);
		}
	}
}
