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
	
	private boolean firstTime = true;
	private boolean isVisibale = false;
	private DataRender dataRender;
	private NoBodyObject body;
	
	
	private void updateControl (){
		isVisibale = false;
		if (Gdx.input.isKeyPressed (Input.Keys.F)){
			if (firstTime){
				ObjectManager.getInstance ().addMessage (new FindSelCharacterMessage (this));
				firstTime = false;
			}
			isVisibale = true;
		}
	}
	
	private static class ActionWheelHolder{
		private final static ActionWheel instance = new ActionWheel ();
	}
	
	private ActionWheel (){
		body = new NoBodyObject ("core/assets/images/action_wheel.png", 0, 0, WHEEL_W, WHEEL_H);
		body.setOrigin (WHEEL_H / 2, WHEEL_H / 2);
		dataRender = new DataRender (body.sprite, LayerType.actionWheel);
	}
	
	
	public static ActionWheel getInstance (){
		return ActionWheelHolder.instance;
	}
	
	@Override
	public void update (){
		updateControl ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.characterMove){
			Character ch = (Character) message.object;
			body.setPosition (ch.getSpriteX () + ch.CHARACTER_W / 2,
					ch.getSpriteY () + ch.CHARACTER_H / 2);
		}
		else if (message.type == MessageType.characterSelected){
			Character ch = (Character) message.object;
			body.setPosition (ch.getSpriteX () + ch.CHARACTER_W / 2,
					ch.getSpriteY () + ch.CHARACTER_H / 2);
		}
	}
	
	@Override
	public void draw (){
		if (isVisibale){
			Render.getInstance ().addDataForRender (dataRender);
		}
	}
}
