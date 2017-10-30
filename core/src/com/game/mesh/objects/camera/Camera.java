package com.game.mesh.objects.camera;

import com.badlogic.gdx.math.Matrix4;

import com.game.GameSystem;
import com.game.messages.*;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.character.Character;

public class Camera extends GameObject{
	private float cameraDeltaY = 0;
	private float firstCharacterSpriteX = -1;
	private float firstCharacterSpriteY = -1;
	private float secondCharacterSpriteX = -1;
	private float secondCharacterSpriteY = -1;
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
	
	public void setFirstCharacterBodyPosition (float spriteX, float spriteY){
		firstCharacterSpriteX = spriteX;
		firstCharacterSpriteY = spriteY;
		
		if (firstCharacterSpriteY > secondCharacterSpriteY){
			camera.setPositionY (spriteY + Character.CHARACTER_H / 2);
		}
	}
	
	public void setSecondCharacterBodyPosition (float spriteX, float spriteY){
		secondCharacterSpriteX = spriteX;
		secondCharacterSpriteY = spriteY;
		
		if (secondCharacterSpriteY > firstCharacterSpriteY){
			camera.setPositionY (spriteY + Character.CHARACTER_H / 2);
		}
	}
	
	public Matrix4 getProjectionMatrix (){
		return camera.getProjectionMatrix ();
	}
	
	@Override
	public void update (){
		camera.update ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			Character character = (Character) message.object;
			MoveMessage msg = (MoveMessage) message;
			
			if ((Math.abs (msg.spriteOldX - firstCharacterSpriteX) < 5) && (Math.abs (msg.spriteOldY - firstCharacterSpriteY) < 5)){
				firstCharacterSpriteX = character.getSpriteX ();
				firstCharacterSpriteY = character.getSpriteY ();
			}
			else{
				secondCharacterSpriteX = character.getSpriteX ();
				secondCharacterSpriteY = character.getSpriteY ();
			}
			
			cameraDeltaY = msg.deltaY;
			camera.moveY (cameraDeltaY);
			
		}
		else if (message.type == MessageType.characterSelected){
			Character character = (Character) message.object;
			camera.setPositionY (character.getSpriteY () + Character.CHARACTER_H / 2);
		}
		else if (message.type == MessageType.pushOut){
			camera.moveY (-cameraDeltaY);
			cameraDeltaY = 0;
		}
	}
	
	@Override
	public void draw (){
		cameraDeltaY = 0;
	}
	
	@Override
	public void clear (){
		camera.setPositionY (GameSystem.SCREEN_H / 2);
	}
}
