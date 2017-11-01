package com.game.mesh.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.game.MyGame;
import com.game.mesh.objects.special.ObjectManager;
import com.game.messages.*;
import com.game.mesh.body.NoBodyObject;
import com.game.mesh.objects.character.Character;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class ActionWheel extends GameObject{
	public static final float percentPerTick = 2;
	public static final float WHEEL_W = UNIT * 2;
	public static final float WHEEL_H = UNIT * 2;
	
	private boolean isVisible = false;
	private float percentSize = 1;
	
	
	private void updateSizeAnimation (){
		if (Gdx.input.isKeyPressed (Input.Keys.F)){
			ObjectManager.getInstance ().addMessage (new GetPositionMessage (this));
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
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.returnPosition && message.objectType == ObjectType.character){
			ReturnPositionMessage msg = (ReturnPositionMessage) message;
			Character character = (Character) message.object;
			if (character.getIsSelected ()){
				body.setSpritePosition (msg.spriteX + Character.CHARACTER_W / 2,
						msg.spriteY + Character.CHARACTER_H / 2);
			}
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
		body.setOrigin (WHEEL_H / 2, WHEEL_H / 2);
		body.setScale (percentSize / 100);
		body.setSpritePosition (WHEEL_H / 2, WHEEL_H / 2);
	}
}
