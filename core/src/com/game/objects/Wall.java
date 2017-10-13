package com.game.objects;

import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.messages.MoveMessage;
import com.game.messages.PushOutMessage;
import com.game.objects.body.StaticBodyObject;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class Wall implements GameObject{
	public static final float WALL_W = 64 * GameObject.ASPECT_RATIO;
	public static final float WALL_H = 192 * GameObject.ASPECT_RATIO;
	
	private boolean horizon = false;
	private DataRender dataRender;
	private StaticBodyObject body;
	
	
	public Wall (boolean isHorizonWall, float x, float y){
		horizon = isHorizonWall;
		body = new StaticBodyObject ("core\\assets\\images\\wall.png", x, y, WALL_W, WALL_H, WALL_W, WALL_H);
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
		if (message.type == MessageType.movement){
			MoveMessage msg = (MoveMessage) message;
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
