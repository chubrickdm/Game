package com.game.mesh.objects.singletons.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.utils.Pools;
import com.game.GameSystem;
import com.game.MyGame;
import com.game.addition.parsers.ParseLevel;
import com.game.addition.parsers.ParseSettings;
import com.game.mesh.objects.singletons.Chain;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.Inventory;
import com.game.mesh.objects.character.CharacterName;
import com.game.mesh.objects.singletons.Camera;
import com.game.messages.AddObjectMessage;
import com.game.messages.GameMessage;
import com.game.messages.GetStartPositionMessage;
import com.game.messages.MessageType;
import com.game.screens.SelectedModeScreen;
import com.introfog.primitiveIsometricEngine.World;

public class LevelManager extends GameObject{
	private void completeLevel (){
		GameObject.triggered[0] = null;
		GameObject.triggered[1] = null;
		ObjectManager.getInstance ().clear ();
		
		if (GameSystem.CURRENT_LEVEL != GameSystem.NUM_LEVELS){
			GameSystem.CURRENT_LEVEL++;
		}
		else{
			GameSystem.GAME_OVER = true;
		}
		
		if (GameSystem.NUM_PASSED_LEVELS != GameSystem.NUM_LEVELS){
			GameSystem.NUM_PASSED_LEVELS++;
		}
		ParseSettings.writeSettings ();
		
		MyGame.getInstance ().setScreen (SelectedModeScreen.getInstance ());
	}
	
	private void closeLevel (){
		GameObject.triggered[0] = null;
		GameObject.triggered[1] = null;
		ObjectManager.getInstance ().clear ();
		MyGame.getInstance ().setScreen (SelectedModeScreen.getInstance ());
	}
	
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
		GameSystem.GAME_OVER = false;
		
		ParseSettings.writeSettings ();
	}
	
	public void createLevel (){
		ParseLevel.parseLVL (GameSystem.CURRENT_LEVEL);
		
		Inventory inventory = Pools.obtain (Inventory.class);
		inventory.setOwnerName (CharacterName.first);
		ObjectManager.getInstance ().sendMessage (new AddObjectMessage (inventory));
		inventory = Pools.obtain (Inventory.class);
		inventory.setOwnerName (CharacterName.second);
		ObjectManager.getInstance ().sendMessage (new AddObjectMessage (inventory));
		ObjectManager.getInstance ().sendMessage (new AddObjectMessage (Camera.getInstance ()));
		ObjectManager.getInstance ().sendMessage (new AddObjectMessage (Chain.getInstance ()));
		ObjectManager.getInstance ().sendMessage (new AddObjectMessage (this));
		
		if (GameSystem.IS_FIRST_GAME_START){
			GameSystem.IS_FIRST_GAME_START = false;
			ParseSettings.writeSettings ();
		}
		
		//важно здесь отослать это сообщение, что б инвентари и камера смогли получить начальные позиции персонажей.
		ObjectManager.getInstance ().addMessage (new GetStartPositionMessage ());
	}
	
	public void updateLevel (){
		ObjectManager.getInstance ().update ();
		ObjectManager.getInstance ().draw ();
	}
	
	@Override
	public void update (){
		if (Gdx.input.isKeyJustPressed (Input.Keys.ESCAPE)){
			closeLevel ();
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.levelComplete){
			completeLevel ();
		}
		else if (message.type == MessageType.playerLost){
			closeLevel ();
		}
	}
}