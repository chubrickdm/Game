package com.game.mesh.objects.character;

import box2dLight.PointLight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;

import com.game.GameSystem;
import com.game.addition.algorithms.aStar.realisation.ConcreteNode;
import com.game.mesh.body.AnimatedObject;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;
import com.game.messages.GameMessage;
import com.game.render.Render;

import java.util.ArrayList;

public class Character extends GameObject{
	protected static final float CHARACTER_W = UNIT;
	protected static final float CHARACTER_H = UNIT;
	
	private static final float BODY_CHARACTER_W = 2 * CHARACTER_W / 5;
	private static final float BODY_CHARACTER_H = CHARACTER_H / 4;
	
	protected boolean isChoke = false;
	protected boolean isFall = false;
	protected boolean isMove = false;
	protected boolean isSelected = false;
	protected ActionType action;
	
	private CharacterName name = CharacterName.unknown;
	private PointLight flashLight;
	private CharacterMessageParser parser;
	private CharacterControl control;
	private CharacterInputProcessor inputProcessor;
	private CharacterAnimations animations;
	
	
	public Character (){ }
	
	public Character (float x, float y){
		objectType = ObjectType.character;
		action = ActionType.forwardWalk;
		if (x < GameSystem.SCREEN_W / 2){ //персонаж слева, всегда первый
			isSelected = true;
			name = CharacterName.first;
		}
		else{ //а справа, всегда второй
			isSelected = false;
			name = CharacterName.second;
		}
		
		body = new AnimatedObject (x, y, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H);
		body.move (0, 0.25f);
		flashLight = new PointLight (Render.getInstance ().handler,100, Color.GRAY, (int) (300 * ASPECT_RATIO),
				x + CHARACTER_W / 2, y + CHARACTER_H);
		
		parser = new CharacterMessageParser (this);
		control = new CharacterControl (this);
		inputProcessor = new CharacterInputProcessor (this);
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
	
	protected void move (float deltaX, float deltaY){
		body.move (deltaX, deltaY);
		flashLight.setPosition (flashLight.getX () + deltaX, flashLight.getY () + deltaY);
	}
	
	protected boolean intersects (float x, float y, float w, float h){
		return body.intersects (x, y, w, h);
	}
	
	protected void setPath (ArrayList <ConcreteNode> path){
		control.setPath (path);
	}
	
	@Override
	public void clear (){
		inputProcessor.clear ();
		flashLight.remove ();
	}
}