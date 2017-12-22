package com.introfog.primitiveIsometricEngine.messages;

import com.introfog.primitiveIsometricEngine.BodyPIE;

public class MoveMessage extends WorldMessage{
	public float deltaX;
	public float deltaY;
	
	
	public MoveMessage (BodyPIE bodyPIE, float deltaX, float deltaY){
		this.type = MessageType.move;
		this.bodyPIE = bodyPIE;
		
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}
}
