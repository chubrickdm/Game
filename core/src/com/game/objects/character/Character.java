package com.game.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.MyGame;
import com.game.math.BodyRectangle;
import com.game.messages.*;
import com.game.objects.GameObject;
import com.game.objects.ObjectManager;
import com.game.objects.body.AnimatedBodyObject;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;
import com.game.screens.MainMenuScreen;


public class Character implements GameObject{
	public static final float CHARACTER_W = 64 * ASPECT_RATIO;
	public static final float CHARACTER_H = 64 * ASPECT_RATIO;
	public static final float BODY_CHARACTER_W = 3 * CHARACTER_W / 4 * ASPECT_RATIO;
	public static final float BODY_CHARACTER_H = 3 * CHARACTER_H / 4 * ASPECT_RATIO;
	public static final float CHARACTER_SPEED = 100 * ASPECT_RATIO;
	public static final int FRAME_COLS = 4;
	public static final int FRAME_ROWS = 1;
	
	private boolean iSelected = false;
	private float deltaX;
	private float deltaY;
	private float time = 0;
	private int angleMove = 0;
	private ActionType action;
	private DataRender dataRender;
	private AnimatedBodyObject body;
	
	
	private void keyWPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.S)){
			if (angleMove == -1){
				angleMove = 0;
				deltaY = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
			}
			else if (angleMove == 2){
				deltaY = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
				angleMove = 1;
			}
			else if (angleMove == 6){
				deltaY = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
				angleMove = 7;
			}
		}
	}
	
	private void keyDPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.A)){
			if (angleMove == -1){
				angleMove = 2;
				deltaX = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
			}
			else if (angleMove == 0){
				deltaX = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
				angleMove = 1;
			}
			else if (angleMove == 4){
				deltaX = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
				angleMove = 3;
			}
		}
	}
	
	private void keySPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.W)){
			if (angleMove == -1){
				angleMove = 4;
				deltaY = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
			}
			else if (angleMove == 2){
				deltaY = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
				angleMove = 3;
			}
			else if (angleMove == 4){
				deltaY = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
				angleMove = 5;
			}
		}
	}
	
	private void keyAPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.D)){
			if (angleMove == -1){
				angleMove = 6;
				deltaX = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
			}
			else if (angleMove == 0){
				deltaX = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
				angleMove = 7;
			}
			else if (angleMove == 4){
				deltaX = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
				angleMove = 5;
			}
		}
	}
	
	private void updateControl (){
		deltaY = 0;
		deltaX = 0;
		int tmpI = angleMove;
		angleMove = -1;
		action = ActionType.stand;
		if (Gdx.input.isKeyPressed (Input.Keys.W)) keyWPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.D)) keyDPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.S)) keySPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.A)) keyAPressed ();
		
		if (deltaX != 0 || deltaY != 0){
			action = ActionType.movement;
			body.move (deltaX, deltaY);
			ObjectManager.getInstance ().addMessage (new MoveMessage (this, body.getBodyX () - deltaX, body.getBodyY () - deltaY, body.bodyRect));
		}
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB))
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (this));
		
		if (angleMove == -1) angleMove = tmpI;
	}
	
	
	public Character (boolean iSelected, float x, float y){
		action = ActionType.stand;
		this.iSelected = iSelected;
		if (iSelected){
			body = new AnimatedBodyObject ("core\\assets\\images\\player.png", x, y, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		}
		else{
			body = new AnimatedBodyObject ("core\\assets\\images\\player.png", x, y, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		}
		dataRender = new DataRender (body.sprite, LayerType.character);
	}
	
	@Override
	public void update (){
		if (iSelected){
			updateControl ();
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
		body.sprite.setRotation (180 - angleMove * 45);
		dataRender.sprite = body.sprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
}