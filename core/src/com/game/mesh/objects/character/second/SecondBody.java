package com.game.mesh.objects.character.second;

import com.game.mesh.body.NoSpriteObject;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.character.ActionType;
import com.game.mesh.objects.character.CharacterName;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;

public class SecondBody extends GameObject{
	protected static final float CHARACTER_W = UNIT;
	protected static final float CHARACTER_H = UNIT;
	private static final float BODY_CHARACTER_W = 2 * CHARACTER_W / 5;
	private static final float BODY_CHARACTER_H = CHARACTER_H / 4;
	
	protected static boolean isMove = false;
	protected static boolean isFall = false;
	protected static boolean isSelected = false;
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	protected static float deltaX = 0;
	protected static float deltaY = 0;
	protected static ActionType action;
	
	private SecondAnimations animations;
	private SecondControl control;
	protected static NoSpriteObject bodySecond;
	
	
	public SecondBody (){ }
	
	public SecondBody (float x, float y){
		objectType = ObjectType.characterSecond;
		action = ActionType.forwardWalk;
		isSelected = false;
		
		animations = new SecondAnimations ();
		control = new SecondControl ();
		
		bodySecond = new NoSpriteObject (x, y, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H);
		bodySecond.move (0, 0.25f);
	}
	
	@Override
	public void update (){
		pushOutHorizontal = false;
		pushOutVertical = false;
		animations.update ();
		control.update ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.characterChange){
			if (isSelected){
				isSelected = false;
			}
			else{
				ObjectManager.getInstance ().addMessage (new CharacterSelectedMessage (this, bodySecond.getSpriteX (),
						bodySecond.getSpriteY (), bodySecond.getSpriteW (), bodySecond.getSpriteH ()));
				isSelected = true;
			}
		}
		else if (message.type == MessageType.move && message.object != this && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			if (bodySecond.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, -msg.deltaX, -msg.deltaY));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == this){
			PushOutMessage msg = (PushOutMessage) message;
			if (msg.deltaX != 0 && !pushOutHorizontal){
				bodySecond.move (msg.deltaX, 0);
				pushOutHorizontal = true;
			}
			if (msg.deltaY != 0 && !pushOutVertical){
				bodySecond.move (0, msg.deltaY);
				pushOutVertical = true;
			}
		}
		else if (message.type == MessageType.getPosition){
			ObjectManager.getInstance ().addMessage (new ReturnPositionMessage (this, bodySecond.getSpriteX (),
					bodySecond.getSpriteY (), bodySecond.getSpriteW (), bodySecond.getSpriteH ()));
		}
		else if (message.type == MessageType.move && message.objectType == ObjectType.box){
			MoveMessage msg = (MoveMessage) message;
			if (msg.deltaX != 0 && bodySecond.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (this, msg.deltaX, 0));
			}
			if (msg.deltaY != 0 && bodySecond.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (this, 0, msg.deltaY));
			}
		}
		else if (message.type == MessageType.characterDied && message.object == this){
			CharacterDiedMessage msg = (CharacterDiedMessage) message;
			if (msg.killer == ObjectType.hole){
				isFall = true;
			}
		}
	}
	
	@Override
	public void draw (){
		animations.draw ();
	}
	
	@Override
	public void clear (){ }
}
