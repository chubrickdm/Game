package com.game.mesh.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.game.mesh.objects.special.ObjectManager;
import com.game.messages.*;
import com.game.mesh.body.NoBodyObject;
import com.game.mesh.objects.character.Character;
import com.game.render.*;

public class ActionWheel extends GameObject{
	public static final float percentPerTick = 2;
	public static final float WHEEL_W = UNIT * 2;
	public static final float WHEEL_H = UNIT * 2;
	
	private boolean isFirstUpdate = true;
	private boolean isVisible = false;
	private float deltaX;
	private float deltaY;
	private float percentSize = 1;
	
	
	private void updateSizeAnimation (){
		if (Gdx.input.isKeyPressed (Input.Keys.F)){
			isVisible = true;
			if (percentSize >= 100){
				body.setScale (1);
			}
			else{
				body.setScale (percentSize / 100);
				percentSize += percentPerTick;
			}
		}
		else{
			if (percentSize <= 1 + percentPerTick){
				isVisible = false;
				body.setScale (percentSize / 100);
			}
			else{
				isVisible = true;
				body.setScale (percentSize / 100);
				percentSize -= percentPerTick;
			}
		}
	}
	
	private static class ActionWheelHolder{
		private final static ActionWheel instance = new ActionWheel ();
	}
	
	private ActionWheel (){
		objectType = ObjectType.actionWheel;
		body = new NoBodyObject ("core/assets/images/action_wheel.png", 0, 0, WHEEL_W, WHEEL_H);
		body.setOrigin (WHEEL_H / 2, WHEEL_H / 2);
		body.setScale (percentSize / 100);
		body.setSpritePosition (WHEEL_H / 2, WHEEL_H / 2);
		dataRender = new DataRender (body.sprite, LayerType.actionWheel);
	}
	
	
	public static ActionWheel getInstance (){
		return ActionWheelHolder.instance;
	}
	
	@Override
	public void update (){
		updateSizeAnimation ();
		if (isFirstUpdate){
			ObjectManager.getInstance ().addMessage (new GetPositionMessage ());
			isFirstUpdate = false;
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			body.move (msg.deltaX, msg.deltaY);
			deltaX = msg.deltaX;
			deltaY = msg.deltaY;
		}
		else if (message.type == MessageType.returnPosition && message.objectType == ObjectType.character){
			ReturnPositionMessage msg = (ReturnPositionMessage) message;
			Character character = (Character) message.object;
			if (character.getIsSelected ()){
				body.setSpritePosition (msg.spriteX + Character.CHARACTER_W / 2, msg.spriteY + Character.CHARACTER_H / 2);
			}
		}
		else if (message.type == MessageType.pushOut && message.objectType == ObjectType.character){
			body.move (-deltaX, -deltaY);
			deltaX = 0;
			deltaY = 0;
		}
		else if (message.type == MessageType.characterSelected){
			CharacterSelectedMessage msg = (CharacterSelectedMessage) message;
			body.setSpritePosition (msg.spriteX + Character.CHARACTER_W / 2, msg.spriteY + Character.CHARACTER_H / 2);
		}
	}
	
	@Override
	public void draw (){
		if (isVisible){
			Render.getInstance ().addDataForRender (dataRender);
		}
	}
	
	@Override
	public void clear (){
		isFirstUpdate = true;
		body.setOrigin (WHEEL_H / 2, WHEEL_H / 2);
		body.setScale (percentSize / 100);
		body.setSpritePosition (WHEEL_H / 2, WHEEL_H / 2);
	}
}
