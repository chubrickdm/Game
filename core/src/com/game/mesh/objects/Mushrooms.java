package com.game.mesh.objects;

import box2dLight.PointLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pools;

import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.body.AnimatedObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.AddObjectMessage;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.messages.MoveMessage;
import com.game.render.*;

public class Mushrooms extends GameObject{
	private static final float BODY_MUSH_W = UNIT * 2;
	private static final float BODY_MUSH_H = UNIT * ANGLE * 2;
	private static final float MUSH_W = UNIT * 2;
	private static final float MUSH_H = UNIT * ANGLE * 2;
	
	private boolean isHide = false;
	private ToxicGas toxicGas = null;
	private Sprite currSprite;
	private ObjectAnimation hide;
	private PointLight light;
	
	
	public Mushrooms (){
		objectType = ObjectType.mushrooms;
		body = new AnimatedObject (0, 0, MUSH_W, MUSH_H, BODY_MUSH_W, BODY_MUSH_H);
		
		
		float regionW = 2 * GameObject.UNIT / GameObject.ASPECT_RATIO;
		float regionH = (2 * GameObject.ANGLE) * GameObject.UNIT / GameObject.ASPECT_RATIO;
		hide = new ObjectAnimation ("core/assets/images/other/mushrooms_hide.png", false, regionW, regionH,
				MUSH_W, MUSH_H, 0.1f);
		light = new PointLight (Render.getInstance ().handler,100, Color.OLIVE, (int) (100 * ASPECT_RATIO),
				MUSH_W / 2, MUSH_H / 2);
		currSprite = hide.getFirstFrame ();
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
		dataRender = new DataRender (currSprite, LayerType.below);
	}
	
	public void setSpritePosition (float x, float y){
		body.setSpritePosition (x, y);
		
		light.setPosition (x + MUSH_W / 2, y + MUSH_H / 2);
		light.setActive (true);
	}
	
	@Override
	public void update (){
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
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && toxicGas == null){
			MoveMessage msg = (MoveMessage) message;
			if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				toxicGas = Pools.obtain (ToxicGas.class);
				toxicGas.setSpritePosition (body.getSpriteX (), body.getSpriteY ());
				ObjectManager.getInstance ().sendMessage (new AddObjectMessage (toxicGas));
			}
		}
		if (message.type == MessageType.destroyObject && message.object == toxicGas){
			toxicGas = null;
			hide.resetTime ();
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
		
		light.setActive (false);
		Pools.free (this);
	}
}