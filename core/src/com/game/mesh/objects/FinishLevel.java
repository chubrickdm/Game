package com.game.mesh.objects;

import com.game.mesh.body.NoSpriteObject;
import com.game.mesh.objects.character.Character;
import com.game.mesh.objects.special.ObjectManager;
import com.game.messages.*;

public class FinishLevel extends GameObject{
	private static int CHARACTER_ON_FINISH_COUNTER = 0;
	
	
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
	
	@Override
	public void update (){
		if (CHARACTER_ON_FINISH_COUNTER == 2){
			ObjectManager.getInstance ().addMessage (new CompleteLevelMessage ());
		}
		CHARACTER_ON_FINISH_COUNTER = 0;
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			if (body.contains (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				CHARACTER_ON_FINISH_COUNTER++;
			}
			
		}
		else if (message.type == MessageType.pushOut && message.objectType == ObjectType.character){
			PushOutMessage msg = (PushOutMessage) message;
			if (!body.contains (msg.whereBodyX, msg.whereBodyY, Character.BODY_CHARACTER_W, Character.BODY_CHARACTER_H)){
				CHARACTER_ON_FINISH_COUNTER--;
			}
		}
	}
	
	@Override
	public void draw (){
		CHARACTER_ON_FINISH_COUNTER = 0;
	}
	
	@Override
	public void clear (){ }
}
