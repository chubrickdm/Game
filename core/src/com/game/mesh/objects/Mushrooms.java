package com.game.mesh.objects;

import com.game.mesh.body.BodyObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.DestroyObjectMessage;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.messages.MoveMessage;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class Mushrooms extends GameObject{
	private static final float BODY_MUSH_W = UNIT * 1.8f;
	private static final float BODY_MUSH_H = UNIT * 1.8f;
	private static final float MUSH_W = UNIT * 2;
	private static final float MUSH_H = UNIT * 2;
	
	
	public Mushrooms (float x, float y){
		objectType = ObjectType.mushrooms;
		body = new BodyObject ("core/assets/images/other/mushrooms.png", true, x, y, MUSH_W, MUSH_H, BODY_MUSH_W,
				BODY_MUSH_H);
		dataRender = new DataRender (body.getSprite (), LayerType.hole);
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move){
			MoveMessage msg = (MoveMessage) message;
			if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new DestroyObjectMessage (msg.object, this));
			}
		}
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataRender);
	}
}
