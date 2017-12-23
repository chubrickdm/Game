package com.game.messages;

import com.game.mesh.objects.*;

import com.introfog.primitiveIsometricEngine.BodyPIE;

public class DestroyObjectMessage extends GameMessage{ //создается когда объект умирает, уничтожается
	public BodyPIE bodyPIE;
	
	
	public DestroyObjectMessage (GameObject destroyer, BodyPIE bodyPIE){
		this.type = MessageType.destroyObject;
		this.object = destroyer;
		this.objectType = destroyer.objectType;
		
		this.bodyPIE = bodyPIE;
	}
}
