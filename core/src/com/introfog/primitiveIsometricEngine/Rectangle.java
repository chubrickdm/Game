package com.introfog.primitiveIsometricEngine;

import com.badlogic.gdx.graphics.Color;

public class Rectangle{
	private float x;
	private float y;
	private float w;
	private float h;
	public Color color = Color.WHITE;
	
	
	public Rectangle (float x, float y, float w, float h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void setSize (float w, float h){
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
		//т.к. мы работаем с прямоугольниками, для пересечения необходимо и достаточно что бы хотя бы
		//одна вершина одного из прямоугольников лежала в другом прямоугольнике
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
		
		else if ((x <= xx + ww) && (y <= yy + hh) && (x >= xx) && (y >= yy)){
			return true;
		}
		else if ((x + w <= xx + ww) && (y <= yy + hh) && (x + w >= xx) && (y >= yy)){
			return true;
		}
		else if ((x <= xx + ww) && (y + h <= yy + hh) && (x >= xx) && (y + h >= yy)){
			return true;
		}
		else if ((x + w <= xx + ww) && (y + h <= yy + hh) && (x + w >= xx) && (y + h >= yy)){
			return true;
		}
		
		return false;
	}
	
	public boolean contains (float xx, float yy, float ww, float hh){
		//для того что б один один прямоугольник находился в другом необходимо и достаточно что бы
		//все 4 вершины одного прямогоульника лежали в другом
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
			return true;
		}
		
		tmpI = 0;
		if ((x <= xx + ww) && (y <= yy + hh) && (x >= xx) && (y >= yy)){
			tmpI++;
		}
		if ((x + w <= xx + ww) && (y <= yy + hh) && (x + w >= xx) && (y >= yy)){
			tmpI++;
		}
		if ((x <= xx + ww) && (y + h <= yy + hh) && (x >= xx) && (y + h >= yy)){
			tmpI++;
		}
		if ((x + w <= xx + ww) && (y + h <= yy + hh) && (x + w >= xx) && (y + h >= yy)){
			tmpI++;
		}
		return tmpI == 4;
	}
}
