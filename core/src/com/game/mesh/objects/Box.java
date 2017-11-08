package com.game.mesh.objects;

import com.game.mesh.body.BodyObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.messages.MoveMessage;
import com.game.messages.PushOutMessage;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class Box extends GameObject{
	private static final float BODY_BOX_W = UNIT - 1;
	private static final float BODY_BOX_H = UNIT - 1;
	private static final float BOX_W = UNIT;
	private static final float BOX_H = UNIT * 2;
	
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	
	
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
	
	
	public Box (float x, float y){
		objectType = ObjectType.box;
		body = new BodyObject ("core/assets/images/box.png", x, y, BOX_W, BOX_H, BODY_BOX_W, BODY_BOX_H);
		body.move (0, 0.5f);
		dataRender = new DataRender (body.getSprite (), LayerType.box);
	}
	
	@Override
	public void update (){
		pushOutHorizontal = false;
		pushOutVertical = false;
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
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataRender);
	}
}
