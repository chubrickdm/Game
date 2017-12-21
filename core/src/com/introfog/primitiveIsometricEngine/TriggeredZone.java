package com.introfog.primitiveIsometricEngine;

import com.badlogic.gdx.graphics.Color;

import com.introfog.primitiveIsometricEngine.messages.*;

import java.util.LinkedList;

public class TriggeredZone extends Body{
	private ZoneType type = ZoneType.intersects;
	private LinkedList<Body> inZone;
	
	
	public TriggeredZone (float x, float y, float w, float h){
		super (x, y, w, h);
		inZone = new LinkedList <>  ();
	}
	
	public TriggeredZone (float x, float y, float w, float h, Color color){
		super (x, y, w, h, color);
		inZone = new LinkedList <>  ();
	}
	
	public TriggeredZone (float x, float y, float w, float h, ZoneType type){
		super (x, y, w, h);
		this.type = type;
		inZone = new LinkedList <>  ();
	}
	
	public TriggeredZone (float x, float y, float w, float h, ZoneType type, Color color){
		super (x, y, w, h, color);
		this.type = type;
		inZone = new LinkedList <>  ();
	}
	
	@Override
	public void sendMessage (WorldMessage message){
		if (message.type == MessageType.move && message.body != this){
			MoveMessage msg = (MoveMessage) message;
			Rectangle rect = msg.body.body;
			if (type == ZoneType.intersects){
				if (body.intersects (rect.getX () + msg.deltaX, rect.getY () + msg.deltaY, rect.getW (), rect.getH ())){
					if (!inZone.contains (msg.body)){
						inZone.add (msg.body);
					}
				}
				else{
					inZone.remove (msg.body);
				}
			}
			else if (type == ZoneType.contains){
				if (body.contains (rect.getX () + msg.deltaX, rect.getY () + msg.deltaY, rect.getW (), rect.getH ())){
					if (!inZone.contains (msg.body)){
						inZone.add (msg.body);
					}
				}
				else{
					inZone.remove (msg.body);
				}
			}
		}
	}
	
	@Override
	public void drawBody (){
		RenderWorld.getInstance ().addRectangle (body);
	}
	
	public LinkedList <Body> getInZone (){
		return inZone;
	}
}
