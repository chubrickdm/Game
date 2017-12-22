package com.game.messages;

import com.game.mesh.objects.GameObject;
import com.introfog.primitiveIsometricEngine.BodyPIE;

public class IsMyBodyMessage extends GameMessage{
	public BodyPIE bodyPIE;
	
	
	public IsMyBodyMessage (GameObject object, BodyPIE bodyPIE){
		this.type = MessageType.isMyBody;
		this.object = object;
		this.objectType = object.objectType;
		
		this.bodyPIE = bodyPIE;
	}
}
