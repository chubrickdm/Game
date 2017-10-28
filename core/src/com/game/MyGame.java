package com.game;

import com.badlogic.gdx.Game;

import com.game.addition.ParseXML;
import com.game.mesh.objects.GameObject;
import com.game.screens.MainMenuScreen;

public class MyGame extends Game{
	public static final float BUTTON_W = 250 * GameObject.ASPECT_RATIO;
	public static final float BUTTON_H = 55 * GameObject.ASPECT_RATIO;
	public static final int   BUTTON_FONT_SIZE = (int) (3 * BUTTON_H / 5);
	public static final float DISTANCE_BETWEEN_BUTTONS = 15 * GameObject.ASPECT_RATIO;
	
	
	private static class MyGameHolder{
		private final static MyGame instance = new MyGame ();
	}
	
	private MyGame (){}
	
	
	public static MyGame getInstance (){
		return MyGameHolder.instance;
	}
	
	@Override
	public void create (){
		ParseXML.parseSettings ();
		setScreen (new MainMenuScreen ());
	}
}
