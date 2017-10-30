package com.game.mesh.objects;

import com.game.mesh.body.NoSpriteObject;
import com.game.mesh.objects.special.ObjectManager;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.messages.MoveMessage;
import com.game.messages.PushOutMessage;

public class InvisibleWall extends GameObject{
	public InvisibleWall (float x, float y, float w, float h){
		objectType = ObjectType.invisibleWall;
		body = new NoSpriteObject (x, y, w, h);
	}
	
	@Override
	public void update (){ }
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			if (body.intersects (msg.bodyRectangle)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, msg.oldBodyX, msg.oldBodyY));
			}
		}
	}
	
	@Override
	public void draw (){ }
}
