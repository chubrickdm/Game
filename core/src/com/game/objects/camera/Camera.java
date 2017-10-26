package com.game.objects.camera;

import com.badlogic.gdx.math.Matrix4;

import com.game.GameSystem;
import com.game.messages.CharacterMoveMessage;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.messages.PushOutMessage;
import com.game.objects.GameObject;
import com.game.objects.ObjectManager;
import com.game.objects.character.Character;


public class Camera implements GameObject{
	private float firstCharacterSpriteX = -1;
	private float firstCharacterSpriteY = -1;
	private float secondCharacterSpriteX = -1;
	private float secondCharacterSpriteY = -1;
	private BodyCamera camera;
	
	
	private static class CameraHolder{
		private final static Camera instance = new Camera ();
	}
	
	private Camera (){
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
		if (message.type == MessageType.characterMove){
			Character character = (Character) message.object;
			CharacterMoveMessage msg = (CharacterMoveMessage) message;
			if (Math.abs (character.getSpriteY () - firstCharacterSpriteY) > GameSystem.SCREEN_H / 2 - GameObject.UNIT){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, msg.oldX, msg.oldY));
			}
			else if (Math.abs (character.getSpriteY () - secondCharacterSpriteY) > GameSystem.SCREEN_H / 2 - GameObject.UNIT){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, msg.oldX, msg.oldY));
			}
			else{
				if ((Math.abs (msg.spriteOldX - firstCharacterSpriteX) < 1) && (Math.abs (msg.spriteOldY - firstCharacterSpriteY) < 1)){
					camera.moveY (character.getSpriteY () - firstCharacterSpriteY);
					firstCharacterSpriteX = character.getSpriteX ();
					firstCharacterSpriteY = character.getSpriteY ();
				}
				else{
					camera.moveY (character.getSpriteY () - secondCharacterSpriteY);
					secondCharacterSpriteX = character.getSpriteX ();
					secondCharacterSpriteY = character.getSpriteY ();
				}
			}
		}
		else if (message.type == MessageType.characterSelected){
			Character character = (Character) message.object;
			camera.setPositionY (character.getSpriteY () + Character.CHARACTER_H / 2);
		}
	}
	
	@Override
	public void draw (){ }
}
