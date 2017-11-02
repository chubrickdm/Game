package com.game.mesh.objects;

import com.game.messages.*;
import com.game.mesh.body.BodyObject;
import com.game.mesh.objects.special.ObjectManager;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class Wall extends GameObject{
	public static final float WALL_W = UNIT;
	public static final float WALL_H = UNIT * 3;
	
	
	public Wall (boolean isHorizonWall, float x, float y){
		objectType = ObjectType.wall;
	
		body = new BodyObject ("core/assets/images/wall.png", x, y, WALL_W, WALL_H, WALL_W, WALL_H);
		if (isHorizonWall){
			body.rotate90 ();
		}
		dataRender = new DataRender (body.sprite, LayerType.wall);
	}
	
	@Override
	public void update (){ }
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, msg.oldBodyX, msg.oldBodyY));
			}
		}
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	@Override
	public void clear (){ }
}
