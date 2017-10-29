package com.game.mesh.objects;

import com.game.addition.math.BodyRectangle;
import com.game.mesh.body.NoSpriteObject;
import com.game.mesh.objects.character.Character;
import com.game.mesh.objects.special.ObjectManager;
import com.game.messages.*;

public class FinishLevel extends GameObject{
	private boolean firstOnFinish = false;
	private boolean secondOnFinish = false;
	private float firstCharacterSpriteX = -1;
	private float firstCharacterSpriteY = -1;
	private float secondCharacterSpriteX = -1;
	private float secondCharacterSpriteY = -1;
	
	
	private static class FinishLevelHolder{
		private final static FinishLevel instance = new FinishLevel ();
	}
	
	private FinishLevel (){
		objectType = ObjectType.finishLevel;
	}
	
	
	public static FinishLevel getInstance (){
		return FinishLevel.FinishLevelHolder.instance;
	}
	
	public void initialize (float x, float y, float w, float h){
		body = new NoSpriteObject (x, y, w, h, w, h);
	}
	
	public void setFirstCharacterBodyPosition (float spriteX, float spriteY){
		firstCharacterSpriteX = spriteX;
		firstCharacterSpriteY = spriteY;
	}
	
	public void setSecondCharacterBodyPosition (float spriteX, float spriteY){
		secondCharacterSpriteX = spriteX;
		secondCharacterSpriteY = spriteY;
	}
	
	@Override
	public void update (){
		if (firstOnFinish && secondOnFinish){
			ObjectManager.getInstance ().addMessage (new CompleteLevelMessage ());
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			Character character = (Character) message.object;
			MoveMessage msg = (MoveMessage) message;
			
			if ((Math.abs (msg.spriteOldX - firstCharacterSpriteX) < 5) && (Math.abs (msg.spriteOldY - firstCharacterSpriteY) < 5)){
				firstCharacterSpriteX = character.getSpriteX ();
				firstCharacterSpriteY = character.getSpriteY ();
				if (body.contains (msg.bodyRectangle)){
					firstOnFinish = true;
				}
				else{
					firstOnFinish = false;
				}
			}
			else{
				secondCharacterSpriteX = character.getSpriteX ();
				secondCharacterSpriteY = character.getSpriteY ();
				if (body.contains (msg.bodyRectangle)){
					secondOnFinish = true;
				}
				else{
					secondOnFinish = false;
				}
			}
		}
		else if (message.type == MessageType.pushOut && message.objectType == ObjectType.character){
			PushOutMessage msg = (PushOutMessage) message;
			Character character = (Character) msg.object;
			if (body.contains (new BodyRectangle (msg.whereBodyX, msg.whereBodyY, Character.BODY_CHARACTER_W,
					Character.BODY_CHARACTER_H))){
				if ((Math.abs (character.getSpriteX () - firstCharacterSpriteX) < 5) &&
						(Math.abs (character.getSpriteY () - firstCharacterSpriteY) < 5)){
					firstOnFinish = true;
				}
				else{
					secondOnFinish = true;
				}
			}
		}
	}
	
	@Override
	public void draw (){ }
}
