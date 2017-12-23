package com.game.mesh.objects;

import box2dLight.PointLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pools;

import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.body.AnimatedObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;
import com.game.render.*;
import com.introfog.primitiveIsometricEngine.*;

public class Mushrooms extends GameObject{
	private static final float BODY_MUSH_W = UNIT * 2;
	private static final float BODY_MUSH_H = UNIT * ANGLE * 2;
	private static final float MUSH_W = UNIT * 2;
	private static final float MUSH_H = UNIT * ANGLE * 2;
	
	private boolean isHide = false;
	private float zoneShiftX;
	private ToxicGas toxicGas = null;
	private Sprite currSprite;
	private ObjectAnimation hide;
	private PointLight light;
	private TriggeredZone triggeredZone;
	
	
	public Mushrooms (){
		objectType = ObjectType.mushrooms;
		
		zoneShiftX = (MUSH_W - BODY_MUSH_W) / 2;
		triggeredZone = new TriggeredZone (0, 0, BODY_MUSH_W, BODY_MUSH_H, ZoneType.intersects, Color.TAN);
		
		float regionW = 2 * GameObject.UNIT / GameObject.ASPECT_RATIO;
		float regionH = (2 * GameObject.ANGLE) * GameObject.UNIT / GameObject.ASPECT_RATIO;
		hide = new ObjectAnimation ("core/assets/images/other/mushrooms_hide.png", false, regionW, regionH, MUSH_W,
									MUSH_H, 0.1f);
		light = new PointLight (Render.getInstance ().handler, 100, Color.OLIVE, (int) (100 * ASPECT_RATIO), MUSH_W / 2,
								MUSH_H / 2);
		currSprite = hide.getFirstFrame ();
		dataRender = new DataRender (currSprite, LayerType.below);
	}
	
	public void setSpritePosition (float x, float y){
		triggeredZone.setPosition (x + zoneShiftX, y);
		triggeredZone.setGhost (false);
		
		light.setPosition (x + MUSH_W / 2, y + MUSH_H / 2);
		light.setActive (true);
	}
	
	@Override
	public void update (){
		if (toxicGas == null && triggeredZone.getInZone ().size () > 0){
			toxicGas = Pools.obtain (ToxicGas.class);
			toxicGas.setSpritePosition (triggeredZone.getX () - zoneShiftX, triggeredZone.getY ());
			ObjectManager.getInstance ().sendMessage (new AddObjectMessage (toxicGas));
		}
		
		if (toxicGas != null && !isHide){
			if (hide.isAnimationFinished ()){
				isHide = true;
				light.setActive (false);
			}
			currSprite = hide.getCurrSprite ();
		}
		else if (toxicGas == null && isHide){
			if (hide.isAnimationFinished ()){
				isHide = false;
				hide.resetTime ();
				light.setActive (true);
			}
			currSprite = hide.getReversedCurrSprite ();
		}
		else if (toxicGas == null){
			currSprite = hide.getFirstFrame ();
		}
		currSprite.setPosition (triggeredZone.getX () - zoneShiftX, triggeredZone.getY ());
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.destroyObject && message.object == toxicGas){
			DestroyObjectMessage msg = (DestroyObjectMessage) message;
			if (msg.bodyPIE == null){
				toxicGas = null;
				hide.resetTime ();
			}
		}
	}
	
	@Override
	public void draw (){
		dataRender.sprite = currSprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	@Override
	public void clear (){
		isHide = false;
		toxicGas = null;
		currSprite = hide.getFirstFrame ();
		
		triggeredZone.clear ();
		triggeredZone.setGhost (true);
		light.setActive (false);
		Pools.free (this);
	}
}