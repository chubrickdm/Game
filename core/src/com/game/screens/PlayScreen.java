package com.game.screens;

import com.badlogic.gdx.Screen;

import com.game.mesh.objects.singletons.special.LevelManager;

public class PlayScreen implements Screen{
	private static class PlayScreenHolder{
		private final static PlayScreen instance = new PlayScreen ();
	}
	
	private PlayScreen (){ }
	
	
	public static PlayScreen getInstance (){
		return PlayScreenHolder.instance;
	}
	
	@Override
	public void show (){
		LevelManager.getInstance ().createLevel ();
	}
	
	@Override
	public void render (float delta){
		LevelManager.getInstance ().updateLevel ();
	}
	
	@Override
	public void resize (int width, int height){ }
	
	@Override
	public void pause (){ }
	
	@Override
	public void resume (){ }
	
	@Override
	public void hide (){ }
	
	@Override
	public void dispose (){ }
}
