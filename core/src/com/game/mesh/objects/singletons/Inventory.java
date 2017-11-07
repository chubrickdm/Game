package com.game.mesh.objects.singletons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;
import com.game.messages.*;
import com.game.mesh.body.NoBodyObject;
import com.game.mesh.objects.character.CharacterBody;
import com.game.render.*;

public class Inventory extends GameObject{
	private static final float percentPerTick = 2;
	private static final float INVENTORY_W = UNIT * 2;
	private static final float INVENTORY_H = UNIT * 2;
	
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	private boolean isVisible = false;
	private float percentSize = 1;
	
	
	private void updateSizeAnimation (){
		if (Gdx.input.isKeyPressed (Input.Keys.F)){
			isVisible = true;
			if (percentSize >= 100){
				body.setScale (1);
			}
			else{
				body.setScale (percentSize / 100);
				percentSize += percentPerTick;
			}
		}
		else{
			if (percentSize <= 1 + percentPerTick){
				isVisible = false;
				body.setScale (percentSize / 100);
			}
			else{
				isVisible = true;
				body.setScale (percentSize / 100);
				percentSize -= percentPerTick;
			}
		}
	}
	
	private static class InventoryHolder{
		private final static Inventory instance = new Inventory ();
	}
	
	private Inventory (){
		objectType = ObjectType.actionWheel;
		body = new NoBodyObject ("core/assets/images/action_wheel.png", 0, 0, INVENTORY_W, INVENTORY_H);
		body.setOrigin (INVENTORY_W / 2, INVENTORY_H / 2);
		body.setScale (percentSize / 100);
		body.setSpritePosition (INVENTORY_W / 2, INVENTORY_H / 2);
		dataRender = new DataRender (body.getSprite (), LayerType.actionWheel);
	}
	
	
	public static Inventory getInstance (){
		return InventoryHolder.instance;
	}
	
	@Override
	public void update (){
		pushOutHorizontal = false;
		pushOutVertical = false;
		updateSizeAnimation ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			
			body.move (msg.deltaX, msg.deltaY);
		}
		else if (message.type == MessageType.returnPosition && message.objectType == ObjectType.character){
			ReturnPositionMessage msg = (ReturnPositionMessage) message;
			CharacterBody characterBody = (CharacterBody) message.object;
			if (characterBody.getIsSelected ()){
				body.setSpritePosition (msg.spriteX + msg.spriteW / 2, msg.spriteY +  msg.spriteH / 2);
			}
		}
		else if (message.type == MessageType.pushOut && message.objectType == ObjectType.character){
			PushOutMessage msg = (PushOutMessage) message;
			if (msg.deltaX != 0 && !pushOutHorizontal){
				body.move (msg.deltaX, 0);
				pushOutHorizontal = true;
			}
			if (msg.deltaY != 0 && !pushOutVertical){
				body.move (0, msg.deltaY);
				pushOutVertical = true;
			}
		}
		else if (message.type == MessageType.characterSelected){
			CharacterSelectedMessage msg = (CharacterSelectedMessage) message;
			body.setSpritePosition (msg.spriteX + msg.spriteW / 2, msg.spriteY + msg.spriteH / 2);
		}
	}
	
	@Override
	public void draw (){
		if (isVisible){
			Render.getInstance ().addDataForRender (dataRender);
		}
	}
	
	@Override
	public void clear (){
		isVisible = false;
		percentSize = 1;
		body.setOrigin (INVENTORY_H / 2, INVENTORY_H / 2);
		body.setScale (percentSize / 100);
		body.setSpritePosition (INVENTORY_H / 2, INVENTORY_H / 2);
	}
}