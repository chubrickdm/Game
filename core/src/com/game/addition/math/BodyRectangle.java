package com.game.addition.math;

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
	
	public boolean intersects (float x, float y, float w, float h){
		if ((x <= this.x + this.w) && (y <= this.y + this.h) && (x >= this.x) && (y >= this.y)){
			return true;
		}
		else if ((x + w <= this.x + this.w) && (y <= this.y + this.h) && (x + w >= this.x) && (y >= this.y)){
			return true;
		}
		else if ((x <= this.x + this.w) && (y + h <= this.y + this.h) && (x >= this.x) && (y + h >= this.y)){
			return true;
		}
		else if ((x + w <= this.x + this.w) && (y + h <= this.y + this.h) && (x + w >= this.x) && (y + h >= this.y)){
			return true;
		}
		
		return false;
	}
	
	public boolean contains (float x, float y, float w, float h){
		int tmpI = 0;
		
		if ((x <= this.x + this.w) && (y <= this.y + this.h) && (x >= this.x) && (y >= this.y)){
			tmpI++;
		}
		else if ((x + w <= this.x + this.w) && (y <= this.y + this.h) && (x + w >= this.x) && (y >= this.y)){
			tmpI++;
		}
		else if ((x <= this.x + this.w) && (y + h <= this.y + this.h) && (x >= this.x) && (y + h >= this.y)){
			tmpI++;
		}
		else if ((x + w <= this.x + this.w) && (y + h <= this.y + this.h) && (x + w >= this.x) && (y + h >= this.y)){
			tmpI++;
		}
		
		if (tmpI == 4){
			return  true;
		}
		else{
			return false;
		}
	}
}
