package com.game.mesh.objects.character;

import com.game.GameSystem;
import com.game.mesh.body.AnimatedObject;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.GameObject;
import com.game.messages.*;
import com.game.mesh.body.NoSpriteObject;

public class Character extends GameObject{
	protected static final float CHARACTER_W = UNIT;
	protected static final float CHARACTER_H = UNIT;
	
	private static final float BODY_CHARACTER_W = 2 * CHARACTER_W / 5;
	private static final float BODY_CHARACTER_H = CHARACTER_H / 4;
	
	protected boolean isFall = false;
	protected boolean isMove = false;
	protected boolean isSelected = false;
	protected ActionType action;
	
	private CharacterName name = CharacterName.unknown;
	private CharacterMessageParser parser;
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
		
		body = new AnimatedObject (x, y, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H);
		body.move (0, 0.25f);
		
		parser = new CharacterMessageParser (this);
		control = new CharacterControl (this);
		animations = new CharacterAnimations (this);
	}
	
	public CharacterName getName (){
		return name;
	}
	
	@Override
	public void update (){
		parser.update ();
		animations.update ();
		control.update ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		parser.parseMessage (message);
	}
	
	@Override
	public void draw (){
		animations.draw ();
	}
	
	
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
	
	protected float getSpriteW (){
		return body.getSpriteW ();
	}
	
	protected float getSpriteH (){
		return body.getSpriteH ();
	}
	
	protected void move (float x, float y){
		body.move (x, y);
	}
	
	protected boolean intersects (float x, float y, float w, float h){
		return body.intersects (x, y, w, h);
	}
}