package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.screens.MainMenuScreen;

import static java.awt.Toolkit.getDefaultToolkit;

public abstract class GameSystem{
	//public static final boolean fullScreen = true;
	public static final boolean fullScreen = false;
	//public static final float SCREEN_W = getDefaultToolkit ().getScreenSize ().width;
	//public static final float SCREEN_H = getDefaultToolkit ().getScreenSize ().height;
	public static final float SCREEN_W = 800;
	public static final float SCREEN_H = 600;
	
	
	public static void update (){
		if (Gdx.input.isKeyJustPressed (Input.Keys.ESCAPE)){
			MyGame.getInstance ().setScreen (new MainMenuScreen ());
		}
	}
}
