package com.game.mesh.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.body.AnimatedObject;
import com.game.mesh.body.NoSpriteObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class Box extends GameObject{
	private static final float BODY_BOX_W = UNIT - 1;
	private static final float BODY_BOX_H = UNIT - 1;
	private static final float BOX_W = UNIT;
	private static final float BOX_H = UNIT * 2;
	
	private boolean isFall = false;
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	private Sprite currSprite;
	private ObjectAnimation fall;
	
	
	private void movedByCharacter (GameMessage message){
		MoveMessage msg = (MoveMessage) message;
		if (msg.deltaX != 0 &&  body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
			ObjectManager.getInstance ().addMessage (new MoveMessage (this, msg.deltaX, 0,
					body.getBodyX (), body.getBodyY (), body.getSpriteX (), body.getSpriteY (), BODY_BOX_W,
					BODY_BOX_H));
			body.move (msg.deltaX, 0);
		}
		if (msg.deltaY != 0 &&  body.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
			ObjectManager.getInstance ().addMessage (new MoveMessage (this, 0, msg.deltaY,
					body.getBodyX (), body.getBodyY (), body.getSpriteX (), body.getSpriteY (), BODY_BOX_W,
					BODY_BOX_H));
			body.move (0, msg.deltaY);
		}
	}
	
	private void pushOut (GameMessage message){
		PushOutMessage msg = (PushOutMessage) message;
		if (msg.deltaX != 0 && !pushOutHorizontal){
			ObjectManager.getInstance ().addMessage (new MoveMessage (this, msg.deltaX, 0,
					body.getBodyX (), body.getBodyY (), body.getSpriteX (), body.getSpriteY (), BODY_BOX_W,
					BODY_BOX_H));
			body.move (msg.deltaX, 0);
			pushOutHorizontal = true;
		}
		if (msg.deltaY != 0 && !pushOutVertical){
			ObjectManager.getInstance ().addMessage (new MoveMessage (this, 0, msg.deltaY,
					body.getBodyX (), body.getBodyY (), body.getSpriteX (), body.getSpriteY (), BODY_BOX_W,
					BODY_BOX_H));
			body.move (0, msg.deltaY);
			pushOutVertical = true;
		}
	}
	
	private void updateFallAnimation (){
		if (fall.isAnimationFinished ()){
			ObjectManager.getInstance ().sendMessage (new DeleteObjectMessage (this));
		}
		else{
			currSprite = fall.getCurrSprite ();
			currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
		}
	}
	
	private void updateMoveAnimation (){
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
	}
	
	
	public Box (float x, float y){
		objectType = ObjectType.box;
		body = new AnimatedObject (x, y, BOX_W, BOX_H, BODY_BOX_W, BODY_BOX_H);
		body.move (0, 0.5f);
		
		fall = new ObjectAnimation ("core/assets/images/box_fall.png", false, BOX_W, BOX_H,
				1, 5, 0.3f);
		currSprite = fall.getCurrSprite (0);
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
		
		dataRender = new DataRender (currSprite, LayerType.box);
	}
	
	@Override
	public void update (){
		pushOutHorizontal = false;
		pushOutVertical = false;
		if (isFall){
			updateFallAnimation ();
		}
		else{
			updateMoveAnimation ();
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			movedByCharacter (message);
		}
		else if (message.type == MessageType.move && message.objectType == ObjectType.box && message.object != this){
			MoveMessage msg = (MoveMessage) message;
			if (msg.deltaX != 0 &&  body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, -msg.deltaX, 0));
			}
			if (msg.deltaY != 0 &&  body.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, 0, -msg.deltaY));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == this){
			pushOut (message);
		}
		else if (message.type == MessageType.destroyObject && message.object == this){
			DestroyObjectMessage msg = (DestroyObjectMessage) message;
			if (msg.destroyer == ObjectType.hole){
				isFall = true;
			}
		}
	}
	
	@Override
	public void draw (){
		dataRender.sprite = currSprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
}
