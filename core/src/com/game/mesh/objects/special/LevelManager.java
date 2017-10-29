package com.game.mesh.objects.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.game.GameSystem;
import com.game.MyGame;
import com.game.addition.parsers.ParseLevel;
import com.game.addition.parsers.ParseSettings;
import com.game.mesh.objects.ActionWheel;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.camera.Camera;
import com.game.mesh.objects.character.Character;
import com.game.messages.GameMessage;
import com.game.mesh.objects.GameObject;
import com.game.messages.MessageType;
import com.game.screens.SelectedModeScreen;

public class LevelManager extends GameObject{
	private static class LevelManagerHolder{
		private final static LevelManager instance = new LevelManager ();
	}
	
	private LevelManager (){ }
	
	
	public static LevelManager getInstance (){
		return LevelManagerHolder.instance;
	}
	
	public void newGame (){
		GameSystem.IS_FIRST_GAME_START = true;
		GameSystem.CURRENT_LEVEL = 1;
		GameSystem.NUM_PASSED_LEVELS = 0;
		ParseSettings.writeSettings ();
	}
	
	public void createLevel (){
		ObjectManager.getInstance ();
		ParseLevel.parseLVL (GameSystem.CURRENT_LEVEL);
		
		ObjectManager.getInstance ().addObject (ActionWheel.getInstance ());
		ObjectManager.getInstance ().addObject (Camera.getInstance ());
		ObjectManager.getInstance ().addObject (this);
		
		if (GameSystem.IS_FIRST_GAME_START){
			GameSystem.IS_FIRST_GAME_START = false;
			ParseSettings.writeSettings ();
		}
	}
	
	public void updateLevel (){
		ObjectManager.getInstance ().update ();
		ObjectManager.getInstance ().draw ();
	}
	
	public void closeLevel (){
		ObjectManager.getInstance ().clear ();
	}
	
	@Override
	public void update (){
		if (Gdx.input.isKeyJustPressed (Input.Keys.ESCAPE)){
			MyGame.getInstance ().setScreen (new SelectedModeScreen ());
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.levelComplete){
			MyGame.getInstance ().setScreen (new SelectedModeScreen ());
		}
	}
	
	@Override
	public void draw (){ }
}
