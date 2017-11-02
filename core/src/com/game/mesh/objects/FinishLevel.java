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
		body = new NoSpriteObject (x, y, w, h, w, h);
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
				if (body.contains (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
					firstOnFinish = true;
					//System.out.println ("First - " + true);
				}
				else{
					firstOnFinish = false;
					//System.out.println ("First - " + false);
				}
			}
			else if (character.getName () == CharacterName.second){
				//System.out.println ("Coordinates body Second: " + msg.oldBodyX + " " + msg.oldBodyY);
				//System.out.println ("deltaY: " + msg.deltaY);
				if (body.contains (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
					secondOnFinish = true;
					//System.out.println ("Second - " + true);
				}
				else{
					secondOnFinish = false;
					//System.out.println ("Second - " + false);
				}
			}
		}
		else if (message.type == MessageType.pushOut && message.objectType == ObjectType.character){
			PushOutMessage msg = (PushOutMessage) message;
			Character character = (Character) msg.object;
			//System.out.println ("Push out character.");
			if (body.contains (msg.whereBodyX, msg.whereBodyY, Character.BODY_CHARACTER_W, Character.BODY_CHARACTER_H)){
				//System.out.println ("Character contains.");
				if (character.getName () == CharacterName.first){
					firstOnFinish = true;
					//System.out.println ("First - " + true);
				}
				else if (character.getName () == CharacterName.second){
					secondOnFinish = true;
					//System.out.println ("Second - " + true);
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
