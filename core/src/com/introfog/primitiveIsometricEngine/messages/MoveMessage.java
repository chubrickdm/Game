package com.introfog.primitiveIsometricEngine.messages;

import com.introfog.primitiveIsometricEngine.Body;

public class MoveMessage extends WorldMessage{
	public float deltaX;
	public float deltaY;
	
	
	public MoveMessage (Body body, float deltaX, float deltaY){
		this.type = MessageType.move;
		this.body = body;
		
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}
}
