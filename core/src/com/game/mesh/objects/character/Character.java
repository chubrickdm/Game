package com.game.mesh.objects.character;

import box2dLight.PointLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pools;

import com.game.addition.algorithms.aStar.ConcreteNode;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.State;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.GameMessage;
import com.game.messages.ReturnPositionMessage;
import com.game.render.Render;
import com.introfog.primitiveIsometricEngine.*;

import java.util.ArrayList;

public class Character extends GameObject{
	protected static final float CHARACTER_W = UNIT;
	protected static final float CHARACTER_H = UNIT;
	
	private static final float BODY_CHARACTER_W = 2 * CHARACTER_W / 5;
	private static final float BODY_CHARACTER_H = CHARACTER_H / 4;
	
	protected boolean goToObject = false;
	protected boolean isSelected = false;
	protected Direction currentDirection = Direction.forward;
	protected State state = State.stand;
	
	private CharacterName name = CharacterName.unknown;
	private float bodyShiftX;
	private PointLight flashLight;
	private CharacterMessageParser parser;
	private CharacterControl control;
	private CharacterInputProcessor inputProcessor;
	private CharacterAnimations animations;
	private BodyPIE bodyPIE;
	private Rectangle spriteRect;
	
	
	private Character (CharacterName name){
		objectType = ObjectType.character;
		this.name = name;
		
		bodyShiftX = (CHARACTER_W - BODY_CHARACTER_W) / 2;
		bodyPIE = new BodyPIE (0, 0, BODY_CHARACTER_W, BODY_CHARACTER_H, BodyType.dynamical, 1, Color.NAVY);
		spriteRect = new Rectangle (0, 0, CHARACTER_W, CHARACTER_H);
		flashLight = new PointLight (Render.getInstance ().handler,100, Color.GRAY, (int) (300 * ASPECT_RATIO),
				CHARACTER_W / 2, CHARACTER_H);
		
		parser = new CharacterMessageParser (this);
		control = new CharacterControl (this);
		inputProcessor = new CharacterInputProcessor (this);
		animations = new CharacterAnimations (this);
	}
	
	private static class CharacterHolder{
		private final static Character first = new Character (CharacterName.first);
		private final static Character second = new Character (CharacterName.second);
	}
	
	protected Character (){ }
	
	
	public static Character getFirstInstance (){
		return CharacterHolder.first;
	}
	
	public static Character getSecondInstance (){
		return CharacterHolder.second;
	}
	
	
	public void setSpritePosition (float x, float y){
		isSelected = (name == CharacterName.first);
		
		bodyPIE.setPosition (x + bodyShiftX, y);
		bodyPIE.setGhost (false);
		spriteRect.setPosition (x, y);
		
		flashLight.setActive (true);
		flashLight.setPosition (x + CHARACTER_W / 2, y + CHARACTER_H);
		inputProcessor.setInputProcessor ();
	}
	
	public CharacterName getName (){
		return name;
	}
	
	@Override
	public void update (){
		parser.update ();
		animations.update ();
		control.update ();
		
		if (isSelected){
			ObjectManager.getInstance ().addMessage (
					new ReturnPositionMessage (this, getSpriteX (), getSpriteY (), getSpriteW (), getSpriteH (),
											   getBodyX (), getBodyY (), getBodyW (), getBodyH ()));
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		parser.parseMessage (message);
	}
	
	@Override
	public void draw (){
		animations.draw ();
		flashLight.setPosition (spriteRect.getX () + CHARACTER_W / 2,spriteRect.getY () + CHARACTER_H / 2);
	}
	
	@Override
	public void clear (){
		state = State.stand;
		currentDirection = Direction.forward;
		
		flashLight.setActive (false);
		control.clear ();
		inputProcessor.clear ();
		animations.clear ();
		bodyPIE.setGhost (true);
		Pools.free (this);
	}
	
	protected float getBodyX (){
		return bodyPIE.getX ();
	}
	
	protected float getBodyY (){
		return bodyPIE.getY ();
	}
	
	protected float getBodyW (){
		return bodyPIE.getW ();
	}
	
	protected float getBodyH (){
		return bodyPIE.getH ();
	}
	
	protected float getSpriteX (){
		return spriteRect.getX ();
	}
	
	protected float getSpriteY (){
		return spriteRect.getY ();
	}
	
	protected float getSpriteW (){
		return spriteRect.getW ();
	}
	
	protected float getSpriteH (){
		return spriteRect.getH ();
	}
	
	protected void move (float deltaX, float deltaY){
		bodyPIE.move (deltaX, deltaY);
		spriteRect.setPosition (bodyPIE.getX () - bodyShiftX, bodyPIE.getY ());
	}
	
	protected void setPath (ArrayList <ConcreteNode> path){
		control.setPath (path);
	}
	
	protected void goToObject (int x, int y){
		inputProcessor.goToObject (x, y);
	}
	
	protected BodyPIE getBodyPIE (){
		return bodyPIE;
	}
}