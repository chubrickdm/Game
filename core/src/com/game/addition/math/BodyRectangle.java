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
	
	public boolean intersects (float xx, float yy, float ww, float hh){
		if ((xx <= x + w) && (yy <= y + h) && (xx >= x) && (yy >= y)){
			return true;
		}
		else if ((xx + ww <= x + w) && (yy <= y + h) && (xx + ww >= x) && (yy >= y)){
			return true;
		}
		else if ((xx <= x + w) && (yy + hh <= y + h) && (xx >= x) && (yy + hh >= y)){
			return true;
		}
		else if ((xx + ww <= x + w) && (yy + hh <= y + h) && (xx + ww >= x) && (yy + hh >= y)){
			return true;
		}
		
		return false;
	}
	
	public boolean contains (float xx, float yy, float ww, float hh){
		int tmpI = 0;
		
		if ((xx <= x + w) && (yy <= y + h) && (xx >= x) && (yy >= y)){
			tmpI++;
		}
		if ((xx + ww <= x + w) && (yy <= y + h) && (xx + ww >= x) && (yy >= y)){
			tmpI++;
		}
		if ((xx <= x + w) && (yy + hh <= y + h) && (xx >= x) && (yy + hh >= y)){
			tmpI++;
		}
		if ((xx + ww <= x + w) && (yy + hh <= y + h) && (xx + ww >= x) && (yy + hh >= y)){
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
