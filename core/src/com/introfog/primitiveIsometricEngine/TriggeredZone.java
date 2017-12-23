package com.introfog.primitiveIsometricEngine;

import com.badlogic.gdx.graphics.Color;

import com.introfog.primitiveIsometricEngine.messages.*;

import java.util.LinkedList;

public class TriggeredZone extends BodyPIE{
	private ZoneType type = ZoneType.intersects;
	private LinkedList <BodyPIE> inZone;
	
	
	public TriggeredZone (float x, float y, float w, float h){
		super (x, y, w, h);
		inZone = new LinkedList <> ();
	}
	
	public TriggeredZone (float x, float y, float w, float h, Color color){
		super (x, y, w, h, color);
		inZone = new LinkedList <> ();
	}
	
	public TriggeredZone (float x, float y, float w, float h, ZoneType type){
		super (x, y, w, h);
		this.type = type;
		inZone = new LinkedList <> ();
	}
	
	public TriggeredZone (float x, float y, float w, float h, ZoneType type, Color color){
		super (x, y, w, h, color);
		this.type = type;
		inZone = new LinkedList <> ();
	}
	
	public void setColor (Color color){
		body.color = color;
	}
	
	@Override
	public void sendMessage (WorldMessage message){
		if (message.type == MessageType.move && message.bodyPIE != this){
			MoveMessage msg = (MoveMessage) message;
			Rectangle rect = msg.bodyPIE.body;
			if (type == ZoneType.intersects){
				if (body.intersects (rect.getX () + msg.deltaX, rect.getY () + msg.deltaY, rect.getW (), rect.getH ())){
					if (!inZone.contains (msg.bodyPIE)){
						inZone.add (msg.bodyPIE);
					}
				}
				else{
					inZone.remove (msg.bodyPIE);
				}
			}
			else if (type == ZoneType.contains){
				if (body.contains (rect.getX () + msg.deltaX, rect.getY () + msg.deltaY, rect.getW (), rect.getH ())){
					if (!inZone.contains (msg.bodyPIE)){
						inZone.add (msg.bodyPIE);
					}
				}
				else{
					inZone.remove (msg.bodyPIE);
				}
			}
		}
	}
	
	@Override
	public void drawBody (){
		RenderWorld.getInstance ().addRectangle (body);
	}
	
	public void clear (){
		inZone.clear ();
	}
	
	public boolean check (float x, float y, float w, float h){
		if (isGhost){
			return false;
		}
		if (type == ZoneType.intersects){
			return body.intersects (x, y, w, h);
		}
		else if (type == ZoneType.contains){
			return body.contains (x, y, w, h);
		}
		return false;
	}
	
	public LinkedList <BodyPIE> getInZone (){
		return inZone;
	}
}
