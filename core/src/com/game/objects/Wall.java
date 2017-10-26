package com.game.objects;

import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.messages.CharacterMoveMessage;
import com.game.messages.PushOutMessage;
import com.game.objects.body.BodyObject;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class Wall implements GameObject{
	public static final float WALL_W = UNIT;
	public static final float WALL_H = UNIT * 3;
	
	private boolean horizon = false;
	private DataRender dataRender;
	private BodyObject body;
	
	
	public Wall (boolean isHorizonWall, float x, float y){
		horizon = isHorizonWall;
		body = new BodyObject ("core/assets/images/wall.png", x, y, WALL_W, WALL_H, WALL_W, WALL_H);
		if (horizon){
			body.rotate90 ();
		}
		dataRender = new DataRender (body.sprite, LayerType.wall);
	}
	
	@Override
	public void update (){
	
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.characterMove){
			CharacterMoveMessage msg = (CharacterMoveMessage) message;
			if (body.intersects (msg.bodyRectangle)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, msg.oldX, msg.oldY));
			}
		}
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataRender);
	}
}