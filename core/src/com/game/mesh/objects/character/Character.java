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

public class Character extends GameObject{
	protected static final float CHARACTER_W = UNIT;
	protected static final float CHARACTER_H = UNIT;
	private static final float BODY_CHARACTER_W = 2 * CHARACTER_W / 5;
	private static final float BODY_CHARACTER_H = CHARACTER_H / 4;
	
	protected boolean isFall = false;
	protected boolean isMove = false;
	protected boolean isSelected = false;
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	private CharacterName name = CharacterName.unknown;
	protected ActionType action;
	private CharacterControl control;
	private CharacterAnimations animations;
	
	
	public Character (){ }
	
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
		body.move (0, 0.25f);
		
		control = new CharacterControl (this);
		animations = new CharacterAnimations (this);
	}
	
	public CharacterName getName (){
		return name;
	}
	
	@Override
	public void update (){
		pushOutHorizontal = false;
		pushOutVertical = false;
		animations.update ();
		control.update ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.characterChange && message.object != this){
			ObjectManager.getInstance ().addMessage (new CharacterSelectedMessage (this, body.getSpriteX (),
					body.getSpriteY (), body.getSpriteW (), body.getSpriteH ()));
			isSelected = true;
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
		else if (message.type == MessageType.characterDied && message.object == this){
			CharacterDiedMessage msg = (CharacterDiedMessage) message;
			if (msg.killer == ObjectType.hole){
				isFall = true;
			}
		}
	}
	
	@Override
	public void draw (){
		animations.draw ();
	}
	
	@Override
	public void clear (){ }
	
	
	protected float getBodyX (){
		return body.getBodyX ();
	}
	
	protected float getBodyY (){
		return body.getBodyY ();
	}
	
	protected float getBodyW (){
		return body.getBodyW ();
	}
	
	protected float getBodyH (){
		return body.getBodyH ();
	}
	
	protected float getSpriteX (){
		return body.getSpriteX ();
	}
	
	protected float getSpriteY (){
		return body.getSpriteY ();
	}
	
	protected void move (float x, float y){
		body.move (x, y);
	}
}