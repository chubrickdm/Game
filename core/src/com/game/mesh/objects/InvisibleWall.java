package com.game.mesh.objects;

import com.game.mesh.body.BodyObject;
import com.game.mesh.body.NoSpriteObject;
import com.game.mesh.objects.special.ObjectManager;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.messages.MoveMessage;
import com.game.messages.PushOutMessage;

public class InvisibleWall extends GameObject{
	public static final float INVISIBLE_WALL_W = Wall.WALL_W;
	public static final float INVISIBLE_WALL_H = Wall.WALL_H;
	
	
	public InvisibleWall (boolean isHorizonWall, float x, float y){
		objectType = ObjectType.invisibleWall;
		
		body = new NoSpriteObject (x, y, INVISIBLE_WALL_W, INVISIBLE_WALL_H, INVISIBLE_WALL_W, INVISIBLE_WALL_H);
		if (isHorizonWall){
			body.rotate90 ();
		}
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
