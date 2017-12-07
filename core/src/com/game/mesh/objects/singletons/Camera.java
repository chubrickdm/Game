package com.game.mesh.objects.singletons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

import com.game.GameSystem;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.character.Character;
import com.game.mesh.objects.character.CharacterName;
import com.game.messages.*;

public class Camera extends GameObject{
	private boolean pushOutVertical = false;
	private OrthographicCamera camera;
	
	
	private static class CameraHolder{
		private final static Camera instance = new Camera ();
	}
	
	private Camera (){
		objectType = ObjectType.camera;
		
		camera = new OrthographicCamera (GameSystem.SCREEN_W, GameSystem.SCREEN_H);
		camera.setToOrtho (false);
	}
	
	
	public static Camera getInstance (){
		return CameraHolder.instance;
	}
	
	public Matrix4 getProjectionMatrix (){
		return camera.combined;
	}
	
	public OrthographicCamera getCamera (){
		return camera;
	}
	
	@Override
	public void update (){
		pushOutVertical = false;
		camera.update ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			camera.translate (0, msg.deltaY);
		}
		else if (message.type == MessageType.characterSelected){
			CharacterSelectedMessage msg = (CharacterSelectedMessage) message;
			camera.position.set (camera.position.x, msg.spriteY + msg.spriteH / 2, 0);
		}
		else if (message.type == MessageType.pushOut && message.objectType == ObjectType.character){
			PushOutMessage msg = (PushOutMessage) message;
			if (msg.deltaY != 0 && !pushOutVertical){
				camera.translate (0, msg.deltaY);
				pushOutVertical = true;
			}
		}
		else if (message.type == MessageType.returnStartPosition && message.objectType == ObjectType.character){
			Character character = (Character) message.object;
			if (character.getName () == CharacterName.first){
				ReturnStartPositionMessage msg = (ReturnStartPositionMessage) message;
				camera.position.set (camera.position.x, msg.spriteY + msg.spriteH / 2, 0);
			}
		}
	}
}