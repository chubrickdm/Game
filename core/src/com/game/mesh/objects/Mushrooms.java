package com.game.mesh.objects;

import box2dLight.PointLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.body.AnimatedObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.AddObjectMessage;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.messages.MoveMessage;
import com.game.render.*;

public class Mushrooms extends GameObject{
	private static final float BODY_MUSH_W = UNIT * 1.8f;
	private static final float BODY_MUSH_H = UNIT * 1.8f;
	private static final float MUSH_W = UNIT * 2;
	private static final float MUSH_H = UNIT * 2;
	
	private boolean isHide = false;
	private boolean gasWasCreated = false;
	private ToxicGas toxicGas = null;
	private Sprite currSprite;
	private ObjectAnimation hide;
	private PointLight light;
	
	public Mushrooms (float x, float y){
		objectType = ObjectType.mushrooms;
		body = new AnimatedObject (x, y, MUSH_W, MUSH_H, BODY_MUSH_W, BODY_MUSH_H);
		
		light = new PointLight (Render.getInstance ().handler,100, Color.OLIVE, (int) (100 * ASPECT_RATIO),
				x + MUSH_W / 2, y + MUSH_H / 2);
		hide = new ObjectAnimation ("core/assets/images/other/mushrooms_hide.png", false, MUSH_W, MUSH_H,
				1, 5, 0.1f);
		currSprite = hide.getFirstFrame ();
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
		dataRender = new DataRender (currSprite, LayerType.below);
	}
	
	@Override
	public void update (){
		if (gasWasCreated && !isHide){
			currSprite = hide.getCurrSprite ();
			if (hide.isAnimationFinished ()){
				isHide = true;
				light.setActive (false);
			}
		}
		else if (!gasWasCreated && isHide){
			currSprite = hide.getReversedCurrSprite ();
			if (hide.isAnimationFinished ()){
				isHide = false;
				hide.resetTime ();
				light.setActive (true);
			}
		}
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && !gasWasCreated){
			MoveMessage msg = (MoveMessage) message;
			if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				gasWasCreated = true;
				toxicGas = new ToxicGas (body.getSpriteX (), body.getSpriteY ());
				ObjectManager.getInstance ().sendMessage (new AddObjectMessage (toxicGas));
			}
		}
		if (message.type == MessageType.destroyObject && message.object == toxicGas){
			gasWasCreated = false;
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
		light.remove ();
	}
}
