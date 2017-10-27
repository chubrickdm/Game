package com.game.objects.camera;

import com.badlogic.gdx.math.Matrix4;

import com.game.GameSystem;
import com.game.messages.*;
import com.game.objects.GameObject;
import com.game.objects.ObjectManager;
import com.game.objects.ObjectType;
import com.game.objects.character.Character;


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
		camera = BodyCamera.getInstance ();
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
			if (Math.abs (character.getSpriteY () - firstCharacterSpriteY) > GameSystem.SCREEN_H / 2 - GameObject.UNIT){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, msg.oldBodyX, msg.oldBodyY));
			}
			else if (Math.abs (character.getSpriteY () - secondCharacterSpriteY) > GameSystem.SCREEN_H / 2 - GameObject.UNIT){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, msg.oldBodyX, msg.oldBodyY));
			}
			else{
				if ((Math.abs (msg.spriteOldX - firstCharacterSpriteX) < 2) && (Math.abs (msg.spriteOldY - firstCharacterSpriteY) < 2)){
					//System.out.println ("First moved.");
					cameraDeltaY = character.getSpriteY () - firstCharacterSpriteY;
					camera.moveY (cameraDeltaY);
					firstCharacterSpriteX = character.getSpriteX ();
					firstCharacterSpriteY = character.getSpriteY ();
				}
				else{
					//System.out.println ("Second moved.");
					cameraDeltaY = character.getSpriteY () - secondCharacterSpriteY;
					camera.moveY (cameraDeltaY);
					secondCharacterSpriteX = character.getSpriteX ();
					secondCharacterSpriteY = character.getSpriteY ();
				}
			}
		}
		else if (message.type == MessageType.characterSelected){
			Character character = (Character) message.object;
			camera.setPositionY (character.getSpriteY () + Character.CHARACTER_H / 2);
		}
		else if (message.type == MessageType.pushOut){
			camera.moveY (-cameraDeltaY);
		}
	}
	
	@Override
	public void draw (){
		cameraDeltaY = 0;
	}
}
