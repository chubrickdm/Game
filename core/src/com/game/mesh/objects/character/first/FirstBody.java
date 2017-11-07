package com.game.mesh.objects.character.first;

import com.game.mesh.body.NoSpriteObject;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.character.ActionType;
import com.game.mesh.objects.character.CharacterName;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;

public class FirstBody extends GameObject{
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
	
	private FirstAnimations animations;
	private FirstControl control;
	protected static NoSpriteObject bodyFirst;
	
	
	public FirstBody (){ }
	
	public FirstBody (float x, float y){
		objectType = ObjectType.characterFirst;
		action = ActionType.forwardWalk;
		isSelected = true;
		
		animations = new FirstAnimations (this);
		control = new FirstControl (this);
		
		bodyFirst = new NoSpriteObject (x, y, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H);
		bodyFirst.move (0, 0.25f);
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
			ObjectManager.getInstance ().addMessage (new CharacterSelectedMessage (this, bodyFirst.getSpriteX (),
					bodyFirst.getSpriteY (), bodyFirst.getSpriteW (), bodyFirst.getSpriteH ()));
			isSelected = true;
		}
		else if (message.type == MessageType.move && message.object != this){
			MoveMessage msg = (MoveMessage) message;
			if (bodyFirst.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, -msg.deltaX, -msg.deltaY));
			}
		}
		else if (message.type == MessageType.pushOut && message.objectType == ObjectType.characterSecond){
			PushOutMessage msg = (PushOutMessage) message;
			if (msg.deltaX != 0 && !pushOutHorizontal){
				bodyFirst.move (msg.deltaX, 0);
				pushOutHorizontal = true;
			}
			if (msg.deltaY != 0 && !pushOutVertical){
				bodyFirst.move (0, msg.deltaY);
				pushOutVertical = true;
			}
		}
		else if (message.type == MessageType.getPosition){
			if (isSelected){
				ObjectManager.getInstance ().addMessage (new ReturnPositionMessage (this, bodyFirst.getSpriteX (),
						bodyFirst.getSpriteY (), bodyFirst.getSpriteW (), bodyFirst.getSpriteH ()));
			}
		}
		else if (message.type == MessageType.move && message.objectType == ObjectType.box){
			MoveMessage msg = (MoveMessage) message;
			if (msg.deltaX != 0 && bodyFirst.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (this, msg.deltaX, 0));
			}
			if (msg.deltaY != 0 && bodyFirst.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
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
