package com.game.mesh.objects.character;

import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.State;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;

public class CharacterMessageParser extends Character{
	private Character character;
	
	
	public CharacterMessageParser (Character character){
		this.character = character;
	}
	
	@Override
	public void update (){}
	
	public void parseMessage (GameMessage message){
		if (message.type == MessageType.characterChange && message.object != character){
			ObjectManager.getInstance ().addMessage (new CharacterSelectedMessage (character, character.getSpriteX (),
					character.getSpriteY (), character.getSpriteW (), character.getSpriteH ()));
			character.isSelected = true;
		}
		else if (message.type == MessageType.destroyObject){
			DestroyObjectMessage msg = (DestroyObjectMessage) message;
			if (character.getBodyPIE () == msg.bodyPIE){
				if (msg.objectType == ObjectType.hole && character.state != State.fall){
					character.state = State.fall;
				}
				else if (msg.objectType == ObjectType.toxicGas && character.state != State.choke){
					character.state = State.choke;
				}
			}
			if (triggered[character.getName ().ordinal ()] == msg.bodyPIE){
				triggered[character.getName ().ordinal ()] = null;
				character.state = State.stand;
			}
		}
		else if (message.type == MessageType.goTo && message.object == character){
			GoToMessage msg = (GoToMessage) message;
			character.goToObject ((int) msg.whereX, (int) msg.whereY);
		}
		else if (message.type == MessageType.changeState && message.object == character){
			ChangeStateMessage msg = (ChangeStateMessage) message;
			if (msg.fromWhom.objectType == ObjectType.box){
				character.state = State.abut;
			}
		}
		else if (message.type == MessageType.whoseBody){
			WhoseBodyMessage msg = (WhoseBodyMessage) message;
			if (msg.bodyPIE == character.getBodyPIE ()){
				ObjectManager.getInstance ().addMessage (new IsMyBodyMessage (character, character.getBodyPIE ()));
			}
		}
	}
}