package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import com.game.addition.parsers.ParseSettings;
import com.game.mesh.objects.GameObject;
import com.game.screens.MainMenuScreen;

public class MyGame extends Game{
	public World world = new World (new Vector2 (0, 0), true);
	
	public static final float BUTTON_W = 250 * GameObject.ASPECT_RATIO;
	public static final float BUTTON_H = 55 * GameObject.ASPECT_RATIO;
	public static final float DISTANCE_BETWEEN_BUTTONS = 15 * GameObject.ASPECT_RATIO;
	public static final int   BUTTON_FONT_SIZE = (int) (3 * BUTTON_H / 5);
	
	
	private static class MyGameHolder{
		private final static MyGame instance = new MyGame ();
	}
	
	private MyGame (){ }
	
	
	public static MyGame getInstance (){
		return MyGameHolder.instance;
	}
	
	@Override
	public void create (){
		ParseSettings.parseSettings ();
		setScreen (MainMenuScreen.getInstance ());
	}
}