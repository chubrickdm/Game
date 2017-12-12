package com.game.mesh.objects.box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.State;
import com.game.mesh.objects.character.Character;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;

public class BoxMessageParser extends Box{
	private boolean pushThis = false;
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	private Box box;
	private Character exciter;
	
	
	private void checkTriggeredZone (float bodyX, float bodyY, float bodyW, float bodyH, Character character){
		if (box.state != State.fall){ //если этого условия не будет, то когда ящик падает и персонаж двигается возле триггеред зоны
			// спрайт с анимации падения меняется на обычный
			boolean trigger = box.checkTriggered (bodyX, bodyY, bodyW, bodyH);
			if (trigger && triggered[character.getName ().ordinal ()] == null){
				exciter = character;
				triggered[character.getName ().ordinal ()] = box;
			}
			else if (!trigger && triggered[character.getName ().ordinal ()] == box){
				exciter = null;
				triggered[character.getName ().ordinal ()] = null;
			}
		}
	}
	
	private void movedByCharacterMessage (GameMessage message){
		MoveMessage msg = (MoveMessage) message;
		Character character = (Character) message.object;
		//такое разделение движения на 2 направлени по оси Х и У не случайно, могут быть ситуации когда персонаж
		//движется под углом к стене, но выталкиваться он будет только в одном направлении, что б он смог двигаться
		//вдоль стены
		if (!pushThis){
			if (msg.deltaX != 0 && box.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (character, -msg.deltaX, 0));
			}
			if (msg.deltaY != 0 && box.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (character, 0, -msg.deltaY));
			}
		}
		else if (exciter == message.object){
			if (msg.deltaX != 0 && box.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new MoveMessage (box, msg.deltaX, 0, box.getBodyX (),
						box.getBodyY (), box.getSpriteX (), box.getSpriteY (), BODY_BOX_W, BODY_BOX_H));
				box.move (msg.deltaX, 0);
			}
			if (msg.deltaY != 0 && box.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new MoveMessage (box, 0, msg.deltaY, box.getBodyX (),
						box.getBodyY (), box.getSpriteX (), box.getSpriteY (), BODY_BOX_W, BODY_BOX_H));
				box.move (0, msg.deltaY);
			}
		}
	}
	
	private void pushOutMessage (GameMessage message){
		//два флага нужны, что бы не было ситуации когда ящик упирается в два объекта, и они его 2 раза выталкивают,
		//вместо одного.
		PushOutMessage msg = (PushOutMessage) message;
		if (msg.deltaX != 0 && !pushOutHorizontal){
			ObjectManager.getInstance ().addMessage (new MoveMessage (box, msg.deltaX, 0, box.getBodyX (), box.getBodyY (), box.getSpriteX (), box.getSpriteY (), BODY_BOX_W, BODY_BOX_H));
			box.move (msg.deltaX, 0);
			pushOutHorizontal = true;
		}
		if (msg.deltaY != 0 && !pushOutVertical){
			ObjectManager.getInstance ().addMessage (new MoveMessage (box, 0, msg.deltaY, box.getBodyX (), box.getBodyY (), box.getSpriteX (), box.getSpriteY (), BODY_BOX_W, BODY_BOX_H));
			box.move (0, msg.deltaY);
			pushOutVertical = true;
		}
	}
	
	
	@Override
	public void update (){
		pushOutHorizontal = false;
		pushOutVertical = false;
		
		if (!pushThis && Gdx.input.isKeyJustPressed (Input.Keys.E)){
			if (triggered[0] == box || triggered[1] == box){
				ObjectManager.getInstance ().addMessage (new GoToMessage (exciter,
						box.getBodyX () + box.getBodyW () / 2, box.getBodyY () + box.getBodyW () / 2));
			}
		}
	}
	
	public BoxMessageParser (Box box){
		this.box = box;
	}
	
	public void parseMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			Character character = (Character) message.object;
			//именно в таком порядке, иначе будет баг, спрайт будет заходить на стену
			checkTriggeredZone (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH, character);
			movedByCharacterMessage (message);
		}
		else if (message.type == MessageType.move && message.objectType == ObjectType.box && message.object != box){
			MoveMessage msg = (MoveMessage) message;
			//не делим на 2 случая по оси Х и У, т.к. ящики двигаются только в 4 направлениях.
			if (box.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, -msg.deltaX, -msg.deltaY));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == box){
			pushOutMessage (message);
		}
		else if (message.type == MessageType.destroyObject && message.object == box){
			DestroyObjectMessage msg = (DestroyObjectMessage) message;
			if (msg.destroyer == ObjectType.hole){
				box.state = State.fall;
				pushThis = false;
				exciter = null;
			}
		}
		else if (message.type == MessageType.comeTo && message.object == exciter){
			pushThis = true;
			ObjectManager.getInstance ().addMessage (new ChangeStateMessage (exciter, box));
		}
		else if (message.type == MessageType.disconnect && message.object == exciter){
			pushThis = false;
		}
		else if (message.type == MessageType.returnStartPosition && message.objectType == ObjectType.character){
			ReturnStartPositionMessage msg = (ReturnStartPositionMessage) message;
			Character character = (Character) message.object;
			checkTriggeredZone (msg.body.getX (), msg.body.getY (), msg.body.getW (), msg.body.getH (), character);
		}
	}
}