package com.game.mesh.objects;

import com.game.mesh.body.NoSpriteObject;
import com.game.mesh.objects.character.Character;
import com.game.mesh.objects.character.CharacterName;
import com.game.mesh.objects.special.ObjectManager;
import com.game.messages.*;

public class FinishLevel extends GameObject{
	private boolean firstOnFinish = false;
	private boolean secondOnFinish = false;
	
	
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
		body = new NoSpriteObject (x, y, w, h);
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
			MoveMessage msg = (MoveMessage) message;
			Character character = (Character) message.object;
			
			if (character.getName () == CharacterName.first){
				if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
					firstOnFinish = true;
				}
				else{
					firstOnFinish = false;
				}
			}
			else if (character.getName () == CharacterName.second){
				if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
					secondOnFinish = true;
				}
				else{
					secondOnFinish = false;
				}
			}
		}
		
	}
	
	@Override
	public void draw (){ }
	
	@Override
	public void clear (){
		firstOnFinish = false;
		secondOnFinish = false;
	}
}
