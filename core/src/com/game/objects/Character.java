package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.messages.*;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;


public class Character implements GameObject{
	private static final float CHARACTER_W = 64;
	private static final float CHARACTER_H = 64;
	private static final float START_FIRST_X = 100;
	private static final float START_FIRST_Y = 100;
	private static final float START_SECOND_X = 300;
	private static final float START_SECOND_Y = 165;
	private static final float CHARACTER_SPEED = 100;
	private static final int   FRAME_COLS = 4;
	private static final int   FRAME_ROWS = 1;
	
	private boolean iSelected = false;
	private float deltaX;
	private float deltaY;
	private BodyObject body;
	private DataRender dataRender;
	
	
	private void inputControll (){
		deltaY = 0; deltaX = 0;
		if (Gdx.input.isKeyPressed (Input.Keys.D)){
			deltaX = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
		else if (Gdx.input.isKeyPressed (Input.Keys.A)){
			deltaX = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
		if (Gdx.input.isKeyPressed (Input.Keys.W)){
			deltaY = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
		else if (Gdx.input.isKeyPressed (Input.Keys.S)){
			deltaY = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
		if (deltaX != 0 || deltaY != 0){
			body.move (deltaX, deltaY);
			ObjectManager.getInstance ().addMessage (new MoveMessage (this,
					body.getX () - deltaX, body.getY () - deltaY, body));
		}
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (this));
		}
	}
	
	
	public Character (boolean iSelected){
		this.iSelected = iSelected;
		if (iSelected){
			body = new BodyObject ("core\\assets\\player.png", START_FIRST_X, START_FIRST_Y,
					CHARACTER_W, CHARACTER_H, FRAME_ROWS, FRAME_COLS);
		}
		else{
			body = new BodyObject ("core\\assets\\player.png", START_SECOND_X, START_SECOND_Y,
					CHARACTER_W, CHARACTER_H, FRAME_ROWS, FRAME_COLS);
		}
		dataRender = new DataRender (body.getSprite (), LayerType.character);
	}
	
	@Override
	public void update (){
		if (iSelected){
			inputControll ();
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
		dataRender.sprite = body.getSprite ();
		Render.getInstance ().addDataForRender (dataRender);
	}
}
