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
	//private int amountPressedKeys = 0;
	private int angleMove = 0;
	private int[] pressedKeys = new int[4]; //0-W, 1-D, 2-S, 3-A, 1-значит зажата кнопка
	private ActionType action;
	private DataRender dataRender;
	private AnimatedBodyObject body;
	
	
	private void processKeyDown (int keyCode){
		switch (keyCode){
		case Input.Keys.D:
			pressedKeys[1] = 1;
			break;
		case Input.Keys.A:
			pressedKeys[3] = 1;
			break;
		case Input.Keys.W:
			pressedKeys[0] = 1;
			break;
		case Input.Keys.S:
			pressedKeys[2] = 1;
			break;
		}
	}
	
	private void processKeyUp (int keyCode){
		switch (keyCode){
		case Input.Keys.D:
			pressedKeys[1] = 0;
			break;
		case Input.Keys.A:
			pressedKeys[3] = 0;
			break;
		case Input.Keys.W:
			pressedKeys[0] = 0;
			break;
		case Input.Keys.S:
			pressedKeys[2] = 0;
			break;
		case Input.Keys.TAB:
			ObjectManager.getInstance ().addMessage (new CharacterChangeMessage (this));
			break;
		case Input.Keys.ESCAPE:
			MyGame.getInstance ().setScreen (new MainMenuScreen ());
			break;
		}
	}
	
	private void processKeyPress (int keyCode, boolean keyDown){
		if (keyDown){
			processKeyDown (keyCode);
		}
		else{
			processKeyUp (keyCode);
		}
		
		
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
		deltaY = 0; deltaX = 0;
		int tmpI = angleMove;
		angleMove = -1;
		action = ActionType.stand;
		if (pressedKeys[1] == 1){
			deltaX = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
			angleMove = 2;
		}
		else if (pressedKeys[3] == 1){
			deltaX = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
			angleMove = 6;
		}
		if (pressedKeys[0] == 1){
			deltaY = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
			if (angleMove == -1){
				angleMove = 0;
			}
			else{
				if (angleMove == 2){
					angleMove = 1;
				}
				else{
					angleMove = 7;
				}
			}
		}
		else if (pressedKeys[2] == 1){
			deltaY = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
			if (angleMove == -1){
				angleMove = 4;
			}
			else{
				if (angleMove == 2){
					angleMove = 3;
				}
				else{
					angleMove = 5;
				}
			}
		}
		if (deltaX != 0 || deltaY != 0){
			action = ActionType.movement;
			body.move (deltaX, deltaY);
			ObjectManager.getInstance ().addMessage (new MoveMessage (this,
					body.getBodyX () - deltaX, body.getBodyY () - deltaY, body.bodyRect));
		}
		
		if (angleMove == -1){
			angleMove = tmpI;
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
		else if (message.type == MessageType.keyDown && iSelected){
			KeyDownMessage msg = (KeyDownMessage) message;
			processKeyPress (msg.getKeyCode (), true);
		}
		else if (message.type == MessageType.keyUp && iSelected){
			KeyUpMessage msg = (KeyUpMessage) message;
			processKeyPress (msg.getKeyCode (), false);
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