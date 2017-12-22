package com.introfog.primitiveIsometricEngine.messages;

import com.introfog.primitiveIsometricEngine.BodyPIE;

public class PushOutMessage extends WorldMessage{
	public float deltaX;
	public float deltaY;
	
	
	public PushOutMessage (BodyPIE bodyPIE, float deltaX, float deltaY){
		this.type = MessageType.pushOut;
		this.bodyPIE = bodyPIE;
		
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}
}
