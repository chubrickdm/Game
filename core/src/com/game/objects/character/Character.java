package com.game.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.messages.*;
import com.game.objects.GameObject;
import com.game.objects.ObjectManager;
import com.game.objects.body.AnimatedBodyObject;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;


public class Character implements GameObject{
	public static final float CHARACTER_W = 64;
	public static final float CHARACTER_H = 64;
	public static final float START_FIRST_X = 100;
	public static final float START_FIRST_Y = 100;
	public static final float START_SECOND_X = 300;
	public static final float START_SECOND_Y = 165;
	public static final float CHARACTER_SPEED = 100;
	public static final int   FRAME_COLS = 4;
	public static final int   FRAME_ROWS = 1;
	
	public AnimatedBodyObject body;
	private CharacterInputControl inputControl;
	private boolean iSelected = false;
	private DataRender dataRender;
	
	
	public Character (boolean iSelected){
		inputControl = new CharacterInputControl (this);
		this.iSelected = iSelected;
		if (iSelected){
			body = new AnimatedBodyObject ("core\\assets\\player.png", START_FIRST_X, START_FIRST_Y,
					CHARACTER_W, CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.18f);
		}
		else{
			body = new AnimatedBodyObject ("core\\assets\\player.png", START_SECOND_X, START_SECOND_Y,
					CHARACTER_W, CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.18f);
		}
		dataRender = new DataRender (body.sprite, LayerType.character);
	}
	
	@Override
	public void update (){
		if (iSelected){
			inputControl.update ();
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.characterChange){
			if (iSelected){
				iSelected = false;
			}
			else{
				iSelected = true;
			}
		}
		else if (message.type == MessageType.movement && message.object != this){
			MoveMessage msg = (MoveMessage) message;
			if (body.intersects (msg.body)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, msg.oldX, msg.oldY));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == this){
			PushOutMessage msg = (PushOutMessage) message;
			body.setPosition (msg.whereX, msg.whereY);
		}
	}
	
	@Override
	public void draw (){
		body.updateCurrAnimationFrame ();
		body.sprite.setRotation (180 - inputControl.getMovementType () * 45);
		dataRender.sprite = body.sprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
}