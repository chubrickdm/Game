package com.game.mesh.objects.box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.State;
import com.game.mesh.objects.character.Character;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;

import com.introfog.primitiveIsometricEngine.BodyType;

public class BoxMessageParser extends Box{
	private boolean pushThis = false;
	private Box box;
	private Character exciter;
	
	
	private void checkTriggeredZone (float bodyX, float bodyY, float bodyW, float bodyH, Character character){
		if (box.state != State.fall){ //если этого условия не будет, то когда ящик падает и персонаж двигается возле триггеред зоны
			// спрайт с анимации падения меняется на обычный
			boolean trigger = box.checkTriggered (bodyX, bodyY, bodyW, bodyH);
			if (trigger && triggered[character.getName ().ordinal ()] == null){
				exciter = character;
				triggered[character.getName ().ordinal ()] = box.bodyPIE;
			}
			else if (!trigger && triggered[character.getName ().ordinal ()] == box.bodyPIE){
				triggered[character.getName ().ordinal ()] = null;
			}
		}
	}
	
	
	public BoxMessageParser (Box box){
		this.box = box;
	}
	
	@Override
	public void update (){
		if (!pushThis && Gdx.input.isKeyJustPressed (Input.Keys.E)){
			if (triggered[0] == box.bodyPIE || triggered[1] == box.bodyPIE){
				ObjectManager.getInstance ().addMessage (new GoToMessage (exciter, box.getBodyX () + box.getBodyW () / 2,
										 box.getBodyY () + box.getBodyW () / 2));
			}
		}
	}
	
	@Override
	public void clear (){
		pushThis = false;
	}
	
	public void parseMessage (GameMessage message){
		if (message.type == MessageType.destroyObject){
			DestroyObjectMessage msg = (DestroyObjectMessage) message;
			if (msg.objectType == ObjectType.hole && msg.bodyPIE == box.bodyPIE){
				box.state = State.fall;
				pushThis = false;
				exciter = null;
			}
		}
		else if (message.type == MessageType.comeTo && message.object == exciter){
			pushThis = true;
			box.bodyPIE.setBodyType (BodyType.dynamical);
			ObjectManager.getInstance ().addMessage (new ChangeStateMessage (exciter, box));
		}
		else if (message.type == MessageType.disconnect && message.object == exciter){
			box.bodyPIE.setBodyType (BodyType.statical);
			pushThis = false;
		}
		else if (message.type == MessageType.returnStartPosition && message.objectType == ObjectType.character){
			ReturnStartPositionMessage msg = (ReturnStartPositionMessage) message;
			Character character = (Character) message.object;
			checkTriggeredZone (msg.body.getX (), msg.body.getY (), msg.body.getW (), msg.body.getH (), character);
		}
	}
}