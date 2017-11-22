package com.game.mesh.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.mesh.body.NoBodyObject;
import com.game.mesh.objects.character.Character;
import com.game.mesh.objects.character.CharacterName;
import com.game.messages.*;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class Inventory extends GameObject{
	private static final float percentPerTick = 2;
	private static final float INVENTORY_W = UNIT * 2;
	private static final float INVENTORY_H = UNIT * 2;
	private static CharacterName selectedCharacter = CharacterName.first; //имя персонажа которым происходит управление
	
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	private boolean isVisible = false;
	private float percentSize = 1;
	private CharacterName ownerName = CharacterName.unknown; //имя персонажа за которым прикреплен инвентарь
	
	
	private void pushOutMessage (GameMessage message){
		//два флага нужны, что бы не было ситуации когда персонаж упирается в два объекта, и они его 2 раза выталкивают,
		//вместо одного и сооствественно колесо выталкивается 2 раза.
		Character character = (Character) message.object;
		PushOutMessage msg = (PushOutMessage) message;
		if (ownerName == character.getName ()){
			if (msg.deltaX != 0 && !pushOutHorizontal){
				body.move (msg.deltaX, 0);
				pushOutHorizontal = true;
			}
			if (msg.deltaY != 0 && !pushOutVertical){
				body.move (0, msg.deltaY);
				pushOutVertical = true;
			}
		}
	}
	
	private void updateSizeAnimation (){
		if (Gdx.input.isKeyPressed (Input.Keys.F) && selectedCharacter == ownerName){
			isVisible = true;
			if (percentSize >= 100){
				body.setScale (1);
			}
			else{
				body.setScale (percentSize / 100);
				percentSize += percentPerTick;
			}
		}
		else{
			if (percentSize <= 1 + percentPerTick){
				isVisible = false;
				body.setScale (percentSize / 100);
			}
			else{
				isVisible = true;
				body.setScale (percentSize / 100);
				percentSize -= percentPerTick;
			}
		}
	}
	
	
	public Inventory (CharacterName ownerName){
		objectType = ObjectType.actionWheel;
		this.ownerName = ownerName;
		body = new NoBodyObject ("core/assets/images/other/action_wheel.png", 0, 0, INVENTORY_W, INVENTORY_H);
		body.setOrigin (INVENTORY_W / 2, INVENTORY_H / 2);
		body.setScale (percentSize / 100);
		body.setSpritePosition (INVENTORY_W / 2, INVENTORY_H / 2);
		dataRender = new DataRender (body.getSprite (), LayerType.over);
	}
	
	@Override
	public void update (){
		pushOutHorizontal = false;
		pushOutVertical = false;
		updateSizeAnimation ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			Character character = (Character) message.object;
			MoveMessage msg = (MoveMessage) message;
			if (ownerName == character.getName ()){
				body.move (msg.deltaX, msg.deltaY);
			}
		}
		else if (message.type == MessageType.returnStartPosition && message.objectType == ObjectType.character){
			Character character = (Character) message.object;
			ReturnStartPositionMessage msg = (ReturnStartPositionMessage) message;
			if (ownerName == character.getName ()){
				body.setSpritePosition (msg.spriteX + msg.spriteW / 2, msg.spriteY + msg.spriteH / 2);
			}
		}
		else if (message.type == MessageType.pushOut && message.objectType == ObjectType.character){
			pushOutMessage (message);
		}
		else if (message.type == MessageType.characterSelected){
			Character character = (Character) message.object;
			CharacterSelectedMessage msg = (CharacterSelectedMessage) message;
			selectedCharacter = character.getName ();
			if (ownerName == character.getName ()){
				body.setSpritePosition (msg.spriteX + msg.spriteW / 2, msg.spriteY + msg.spriteH / 2);
			}
		}
	}
	
	@Override
	public void draw (){
		if (isVisible){
			Render.getInstance ().addDataForRender (dataRender);
		}
	}
	
	@Override
	public void clear (){
		selectedCharacter = CharacterName.first;
	}
}