package com.game.mesh.objects.singletons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

import com.game.GameSystem;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;
import com.game.messages.*;

public class Camera extends GameObject{
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
		camera.update ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.returnStartPosition && message.objectType == ObjectType.character){
			ReturnStartPositionMessage msg = (ReturnStartPositionMessage) message;
			camera.position.set (camera.position.x, msg.sprite.getY () + msg.sprite.getH () / 2, 0);
		}
	}
}