package com.game.mesh.objects.box;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pools;

import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.State;
import com.game.messages.*;

import com.introfog.primitiveIsometricEngine.*;

public class Box extends GameObject{
	protected static final float BODY_BOX_W = UNIT - 20;
	protected static final float BODY_BOX_H = UNIT * ANGLE - 20;
	protected static final float BOX_W = UNIT;
	protected static final float BOX_H = UNIT + UNIT * ANGLE;
	
	private static final float TRIGGERED_ZONE_W = 2 * BODY_BOX_W;
	private static final float TRIGGERED_ZONE_H = 2 * BODY_BOX_H;
	
	protected State state = State.stand;
	
	private float bodyShiftX;
	private float zoneShiftX;
	private float zoneShiftY;
	private Rectangle spriteRect;
	protected BodyPIE bodyPIE;
	protected TriggeredZone triggeredZone;
	private BoxMessageParser parser;
	private BoxAnimations animations;
	
	
	public Box (){ }
	
	public Box (boolean fictiv){ //нужен фиктивный параметр, что бы не зацикливалось создание ящика.
		objectType = ObjectType.box;
		
		bodyShiftX = (BOX_W - BODY_BOX_W) / 2;
		zoneShiftX = (BODY_BOX_W - TRIGGERED_ZONE_W) / 2;
		zoneShiftY = (BODY_BOX_H - TRIGGERED_ZONE_H) / 2;
		spriteRect = new Rectangle (0,0, BOX_W, BOX_H);
		bodyPIE = new BodyPIE (0, 0, BODY_BOX_W, BODY_BOX_H, BodyType.statical, 1f, Color.PURPLE);
		triggeredZone = new TriggeredZone (0, 0, TRIGGERED_ZONE_W, TRIGGERED_ZONE_H, ZoneType.intersects,
										   Color.GOLDENROD);
		
		parser = new BoxMessageParser (this);
		animations = new BoxAnimations (this);
	}
	
	public void setSpritePosition (float x, float y){
		bodyPIE.setGhost (false);
		triggeredZone.setGhost (false);
		spriteRect.setPosition (x, y);
		bodyPIE.setPosition (x + bodyShiftX, y);
		triggeredZone.setPosition (bodyPIE.getX () + zoneShiftX, bodyPIE.getY () + zoneShiftY);
	}
	
	@Override
	public void update (){
		parser.update ();
		animations.update ();
		
		triggeredZone.setPosition (bodyPIE.getX () + zoneShiftX, bodyPIE.getY () + zoneShiftY);
	}
	
	@Override
	public void sendMessage (GameMessage message){
		parser.parseMessage (message);
	}
	
	@Override
	public void draw (){
		spriteRect.setPosition (bodyPIE.getX () - bodyShiftX, bodyPIE.getY ());
		animations.draw ();
	}
	
	@Override
	public void clear (){
		state = State.stand;
		triggeredZone.clear ();
		bodyPIE.setBodyType (BodyType.statical);
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
	
	protected float getSpriteX (){
		return spriteRect.getX ();
	}
	
	protected float getSpriteY (){
		return spriteRect.getY ();
	}
	
	protected boolean checkTriggered (float x, float y, float w, float h){
		return triggeredZone.check (x, y, w, h);
	}
}