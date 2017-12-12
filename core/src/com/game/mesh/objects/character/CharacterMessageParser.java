package com.game.mesh.objects.character;

import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.State;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;

public class CharacterMessageParser extends Character{
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	private Character character;
	
	
	private void boxMovedMessage (GameMessage message){
		MoveMessage msg = (MoveMessage) message;
		if (msg.deltaX != 0 && character.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
			ObjectManager.getInstance ().addMessage (new PushOutMessage (character, msg.deltaX, 0));
		}
		if (msg.deltaY != 0 && character.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
			ObjectManager.getInstance ().addMessage (new PushOutMessage (character, 0, msg.deltaY));
		}
	}
	
	private void pushOutMessage (GameMessage message){
		PushOutMessage msg = (PushOutMessage) message;
		//может быть ситуация, когда два объекта стоят рядом и персонаж упирается в них обоих тогда без этих флагов
		//он будет вытакливаться 2 раза, вместо 1
		if (msg.deltaX != 0 && !pushOutHorizontal){
			character.move (msg.deltaX, 0);
			ObjectManager.getInstance ().addMessage (new MoveMessage (character, msg.deltaX, 0, character.getBodyX (),
					character.getBodyY (), character.getSpriteX (), character.getSpriteY (), character.getBodyW (), character.getBodyH ()));
			pushOutHorizontal = true;
			if (character.state == State.push){
				character.state = State.stand;
				ObjectManager.getInstance ().addMessage (new DisconnectMessage (character));
			}
		}
		if (msg.deltaY != 0 && !pushOutVertical){
			character.move (0, msg.deltaY);
			ObjectManager.getInstance ().addMessage (new MoveMessage (character, 0, msg.deltaY, character.getBodyX (),
					character.getBodyY (), character.getSpriteX (), character.getSpriteY (), character.getBodyW (), character.getBodyH ()));
			pushOutVertical = true;
			if (character.state == State.push){
				character.state = State.stand;
				ObjectManager.getInstance ().addMessage (new DisconnectMessage (character));
			}
		}
	}
	
	
	public CharacterMessageParser (Character character){
		this.character = character;
	}
	
	@Override
	public void update (){
		pushOutHorizontal = false;
		pushOutVertical = false;
	}
	
	public void parseMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			if (message.object != character){
				MoveMessage msg = (MoveMessage) message;
				if (character.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
					ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, -msg.deltaX, -msg.deltaY));
				}
			}
		}
		else if (message.type == MessageType.characterChange && message.object != character){
			ObjectManager.getInstance ().addMessage (new CharacterSelectedMessage (character, character.getSpriteX (),
					character.getSpriteY (), character.getSpriteW (), character.getSpriteH ()));
			character.isSelected = true;
		}
		else if (message.type == MessageType.pushOut && message.object == character){
			pushOutMessage (message);
		}
		else if (message.type == MessageType.getStartPosition){
			ObjectManager.getInstance ().addMessage (new ReturnStartPositionMessage (character, character.getSpriteX (),
					character.getSpriteY (), character.getSpriteW (), character.getSpriteH (), character.getBodyX (),
					character.getBodyY (), character.getBodyW (), character.getBodyH ()));
		}
		else if (message.type == MessageType.move && message.objectType == ObjectType.box){
			boxMovedMessage (message);
		}
		else if (message.type == MessageType.destroyObject && message.object == character){
			DestroyObjectMessage msg = (DestroyObjectMessage) message;
			if (msg.destroyer == ObjectType.hole){
				character.state = State.fall;
			}
			else if (msg.destroyer == ObjectType.toxicGas){
				character.state = State.choke;
			}
		}
		else if (message.type == MessageType.goTo && message.object == character){
			GoToMessage msg = (GoToMessage) message;
			character.goTo ((int) msg.whereX, (int) msg.whereY);
		}
		else if (message.type == MessageType.changeState && message.object == character){
			ChangeStateMessage msg = (ChangeStateMessage) message;
			if (msg.fromWhom.objectType == ObjectType.box){
				character.state = State.abut;
			}
		}
		else if (message.type == MessageType.destroyObject && message.object == triggered[character.getName ().ordinal ()]){
			triggered[character.getName ().ordinal ()] = null;
			character.state = State.stand;
		}
	}
}