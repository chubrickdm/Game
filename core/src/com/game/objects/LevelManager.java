package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.game.MyGame;
import com.game.messages.GameMessage;
import com.game.screens.MainMenuScreen;


public class LevelManager implements GameObject{
	private static class LevelManagerHolder{
		private final static LevelManager instance = new LevelManager ();
	}
	
	private LevelManager (){ }
	
	
	public static LevelManager getInstance (){
		return LevelManagerHolder.instance;
	}
	
	@Override
	public void update (){
		if (Gdx.input.isKeyJustPressed (Input.Keys.ESCAPE)){
			MyGame.getInstance ().setScreen (new MainMenuScreen ());
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){ }
	
	@Override
	public void draw (){ }
}