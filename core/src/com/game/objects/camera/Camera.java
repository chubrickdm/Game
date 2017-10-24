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
	private float firstCharacterBodyX = -1;
	private float firstCharacterBodyY = -1;
	private float secondCharacterBodyX = -1;
	private float secondCharacterBodyY = -1;
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
	
	public void setFirstCharacterBodyPosition (float spriteY, float bodyX, float bodyY){
		firstCharacterBodyX = bodyX;
		firstCharacterBodyY = bodyY;
		
		if (firstCharacterBodyY > secondCharacterBodyY){
			camera.setPositionY (spriteY + Character.CHARACTER_H / 2);
			//camera.translate (0, -GameSystem.SCREEN_H / 2 + firstCharacterBodyY + Character.CHARACTER_H / 2);
		}
	}
	
	public void setSecondCharacterBodyPosition (float spriteY, float bodyX, float bodyY){
		secondCharacterBodyX = bodyX;
		secondCharacterBodyY = bodyY;
		
		if (secondCharacterBodyY > firstCharacterBodyY){
			camera.setPositionY (spriteY + Character.CHARACTER_H / 2);
			//camera.translate (0, -GameSystem.SCREEN_H / 2 + secondCharacterBodyY + Character.CHARACTER_H / 2);
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
			CharacterMoveMessage msg = (CharacterMoveMessage) message;
			if (Math.abs (msg.bodyRectangle.getY () - firstCharacterBodyY) > GameSystem.SCREEN_H / 2 - GameObject.UNIT){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, msg.oldX, msg.oldY));
			}
			else if (Math.abs (msg.bodyRectangle.getY () - secondCharacterBodyY) > GameSystem.SCREEN_H / 2 - GameObject.UNIT){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, msg.oldX, msg.oldY));
			}
			else{
				if ((Math.abs (msg.oldX - firstCharacterBodyX) < 1) && (Math.abs (msg.oldY - firstCharacterBodyY) < 1)){
					firstCharacterBodyX = msg.bodyRectangle.getX ();
					firstCharacterBodyY = msg.bodyRectangle.getY ();
					//System.out.println ("First.");
					//camera.setPositionY (firstCharacterBodyY);
				}
				else{
					secondCharacterBodyX = msg.bodyRectangle.getX ();
					secondCharacterBodyY = msg.bodyRectangle.getY ();
					//System.out.println ("Second.");
					//camera.setPositionY (secondCharacterBodyY);
				}
				camera.moveY (msg.bodyRectangle.getY () - msg.oldY);
				//camera.translate (0, msg.bodyRectangle.getY () - msg.oldY);
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
