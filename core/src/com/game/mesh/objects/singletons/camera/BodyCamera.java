package com.game.mesh.objects.singletons.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.game.GameSystem;

public class BodyCamera{
	private float x;
	private float y;
	private OrthographicCamera camera; //у камеры нет метода setPosition, поэтому пришлось делать оболочку
	
	
	public BodyCamera (){
		x = GameSystem.SCREEN_W / 2;
		y = GameSystem.SCREEN_H / 2;
		camera = new OrthographicCamera (GameSystem.SCREEN_W, GameSystem.SCREEN_H);
		camera.setToOrtho (false);
	}
	
	public void update (){
		camera.update ();
	}
	
	public Matrix4 getProjectionMatrix (){
		return camera.combined;
	}
	
	public OrthographicCamera getCamera (){
		return camera;
	}
	
	public void moveY (float deltaY){
		camera.translate (0, deltaY);
		y += deltaY;
	}
	
	public void move (float deltaX, float deltaY){
		camera.translate (deltaX, deltaY);
		x += deltaX;
		y += deltaY;
	}
	
	public void setPositionY (float y){
		camera.translate (0, y - this.y);
		this.y = y;
	}
	
	public void setPosition (float x, float y){
		camera.translate (x - this.x, y - this.y);
		this.x = x;
		this.y = y;
	}
}