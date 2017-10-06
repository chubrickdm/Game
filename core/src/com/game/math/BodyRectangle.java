package com.game.math;

public class BodyRectangle{
	private float x;
	private float y;
	private float w;
	private float h;
	
	
	public BodyRectangle (float x, float y, float w, float h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void setPosition (float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void move (float deltaX, float deltaY){
		x += deltaX;
		y += deltaY;
	}
	
	public float getX (){
		return x;
	}
	
	public float getY (){
		return y;
	}
	
	public float getW (){
		return w;
	}
	
	public float getH (){
		return h;
	}
	
	public boolean intersects (BodyRectangle rect){
		if ((rect.getX () <= x + w) && (rect.getY () >= y - h) &&
				(rect.getX () >= x) && (rect.getY () <= y)){
			return true;
		}
		else if ((rect.getX () + rect.getW () <= x + w) && (rect.getY () >= y - h) &&
				(rect.getX () + rect.getW () >= x) && (rect.getY () <= y)){
			return true;
		}
		else if ((rect.getX () <= x + w) && (rect.getY () - rect.getH () >= y - h) &&
				(rect.getX () >= x) && (rect.getY () - rect.getH () <= y)){
			return true;
		}
		else if ((rect.getX () + rect.getW () <= x + w) && (rect.getY () - rect.getH () >= y - h) &&
				(rect.getX () + rect.getW () >= x) && (rect.getY () - rect.getH () <= y)){
			return true;
		}
		
		return false;
	}
}
