package com.game.objects.character;

import com.badlogic.gdx.Gdx;
import com.game.math.BodyRectangle;
import com.game.messages.*;
import com.game.objects.GameObject;
import com.game.objects.ObjectManager;
import com.game.objects.body.AnimatedBodyObject;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;


public class Character implements GameObject{
	public static final float CHARACTER_W = 64 * ASPECT_RATIO;
	public static final float CHARACTER_H = 64 * ASPECT_RATIO;
	public static final float BODY_CHARACTER_W = 3 * CHARACTER_W / 4 * ASPECT_RATIO;
	public static final float BODY_CHARACTER_H = 3 * CHARACTER_H / 4 * ASPECT_RATIO;
	public static final float START_FIRST_X = 200 * ASPECT_RATIO;
	public static final float START_FIRST_Y = 200 * ASPECT_RATIO;
	public static final float START_SECOND_X = (Gdx.graphics.getWidth () - 200 - CHARACTER_W) * ASPECT_RATIO;
	public static final float START_SECOND_Y = 200 * ASPECT_RATIO;
	public static final float CHARACTER_SPEED = 100 * ASPECT_RATIO;
	public static final int   FRAME_COLS = 4;
	public static final int   FRAME_ROWS = 1;
	
	private ActionType action;
	private AnimatedBodyObject body;
	private float time = 0;
	private CharacterInputControl inputControl;
	private boolean iSelected = false;
	private DataRender dataRender;
	
	
	public Character (boolean iSelected){
		action = ActionType.stand;
		inputControl = new CharacterInputControl (this, CHARACTER_SPEED);
		this.iSelected = iSelected;
		if (iSelected){
			body = new AnimatedBodyObject ("core\\assets\\player.png", START_FIRST_X, START_FIRST_Y,
					CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H, FRAME_ROWS,
					FRAME_COLS, 0.15f);
		}
		else{
			body = new AnimatedBodyObject ("core\\assets\\player.png", START_SECOND_X, START_SECOND_Y,
					CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H, FRAME_ROWS,
					FRAME_COLS, 0.15f);
		}
		dataRender = new DataRender (body.sprite, LayerType.character);
	}
	
	public void setAction (ActionType type){
		action = type;
	}
	
	public void move (float deltaX, float deltaY){
		body.move (deltaX, deltaY);
	}
	
	public float getBodyX (){
		return body.getBodyX ();
	}
	
	public float getBodyY (){
		return body.getBodyY ();
	}
	
	public BodyRectangle getBodyRectangle (){
		return body.bodyRect;
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
				action = ActionType.stand;
			}
			else{
				iSelected = true;
			}
		}
		else if (message.type == MessageType.movement && message.object != this){
			MoveMessage msg = (MoveMessage) message;
			if (body.intersects (msg.bodyRectangle)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, msg.oldX, msg.oldY));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == this){
			PushOutMessage msg = (PushOutMessage) message;
			body.setBodyPosition (msg.whereX, msg.whereY);
		}
	}
	
	@Override
	public void draw (){
		if (action == ActionType.movement){
			time += Gdx.graphics.getDeltaTime ();
			body.updateCurrAnimationFrame (time);
		}
		else{
			body.updateCurrAnimationFrame (0);
		}
		body.sprite.setOriginCenter ();
		body.sprite.setRotation (180 - inputControl.getMovementType () * 45);
		dataRender.sprite = body.sprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
}