package com.game.mesh.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.utils.Pools;
import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.body.AnimatedObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class ToxicGas extends GameObject{
	private static final float BODY_GAS_W = UNIT * 1.5f;
	private static final float BODY_GAS_H = UNIT * ANGLE * 1.5f;
	private static final float GAS_W = UNIT * 2;
	private static final float GAS_H = UNIT * ANGLE * 2;
	
	private float timer = 5f;
	private Sprite currSprite;
	private ObjectAnimation animation;
	
	
	public ToxicGas (){
		objectType = ObjectType.toxicGas;
		body = new AnimatedObject (0, 0, GAS_W, GAS_H, BODY_GAS_W, BODY_GAS_H, true);
		
		float regionW = 2 * GameObject.UNIT / GameObject.ASPECT_RATIO;
		float regionH = (2 * GameObject.ANGLE) * GameObject.UNIT / GameObject.ASPECT_RATIO;
		animation = new ObjectAnimation ("core/assets/images/other/toxic_gas.png", regionW, regionH,
				GAS_W, GAS_H, 0.5f);
		currSprite = animation.getFirstFrame ();
		currSprite.setPosition (0, 0);
		dataRender = new DataRender (currSprite, LayerType.normal);
	}
	
	public void setSpritePosition (float x, float y){
		body.setSpritePosition (x, y);
	}
	
	@Override
	public void update (){
		timer -= Gdx.graphics.getDeltaTime ();
		if (timer <= 0){
			ObjectManager.getInstance ().sendMessage (new DeleteObjectMessage (this));
			//ObjectManager.getInstance ().addMessage (new DestroyObjectMessage (this, this));
			clear ();
		}
		currSprite = animation.getCurrSprite ();
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move){
			MoveMessage msg = (MoveMessage) message;
			//if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				//ObjectManager.getInstance ().addMessage (new DestroyObjectMessage (msg.object, this));
			//}
		}
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
		Pools.free (this);
	}
}