package com.game.mesh.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Pools;

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
	
	private boolean isVisible = false;
	private float percentSize = 1;
	private CharacterName ownerName; //имя персонажа за которым прикреплен инвентарь
	
	
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
	
	
	public Inventory (){
		objectType = ObjectType.actionWheel;
		this.ownerName = CharacterName.unknown;
		
		body = new NoBodyObject ("core/assets/images/other/action_wheel.png", 0, 0, INVENTORY_W, INVENTORY_H);
		body.setOrigin (INVENTORY_W / 2, INVENTORY_H / 2);
		body.setScale (percentSize / 100);
		body.setSpritePosition (INVENTORY_W / 2, INVENTORY_H / 2);
		dataRender = new DataRender (body.getSprite (), LayerType.over);
	}
	
	public void setOwnerName (CharacterName ownerName){
		this.ownerName = ownerName;
	}
	
	@Override
	public void update (){
		updateSizeAnimation ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.returnStartPosition && message.objectType == ObjectType.character){
			Character character = (Character) message.object;
			if (ownerName == character.getName ()){
				ReturnStartPositionMessage msg = (ReturnStartPositionMessage) message;
				body.setSpritePosition (msg.sprite.getX () + msg.sprite.getW () / 2, msg.sprite.getY () + msg.sprite.getH () / 2);
			}
		}
		else if (message.type == MessageType.characterSelected){
			Character character = (Character) message.object;
			selectedCharacter = character.getName ();
			if (ownerName == character.getName ()){
				CharacterSelectedMessage msg = (CharacterSelectedMessage) message;
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
		
		isVisible = false;
		percentSize = 1;
		Pools.free (this);
	}
}