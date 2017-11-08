package com.game.mesh.objects.character;

import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;

public class CharacterMessageParser extends Character{
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	private Character character;
	
	
	public CharacterMessageParser (Character character){
		this.character = character;
	}
	
	@Override
	public void update (){
		pushOutHorizontal = false;
		pushOutVertical = false;
	}
	
	public void parseMessage (GameMessage message){
		if (message.type == MessageType.characterChange && message.object != character){
			ObjectManager.getInstance ().addMessage (new CharacterSelectedMessage (character, character.getSpriteX (),
					character.getSpriteY (), character.getSpriteW (), character.getSpriteH ()));
			character.isSelected = true;
		}
		else if (message.type == MessageType.move && message.object != character && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			if (character.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, -msg.deltaX, -msg.deltaY));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == character){
			PushOutMessage msg = (PushOutMessage) message;
			if (msg.deltaX != 0 && !pushOutHorizontal){
				character.move (msg.deltaX, 0);
				pushOutHorizontal = true;
			}
			if (msg.deltaY != 0 && !pushOutVertical){
				character.move (0, msg.deltaY);
				pushOutVertical = true;
			}
		}
		else if (message.type == MessageType.getStartPosition){
			ObjectManager.getInstance ().addMessage (new ReturnStartPositionMessage (character, character.getSpriteX (),
					character.getSpriteY (), character.getSpriteW (), character.getSpriteH ()));
		}
		else if (message.type == MessageType.move && message.objectType == ObjectType.box){
			MoveMessage msg = (MoveMessage) message;
			if (msg.deltaX != 0 && character.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (character, msg.deltaX, 0));
			}
			if (msg.deltaY != 0 && character.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (character, 0, msg.deltaY));
			}
		}
		else if (message.type == MessageType.characterDied && message.object == character){
			CharacterDiedMessage msg = (CharacterDiedMessage) message;
			if (msg.killer == ObjectType.hole){
				character.isFall = true;
			}
		}
	}
}