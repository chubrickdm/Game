package com.game.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.messages.*;
import com.game.objects.GameObject;
import com.game.objects.ObjectAnimation;
import com.game.objects.ObjectManager;
import com.game.objects.body.NoSpriteObject;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;


public class Character implements GameObject{
	public static final float CHARACTER_W = 64 * ASPECT_RATIO;
	public static final float CHARACTER_H = 64 * ASPECT_RATIO;
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
	private ActionType action;
	private Sprite currSprite;
	private DataRender dataRender;
	private NoSpriteObject body;
	private ObjectAnimation moveAnimation;
	
	
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
		
		if (deltaX != 0 || deltaY != 0){
			action = ActionType.movement;
			body.move (deltaX, deltaY);
			ObjectManager.getInstance ().addMessage (new CharacterMoveMessage (this, body.getBodyX () - deltaX, body.getBodyY () - deltaY, body.bodyRect));
		}
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB))
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (this));
		
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
	
	
	public Character (boolean isSelected, float x, float y){
		action = ActionType.stand;
		this.isSelected = isSelected;
		
		body = new NoSpriteObject (x, y, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H);
		
		moveAnimation = new ObjectAnimation ("core/assets/images/player.png", CHARACTER_W, CHARACTER_H,
				FRAME_ROWS, FRAME_COLS, 0.15f);
		currSprite = moveAnimation.getCurrSprite (0);
		dataRender = new DataRender (currSprite, LayerType.character);
	}
	
	public float getSpriteX (){
		return body.getSpriteX ();
	}
	
	public float getSpriteY (){
		return body.getSpriteY ();
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
				ObjectManager.getInstance ().addMessage (new CharacterSelectedMessage (this));
				isSelected = true;
			}
		}
		else if (message.type == MessageType.characterMove && message.object != this){
			CharacterMoveMessage msg = (CharacterMoveMessage) message;
			if (body.intersects (msg.bodyRectangle)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, msg.oldX, msg.oldY));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == this){
			isPushOut = true;
			PushOutMessage msg = (PushOutMessage) message;
			body.setBodyPosition (msg.whereX, msg.whereY);
		}
		else if (message.type == MessageType.findSelCharacter && isSelected){
			ObjectManager.getInstance ().addMessage (new CharacterSelectedMessage (this));
		}
	}
	
	@Override
	public void draw (){
		dataRender.sprite = currSprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
}