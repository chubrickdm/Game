package com.game.mesh.objects;

import com.game.mesh.body.NoSpriteObject;
import com.game.mesh.objects.character.Character;
import com.game.mesh.objects.character.CharacterName;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.CompleteLevelMessage;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.messages.MoveMessage;

public class FinishLevel extends GameObject{
	private static boolean firstOnFinish = false;
	private static boolean secondOnFinish = false;
	private static FinishLevel firstDetected = null; //ссылка на финиш, на который стал первый персонаж
	private static FinishLevel secondDetected = null; //ссылка на финиш, на который стал второй персонаж
	
	
	public FinishLevel (float x, float y, float w, float h){
		objectType = ObjectType.finishLevel;
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
					firstDetected = this;
				}
				else{
					//персонаж может уйти только с того финиша, на котором ранее находился
					if (firstDetected == this || firstDetected == null){
						firstOnFinish = false;
						firstDetected = null;
					}
				}
			}
			else if (character.getName () == CharacterName.second){
				if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
					secondOnFinish = true;
					secondDetected = this;
				}
				else{
					//персонаж может уйти только с того финиша, на котором ранее находился
					if (secondDetected == this || secondDetected == null){
						secondOnFinish = false;
						secondDetected = null;
					}
				}
			}
		}
	}
	
	@Override
	public void clear (){
		firstOnFinish = false;
		secondOnFinish = false;
		firstDetected = null;
		secondDetected = null;
	}
}