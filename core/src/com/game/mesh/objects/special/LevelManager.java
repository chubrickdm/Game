package com.game.mesh.objects.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.game.MyGame;
import com.game.messages.GameMessage;
import com.game.mesh.objects.GameObject;
import com.game.screens.MainMenuScreen;
import com.game.screens.SelectedModeScreen;


public class LevelManager extends GameObject{
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
			MyGame.getInstance ().setScreen (new SelectedModeScreen ());
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){ }
	
	@Override
	public void draw (){ }
}
