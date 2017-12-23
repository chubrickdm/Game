package com.game.mesh.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pools;

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
	private float originX;
	private float originY;
	private float percentSize = 1;
	private CharacterName ownerName; //имя персонажа за которым прикреплен инвентарь
	private Sprite sprite;
	
	
	private void setScale (float scale){
		sprite.setScale (scale);
		sprite.setOriginCenter ();
	}
	
	private void updateSizeAnimation (){
		if (Gdx.input.isKeyPressed (Input.Keys.F) && selectedCharacter == ownerName){
			isVisible = true;
			if (percentSize >= 100){
				setScale (1);
			}
			else{
				setScale (percentSize / 100);
				percentSize += percentPerTick;
			}
		}
		else{
			if (percentSize <= 1 + percentPerTick){
				isVisible = false;
				setScale (percentSize / 100);
			}
			else{
				isVisible = true;
				setScale (percentSize / 100);
				percentSize -= percentPerTick;
			}
		}
	}
	
	
	public Inventory (){
		objectType = ObjectType.actionWheel;
		this.ownerName = CharacterName.unknown;
		
		Texture texture = new Texture ("core/assets/images/other/action_wheel.png");
		sprite = new Sprite (texture);
		sprite.setBounds (0, 0, INVENTORY_W, INVENTORY_H);
		originX = INVENTORY_W / 2;
		originY = INVENTORY_H / 2;
		setScale (percentSize / 100);
		dataRender = new DataRender (sprite, LayerType.over);
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
		if (message.type == MessageType.returnPosition && message.objectType == ObjectType.character){
			Character character = (Character) message.object;
			selectedCharacter = character.getName ();
			if (ownerName == character.getName ()){
				ReturnPositionMessage msg = (ReturnPositionMessage) message;
				sprite.setPosition (msg.sprite.getX () + msg.sprite.getW () / 2 - originX,
									msg.sprite.getY () + msg.sprite.getH () / 2 - originY);
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