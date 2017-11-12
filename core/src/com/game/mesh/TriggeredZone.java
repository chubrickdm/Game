package com.game.mesh;

import com.game.addition.math.BodyRectangle;

public class TriggeredZone{
	private float originY = 0;
	private float originX = 0;
	private BodyRectangle zone;
	
	
	public TriggeredZone (float x, float y, float w, float h){
		zone = new BodyRectangle (x, y, w, h);
	}
	
	public boolean isTriggered (float x, float y, float w, float h){
		return zone.intersects (x, y, w, h);
	}
	
	public void setPosition (float x, float y){
		//zone.setPosition (x );
	}
}
