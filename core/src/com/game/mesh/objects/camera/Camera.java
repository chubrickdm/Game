package com.game.mesh.objects.camera;

import com.badlogic.gdx.math.Matrix4;

import com.game.GameSystem;
import com.game.mesh.objects.character.CharacterName;
import com.game.mesh.objects.special.ObjectManager;
import com.game.messages.*;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.character.Character;

public class Camera extends GameObject{
	private boolean isFirstUpdate = true;
	private float cameraDeltaY = 0;
	private BodyCamera camera;
	
	
	private static class CameraHolder{
		private final static Camera instance = new Camera ();
	}
	
	private Camera (){
		objectType = ObjectType.camera;
		camera = new BodyCamera ();
	}
	
	
	public static Camera getInstance (){
		return CameraHolder.instance;
	}
	
	public Matrix4 getProjectionMatrix (){
		return camera.getProjectionMatrix ();
	}
	
	@Override
	public void update (){
		camera.update ();
		if (isFirstUpdate){
			ObjectManager.getInstance ().addMessage (new GetPositionMessage ());
			isFirstUpdate = false;
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			
			cameraDeltaY = msg.deltaY;
			camera.moveY (cameraDeltaY);
		}
		else if (message.type == MessageType.characterSelected){
			CharacterSelectedMessage msg = (CharacterSelectedMessage) message;
			camera.setPositionY (msg.spriteY + msg.spriteH / 2);
		}
		else if (message.type == MessageType.pushOut && message.objectType == ObjectType.character){
			camera.moveY (-cameraDeltaY);
			cameraDeltaY = 0;
		}
		else if (message.type == MessageType.returnPosition && message.objectType == ObjectType.character){
			Character character = (Character) message.object;
			if (character.getIsSelected ()){
				ReturnPositionMessage msg = (ReturnPositionMessage) message;
				camera.setPositionY (msg.spriteY + msg.spriteH / 2);
			}
		}
	}
	
	@Override
	public void draw (){
		cameraDeltaY = 0;
	}
	
	@Override
	public void clear (){
		isFirstUpdate = true;
		cameraDeltaY = 0;
		camera.setPositionY (GameSystem.SCREEN_H / 2);
	}
}