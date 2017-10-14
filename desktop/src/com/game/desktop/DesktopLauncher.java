package com.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.MyGame;
import com.game.objects.GameObject;


public class DesktopLauncher{
	public static void main (String[] arg){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration ();
		
		config.title = "Project 6";
		config.fullscreen = true;
		config.width = (int) GameObject.SCREEN_W;
		config.height = (int) GameObject.SCREEN_H;
		
		new LwjglApplication (MyGame.getInstance (), config);
	}
}
