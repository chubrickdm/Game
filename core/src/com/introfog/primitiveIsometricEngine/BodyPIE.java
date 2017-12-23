package com.introfog.primitiveIsometricEngine;

import com.badlogic.gdx.graphics.Color;

import com.introfog.primitiveIsometricEngine.messages.*;

public class BodyPIE{
	protected boolean isGhost = false;
	protected Rectangle body;
	
	private float friction = 1;
	private BodyType type = BodyType.statical;
	
	
	public BodyPIE (float x, float y, float w, float h){
		body = new Rectangle (x, y, w, h);
		World.getInstance ().addObject (this);
	}
	
	public BodyPIE (float x, float y, float w, float h, Color color){
		body = new Rectangle (x, y, w, h);
		body.color = color;
		World.getInstance ().addObject (this);
	}
	
	public BodyPIE (float x, float y, float w, float h, BodyType type, float friction){
		this.friction = friction;
		this.type = type;
		body = new Rectangle (x, y, w, h);
		World.getInstance ().addObject (this);
	}
	
	public BodyPIE (float x, float y, float w, float h, BodyType type, float friction, Color color){
		this.friction = friction;
		this.type = type;
		body = new Rectangle (x, y, w, h);
		body.color = color;
		World.getInstance ().addObject (this);
	}
	
	public boolean isGhost (){
		return isGhost;
	}
	
	public void setGhost (boolean isGhost){
		this.isGhost = isGhost;
	}
	
	public void move (float deltaX, float deltaY){
		if (isGhost){
			return;
		}
		World.getInstance ().addMessage (new MoveMessage (this, deltaX, deltaY));
		body.move (deltaX, deltaY);
	}
	
	public void drawBody (){
		RenderWorld.getInstance ().addRectangle (body);
	}
	
	public void sendMessage (WorldMessage message){
		if (message.type == MessageType.move && message.bodyPIE != this){
			MoveMessage msg = (MoveMessage) message;
			Rectangle rect = msg.bodyPIE.body;
			if (msg.deltaX != 0 && body.intersects (rect.getX () + msg.deltaX, rect.getY (), rect.getW (), rect.getH ())){
				if (body.contains (rect.getX () + msg.deltaX, rect.getY (), rect.getW (), rect.getH ())){
					return;
				}
				if (type == BodyType.statical){
					World.getInstance ().addMessage (new PushOutMessage (msg.bodyPIE, -msg.deltaX, 0));
				}
				else if (type == BodyType.dynamical){
					move (msg.deltaX * friction, 0);
					World.getInstance ().addMessage (new PushOutMessage (msg.bodyPIE, -msg.deltaX * (1 - friction), 0));
				}
			}
			if (msg.deltaY != 0 && body.intersects (rect.getX (), rect.getY () + msg.deltaY, rect.getW (), rect.getH ())){
				if (body.contains (rect.getX (), rect.getY () + msg.deltaY, rect.getW (), rect.getH ())){
					return;
				}
				if (type == BodyType.statical){
					World.getInstance ().addMessage (new PushOutMessage (msg.bodyPIE, 0, -msg.deltaY));
				}
				else if (type == BodyType.dynamical){
					move (0, msg.deltaY * friction);
					World.getInstance ().addMessage (new PushOutMessage (msg.bodyPIE, 0, -msg.deltaY * (1 - friction)));
				}
			}
		}
		else if (message.type == MessageType.pushOut && message.bodyPIE == this){
			PushOutMessage msg = (PushOutMessage) message;
			move (msg.deltaX, msg.deltaY);
		}
	}
	
	public void setBodyType (BodyType type){
		this.type = type;
	}
	
	public void setPosition (float x, float y){
		body.setPosition (x, y);
	}
	
	public void setBounds (float x, float y, float w, float h){
		body.setBounds (x, y, w, h);
	}
	
	public float getX (){
		return body.getX ();
	}
	
	public float getY (){
		return body.getY ();
	}
	
	public float getW (){
		return body.getW ();
	}
	
	public float getH (){
		return body.getH ();
	}
}