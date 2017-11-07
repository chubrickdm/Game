package com.game.mesh.objects.character;

import com.game.GameSystem;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.character.first.CharacterAnimations;
import com.game.mesh.objects.character.first.CharacterControl;
import com.game.messages.*;
import com.game.mesh.body.NoSpriteObject;
import com.game.mesh.objects.singletons.special.ObjectManager;

import static com.game.mesh.objects.character.first.CharacterAnimations.CHARACTER_H;
import static com.game.mesh.objects.character.first.CharacterAnimations.CHARACTER_W;

public class Character extends GameObject{
	private static final float BODY_CHARACTER_W = 2 * CHARACTER_W / 5;
	private static final float BODY_CHARACTER_H = CHARACTER_H / 4;
	
	private boolean isMove = false;
	private boolean isFall = false;
	private boolean isSelected = false;
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	private CharacterName name = CharacterName.unknown;
	private ActionType action;
	private CharacterControl control;
	private CharacterAnimations animations;
	
	
	public Character (float x, float y){
		objectType = ObjectType.character;
		if (x < GameSystem.SCREEN_W / 2){
			isSelected = true;
			name = CharacterName.first;
		}
		else{
			isSelected = false;
			name = CharacterName.second;
		}
		
		body = new NoSpriteObject (x, y, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H);
		body.move (0, 0.25f);
		
		control = new CharacterControl (this, body);
		animations = new CharacterAnimations (body);
		action = animations.getAction ();
	}
	
	public boolean getIsSelected (){
		return isSelected;
	}
	
	public CharacterName getName (){
		return name;
	}
	
	@Override
	public void update (){
		pushOutHorizontal = false;
		pushOutVertical = false;
		animations.update (action, isFall, isMove);
		action = animations.getAction ();
		if (isSelected && !isFall){
			control.update ();
			action = control.getAction ();
			isMove = control.getIsMove ();
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.characterChange){
			if (isSelected){
				isSelected = false;
			}
			else{
				ObjectManager.getInstance ().addMessage (new CharacterSelectedMessage (this, body.getSpriteX (),
						body.getSpriteY (), body.getSpriteW (), body.getSpriteH ()));
				isSelected = true;
			}
		}
		else if (message.type == MessageType.move && message.object != this && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, -msg.deltaX, -msg.deltaY));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == this){
			PushOutMessage msg = (PushOutMessage) message;
			if (msg.deltaX != 0 && !pushOutHorizontal){
				body.move (msg.deltaX, 0);
				pushOutHorizontal = true;
			}
			if (msg.deltaY != 0 && !pushOutVertical){
				body.move (0, msg.deltaY);
				pushOutVertical = true;
			}
		}
		else if (message.type == MessageType.getPosition){
			ObjectManager.getInstance ().addMessage (new ReturnPositionMessage (this, body.getSpriteX (),
					body.getSpriteY (), body.getSpriteW (), body.getSpriteH ()));
		}
		else if (message.type == MessageType.move && message.objectType == ObjectType.box){
			MoveMessage msg = (MoveMessage) message;
			if (msg.deltaX != 0 && body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (this, msg.deltaX, 0));
			}
			if (msg.deltaY != 0 && body.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
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