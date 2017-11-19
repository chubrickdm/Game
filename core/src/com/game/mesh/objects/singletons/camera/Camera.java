package com.game.mesh.objects.singletons.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.character.Character;
import com.game.mesh.objects.character.CharacterName;
import com.game.messages.*;

public class Camera extends GameObject{
	//private boolean pushOutHorizontal = false; //то что закомментировано, это вариант когда камера всегда на персонаже
	private boolean pushOutVertical = false;
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
	
	public OrthographicCamera getCamera (){
		return camera.getCamera ();
	}
	
	@Override
	public void update (){
		//pushOutHorizontal = false;
		pushOutVertical = false;
		camera.update ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			camera.moveY (msg.deltaY);
			//camera.move (msg.deltaX, msg.deltaY);
		}
		else if (message.type == MessageType.characterSelected){
			CharacterSelectedMessage msg = (CharacterSelectedMessage) message;
			camera.setPositionY (msg.spriteY + msg.spriteH / 2);
			//camera.setPosition (msg.spriteX + msg.spriteW / 2, msg.spriteY + msg.spriteH / 2);
		}
		else if (message.type == MessageType.pushOut && message.objectType == ObjectType.character){
			PushOutMessage msg = (PushOutMessage) message;
			//if (msg.deltaX != 0 && !pushOutHorizontal){
			//	camera.move (msg.deltaX, 0);
			//	pushOutHorizontal = true;
			//}
			if (msg.deltaY != 0 && !pushOutVertical){
				camera.move (0, msg.deltaY);
				pushOutVertical = true;
			}
		}
		else if (message.type == MessageType.returnStartPosition && message.objectType == ObjectType.character){
			Character character = (Character) message.object;
			if (character.getName () == CharacterName.first){
				ReturnStartPositionMessage msg = (ReturnStartPositionMessage) message;
				camera.setPositionY (msg.spriteY + msg.spriteH / 2);
				//camera.setPosition (msg.spriteX + msg.spriteW / 2, msg.spriteY + msg.spriteH / 2);
			}
		}
	}
	
	@Override
	public void clear (){
		camera = new BodyCamera ();
	}
}