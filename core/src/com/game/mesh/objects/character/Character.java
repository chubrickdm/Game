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
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.render.*;

public class Character extends GameObject{
	private static final float CHARACTER_W = UNIT;
	private static final float CHARACTER_H = UNIT;
	private static final float BODY_CHARACTER_W = 2 * CHARACTER_W / 5;
	private static final float BODY_CHARACTER_H = CHARACTER_H / 4;
	private static final float CHARACTER_SPEED = 80 * ASPECT_RATIO;
	private static final int FRAME_COLS = 7;
	private static final int FRAME_ROWS = 1;
	
	private boolean isSelected = false;
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	private float deltaX = 0;
	private float deltaY = 0;
	private CharacterName name = CharacterName.unknown;
	private ActionType action;
	private Sprite currSprite;
	private ObjectAnimation leftWalk;
	private ObjectAnimation rightWalk;
	private ObjectAnimation forwardWalk;
	private ObjectAnimation backWalk;
	
	
	private void keyWPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.S)){
			action = ActionType.forwardWalk;
			deltaY = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void keySPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.W)){
			action = ActionType.backWalk;
			deltaY = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void keyDPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.A)){
			if (!Gdx.input.isKeyPressed (Input.Keys.W)){
				action = ActionType.rightWalk;
			}
			deltaX = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void keyAPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.D)){
			if (!Gdx.input.isKeyPressed (Input.Keys.W)){
				action = ActionType.leftWalk;
			}
			deltaX = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void updateControl (){
		deltaX = 0; deltaY = 0;
		if (Gdx.input.isKeyPressed (Input.Keys.W)) keyWPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.S)) keySPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.D)) keyDPressed ();
		if (Gdx.input.isKeyPressed (Input.Keys.A)) keyAPressed ();
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (this));
		}
		else if (deltaX != 0 || deltaY != 0){
			ObjectManager.getInstance ().addMessage (new MoveMessage (this, deltaX, deltaY, body.getBodyX (),
					body.getBodyY (), body.getSpriteX (), body.getSpriteY (), body.getBodyW (), body.getBodyH ()));
			body.move (deltaX, deltaY);
		}
	}
	
	private void updateMoveAnimation (){
		if ((deltaX != 0 || deltaY != 0) && isSelected){
			switch (action){
			case forwardWalk:
				currSprite = forwardWalk.getCurrSprite ();
				break;
			case rightWalk:
				currSprite = rightWalk.getCurrSprite ();
				break;
			case backWalk:
				currSprite = backWalk.getCurrSprite ();
				break;
			case leftWalk:
				currSprite = leftWalk.getCurrSprite ();
				break;
			}
		}
		else{
			switch (action){
			case forwardWalk:
				currSprite = forwardWalk.getFirstFrame ();
				break;
			case rightWalk:
				currSprite = rightWalk.getFirstFrame ();
				break;
			case backWalk:
				currSprite = backWalk.getFirstFrame ();
				break;
			case leftWalk:
				currSprite = leftWalk.getFirstFrame ();
				break;
			}
		}
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
	}
	
	
	public Character (float x, float y){
		objectType = ObjectType.character;
		action = ActionType.forwardWalk;
		if (x < GameSystem.SCREEN_W / 2){
			isSelected = true;
			name = CharacterName.first;
		}
		else{
			isSelected = false;
			name = CharacterName.second;
		}
		
		body = new NoSpriteObject (x, y, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H);
		
		leftWalk = new ObjectAnimation ("core/assets/images/walking_left.png", CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		rightWalk = new ObjectAnimation ("core/assets/images/walking_right.png", CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		forwardWalk = new ObjectAnimation ("core/assets/images/walking_forward.png", CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		backWalk = new ObjectAnimation ("core/assets/images/walking_back.png", CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		
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
		pushOutHorizontal = false;
		pushOutVertical = false;
		updateMoveAnimation ();
		if (isSelected){
			updateControl ();
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.characterChange){
			if (isSelected){
				isSelected = false;
			}
			else{
				ObjectManager.getInstance ().addMessage (new CharacterSelectedMessage (this, body.getSpriteX (),
						body.getSpriteY (), body.getSpriteW (), body.getSpriteH ()));
				isSelected = true;
			}
		}
		else if (message.type == MessageType.move && message.object != this && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, -msg.deltaX, -msg.deltaY));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == this){
			PushOutMessage msg = (PushOutMessage) message;
			if (msg.deltaX != 0 && !pushOutHorizontal){
				body.move (msg.deltaX, 0);
				pushOutHorizontal = true;
			}
			if (msg.deltaY != 0 && !pushOutVertical){
				body.move (0, msg.deltaY);
				pushOutVertical = true;
			}
		}
		else if (message.type == MessageType.getPosition){
			ObjectManager.getInstance ().addMessage (new ReturnPositionMessage (this, body.getSpriteX (),
					body.getSpriteY (), body.getSpriteW (), body.getSpriteH ()));
		}
		else if (message.type == MessageType.move && message.objectType == ObjectType.box){
			MoveMessage msg = (MoveMessage) message;
			if (msg.deltaX != 0 && body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (this, msg.deltaX, 0));
			}
			if (msg.deltaY != 0 && body.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (this, 0, msg.deltaY));
			}
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