package com.game.desktop;

import com.game.GameSystem;
import com.game.MyGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher{
	public static void main (String[] arg){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration ();
		
		config.title = "Project 6";
		config.fullscreen = GameSystem.FULL_SCREEN;
		config.width = (int) GameSystem.SCREEN_W;
		config.height = (int) GameSystem.SCREEN_H;
		
		new LwjglApplication (MyGame.getInstance (), config);
	}
}
