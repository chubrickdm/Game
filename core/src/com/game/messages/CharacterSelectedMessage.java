package com.game.messages;

import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;

public class CharacterSelectedMessage extends GameMessage{ //собщение создается когда был смене персонаж
	public float spriteX;
	public float spriteY;
	public float spriteW;
	public float spriteH;
	
	
	public CharacterSelectedMessage (GameObject object, float spriteX, float spriteY, float spriteW, float spriteH){
		this.type = MessageType.characterSelected;
		this.object = object; //ссылка на персонажа которым сейчас управляют
		this.objectType = ObjectType.character;
		
		this.spriteX = spriteX;
		this.spriteY = spriteY;
		this.spriteW = spriteW;
		this.spriteH = spriteH;
	}
}
