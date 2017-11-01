package com.game.mesh.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.game.GameSystem;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.GameObject;
import com.game.messages.*;
import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.body.NoSpriteObject;
import com.game.mesh.objects.special.ObjectManager;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class Character extends GameObject{
	public static final float CHARACTER_W = UNIT;
	public static final float CHARACTER_H = UNIT;
	public static final float BODY_CHARACTER_W = 3 * CHARACTER_W / 4 * ASPECT_RATIO;
	public static final float BODY_CHARACTER_H = 3 * CHARACTER_H / 4 * ASPECT_RATIO;
	public static final float CHARACTER_SPEED = 100 * ASPECT_RATIO;
	public static final int FRAME_COLS = 4;
	public static final int FRAME_ROWS = 1;
	
	
	
	private boolean isSelected = false;
	private boolean isPushOut = false;
	private float deltaX = 0;
	private float deltaY = 0;
	private float time = 0;
	private int angleMove = 0;
	private CharacterName name = CharacterName.unknown;
	private ActionType action;
	private Sprite currSprite;
	private ObjectAnimation moveAnimation;
	
	
	private void keyWPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.S)){
			if (angleMove == -1){
				angleMove = 0;
			}
			else if (angleMove == 2){
				angleMove = 1;
			}
			else if (angleMove == 6){
				angleMove = 7;
			}
			deltaY = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void keyDPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.A)){
			if (angleMove == -1){
				angleMove = 2;
			}
			else if (angleMove == 0){
				angleMove = 1;
			}
			else if (angleMove == 4){
				angleMove = 3;
			}
			deltaX = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void keySPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.W)){
			if (angleMove == -1){
				angleMove = 4;
			}
			else if (angleMove == 2){
				angleMove = 3;
			}
			else if (angleMove == 4){
				angleMove = 5;
			}
			deltaY = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void keyAPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.D)){
			if (angleMove == -1){
				angleMove = 6;
			}
			else if (angleMove == 0){
				angleMove = 7;
			}
			else if (angleMove == 4){
				angleMove = 5;
			}
			deltaX = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void updateControl (){
		int tmpI = angleMove;
		deltaY = 0;
		deltaX = 0;
		angleMove = -1;
		action = ActionType.stand;
		if (Gdx.input.isKeyPressed (Input.Keys.W))
			keyWPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.D))
			keyDPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.S))
			keySPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.A))
			keyAPressed ();
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (this));
		}
		else if (deltaX != 0 || deltaY != 0){
			action = ActionType.movement;
			body.move (deltaX, deltaY);
			ObjectManager.getInstance ().addMessage (new MoveMessage (this, deltaX, deltaY,
					body.getBodyX () - deltaX, body.getBodyY () - deltaY,
					body.getSpriteX () - deltaX, body.getSpriteY () - deltaY, body.getBodyW (),
					body.getBodyH ()));
		}
		
		
		if (angleMove == -1){
			angleMove = tmpI;
		}
	}
	
	private void updateMoveAnimation (){
		if (action == ActionType.movement && !isPushOut){
			time += Gdx.graphics.getDeltaTime ();
			currSprite = moveAnimation.getCurrSprite (time);
			body.setBodyPosition (body.getBodyX (), body.getBodyY ());
		}
		else{
			currSprite = moveAnimation.getCurrSprite (0);
			body.setBodyPosition (body.getBodyX (), body.getBodyY ());
		}
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
		currSprite.setOriginCenter ();
		currSprite.setRotation (180 - angleMove * 45);
	}
	
	
	public Character (float x, float y){
		objectType = ObjectType.character;
		action = ActionType.stand;
		if (x < GameSystem.SCREEN_W / 2){
			isSelected = true;
			name = CharacterName.first;
		}
		else{
			isSelected = false;
			name = CharacterName.second;
		}
		
		body = new NoSpriteObject (x, y, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H);
		
		moveAnimation = new ObjectAnimation ("core/assets/images/player.png", CHARACTER_W, CHARACTER_H,
				FRAME_ROWS, FRAME_COLS, 0.15f);
		currSprite = moveAnimation.getCurrSprite (0);
		dataRender = new DataRender (currSprite, LayerType.character);
	}
	
	public boolean getIsSelected (){
		return isSelected;
	}
	
	public CharacterName getName (){
		return name;
	}
	
	@Override
	public void update (){
		updateMoveAnimation ();
		if (isSelected){
			updateControl ();
			isPushOut = false;
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.characterChange){
			if (isSelected){
				isSelected = false;
				action = ActionType.stand;
			}
			else{
				ObjectManager.getInstance ().addMessage (new CharacterSelectedMessage (this, body.getSpriteX (),
						body.getSpriteY ()));
				isSelected = true;
			}
		}
		else if (message.type == MessageType.move && message.object != this && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, msg.oldBodyX, msg.oldBodyY,
						-msg.deltaX, -msg.deltaY));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == this){
			isPushOut = true;
			PushOutMessage msg = (PushOutMessage) message;
			body.setBodyPosition (msg.whereBodyX, msg.whereBodyY);
		}
		else if (message.type == MessageType.getPosition){
			ObjectManager.getInstance ().addMessage (new ReturnPositionMessage (this, body.getSpriteX (), body.getSpriteY ()));
		}
	}
	
	@Override
	public void draw (){
		dataRender.sprite = currSprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	@Override
	public void clear (){ }
}