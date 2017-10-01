package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;


public class StartGame implements Screen{
	Game game;
	
	
	StartGame (Game game){
		this.game = game;
	}
	
	@Override
	public void show (){
	
	}
	
	@Override
	public void render (float delta){
		Gdx.gl.glClearColor (0, 0, 0.2f, 1);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isTouched ()){
			System.out.println (Gdx.input.getX () + " " + Gdx.input.getY ());
		}
	}
	
	@Override
	public void resize (int width, int height){
		
	}
	
	@Override
	public void pause (){
		
	}
	
	@Override
	public void resume (){
		
	}
	
	@Override
	public void hide (){
		
	}
	
	@Override
	public void dispose (){
	}
}
