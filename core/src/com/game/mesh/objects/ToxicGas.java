package com.game.mesh.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pools;

import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

import com.introfog.primitiveIsometricEngine.*;

public class ToxicGas extends GameObject{
	private static final float BODY_GAS_W = UNIT * 1.5f;
	private static final float BODY_GAS_H = UNIT * ANGLE * 1.5f;
	private static final float GAS_W = UNIT * 2;
	private static final float GAS_H = UNIT * ANGLE * 2;
	
	private float zoneShiftX;
	private float zoneShiftY;
	private float timer = 5f;
	private Sprite currSprite;
	private ObjectAnimation animation;
	private TriggeredZone triggeredZone;
	
	
	public ToxicGas (){
		objectType = ObjectType.toxicGas;
		
		zoneShiftX = (GAS_W - BODY_GAS_W) / 2;
		zoneShiftY = (GAS_H - BODY_GAS_H) / 2;
		triggeredZone = new TriggeredZone (0, 0, BODY_GAS_W, BODY_GAS_H, ZoneType.intersects, Color.SALMON);
		
		float regionW = 2 * GameObject.UNIT / GameObject.ASPECT_RATIO;
		float regionH = (2 * GameObject.ANGLE) * GameObject.UNIT / GameObject.ASPECT_RATIO;
		animation = new ObjectAnimation ("core/assets/images/other/toxic_gas.png", regionW, regionH, GAS_W, GAS_H,
										 0.5f);
		currSprite = animation.getFirstFrame ();
		currSprite.setPosition (0, 0);
		dataRender = new DataRender (currSprite, LayerType.normal);
	}
	
	public void setSpritePosition (float x, float y){
		triggeredZone.setPosition (x + zoneShiftX, y + zoneShiftY);
		triggeredZone.setGhost (false);
	}
	
	@Override
	public void update (){
		for (BodyPIE tmpB : triggeredZone.getInZone ()){
			ObjectManager.getInstance ().addMessage (new DestroyObjectMessage (this, tmpB));
		}
		
		timer -= Gdx.graphics.getDeltaTime ();
		if (timer <= 0){
			ObjectManager.getInstance ().sendMessage (new DeleteObjectMessage (this));
			ObjectManager.getInstance ().addMessage (new DestroyObjectMessage (this, null));
			clear ();
		}
		currSprite = animation.getCurrSprite ();
		currSprite.setPosition (triggeredZone.getX () - zoneShiftX, triggeredZone.getY () - zoneShiftY);
	}
	
	@Override
	public void draw (){
		dataRender.sprite = currSprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	@Override
	public void clear (){
		timer = 5f;
		animation.resetTime ();
		currSprite = animation.getFirstFrame ();
		
		triggeredZone.clear ();
		triggeredZone.setGhost (true);
		Pools.free (this);
	}
}