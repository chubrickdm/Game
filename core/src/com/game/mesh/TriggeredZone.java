package com.game.mesh;

import com.game.addition.math.BodyRectangle;

public class TriggeredZone{
	private float originY;
	private float originX;
	private BodyRectangle zone;
	
	
	public TriggeredZone (float x, float y, float w, float h){
		zone = new BodyRectangle (x, y, w, h);
	}
	
	public boolean isTriggered (float x, float y, float w, float h){
		return zone.intersects (x, y, w, h);
	}
	
	public void setPosition (float x, float y){
		zone.setPosition (x - originX, y - originY);
	}
	
	public void setOrigin (float originX, float originY){
		this.originX = originX;
		this.originY = originY;
		setPosition (zone.getX (), zone.getY ());
	}
	
	public void move (float deltaX, float deltaY){
		zone.move (deltaX, deltaY);
	}
}