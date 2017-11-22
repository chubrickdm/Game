package com.game.mesh.objects;

import com.game.mesh.body.BodyObject;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.DestroyObjectMessage;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.messages.MoveMessage;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class ToxicGas extends GameObject{
	private static final float BODY_HOLE_W = UNIT * 1.8f;
	private static final float BODY_HOLE_H = UNIT * 1.8f;
	private static final float HOLE_W = UNIT * 2;
	private static final float HOLE_H = UNIT * 2;
	
	
	public ToxicGas (float x, float y){
		objectType = ObjectType.hole;
		body = new BodyObject ("core/assets/images/other/toxic_gas.png", true, x, y, HOLE_W, HOLE_H,
				BODY_HOLE_W, BODY_HOLE_H);
		dataRender = new DataRender (body.getSprite (), LayerType.normal);
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
}
