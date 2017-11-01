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
		if ((this.x <= x + w) && (this.y <= y + h) &&
				(this.x >= x) && (this.y >= y)){
			return true;
		}
		else if ((this.x + this.w <= x + w) && (this.y <= y + h) &&
				(this.x + this.w >= x) && (this.y >= y)){
			return true;
		}
		else if ((this.x <= x + w) && (this.y + this.h <= y + h) &&
				(this.x >= x) && (this.y + this.h >= y)){
			return true;
		}
		else if ((this.x + this.w <= x + w) && (this.y + this.h <= y + h) &&
				(this.x + this.w >= x) && (this.y + this.h >= y)){
			return true;
		}
		
		return false;
	}
	
	public boolean contains (float x, float y, float w, float h){
		int tmpI = 0;
		
		if ((this.x <= x + w) && (this.y <= y + h) &&
				(this.x >= x) && (this.y >= y)){
			tmpI++;
		}
		if ((this.x + this.w <= x + w) && (this.y <= y + h) &&
				(this.x + this.w >= x) && (this.y >= y)){
			tmpI++;
		}
		if ((this.x <= x + w) && (this.y + this.h <= y + h) &&
				(this.x >= x) && (this.y + this.h >= y)){
			tmpI++;
		}
		if ((this.x + this.w <= x + w) && (this.y + this.h <= y + h) &&
				(this.x + this.w >= x) && (this.y + this.h >= y)){
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
