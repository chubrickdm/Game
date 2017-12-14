package com.game.mesh.objects;

import com.badlogic.gdx.utils.Pools;

import com.game.mesh.body.BodyObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.DestroyObjectMessage;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.messages.MoveMessage;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class Hole extends GameObject{
	private static final float BODY_HOLE_W = UNIT * 2;
	private static final float BODY_HOLE_H = UNIT * ANGLE * 2;
	private static final float HOLE_W = UNIT * 2;
	private static final float HOLE_H = UNIT * ANGLE * 2;
	
	
	public Hole (){
		objectType = ObjectType.hole;
		body = new BodyObject ("core/assets/images/other/hole.png", true, 0, 0, HOLE_W, HOLE_H,
				BODY_HOLE_W, BODY_HOLE_H);
		dataRender = new DataRender (body.getSprite (), LayerType.below);
	}
	
	public void setSpritePosition (float x, float y){
		body.setSpritePosition (x, y);
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move){
			MoveMessage msg = (MoveMessage) message;
			if (body.contains (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new DestroyObjectMessage (msg.object, this));
			}
		}
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	@Override
	public void clear (){
		Pools.free (this);
	}
}