package com.game.messages;

import com.game.mesh.objects.GameObject;

import com.introfog.primitiveIsometricEngine.BodyPIE;

public class WhoseBodyMessage extends GameMessage{
	public BodyPIE bodyPIE;
	
	
	public WhoseBodyMessage (GameObject object, BodyPIE bodyPIE){
		this.type = MessageType.whoseBody;
		this.object = object;
		this.objectType = object.objectType;
		
		this.bodyPIE = bodyPIE;
	}
}
