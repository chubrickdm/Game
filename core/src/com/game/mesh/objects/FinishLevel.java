package com.game.mesh.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pools;

import com.game.mesh.objects.character.Character;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.*;

import com.introfog.primitiveIsometricEngine.*;

public class FinishLevel extends GameObject{
	private static boolean[] onFinish;
	
	private TriggeredZone triggeredZone;
	
	
	public FinishLevel (){
		objectType = ObjectType.finishLevel;
		
		onFinish = new boolean[2];
		triggeredZone = new TriggeredZone (0, 0, 1, 1, ZoneType.contains);
	}
	
	public void setBodyBounds (float x, float y, float w, float h){
		triggeredZone.setBounds (x, y, w, h);
	}
	
	@Override
	public void update (){
		if (triggeredZone.getInZone ().size () > 0){
			triggeredZone.setColor (Color.CHARTREUSE);
			for (BodyPIE tmpB : triggeredZone.getInZone ()){
				ObjectManager.getInstance ().addMessage (new WhoseBodyMessage (this, tmpB));
			}
		}
		else{
			triggeredZone.setColor (Color.FIREBRICK);
		}
		
		onFinish[0] = false;
		onFinish[1] = false;
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.isMyBody && message.objectType == ObjectType.character){
			Character character = (Character) message.object;
			onFinish[character.getName ().ordinal ()] = true;
			if (onFinish[0] && onFinish[1]){
				ObjectManager.getInstance ().addMessage (new CompleteLevelMessage ());
			}
		}
	}
	
	@Override
	public void clear (){
		onFinish[0] = false;
		onFinish[1] = false;
		triggeredZone.clear ();
		Pools.free (this);
	}
}