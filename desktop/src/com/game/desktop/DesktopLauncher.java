package com.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.MyGame;


public class DesktopLauncher{
	public static void main (String[] arg){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration ();
		
		config.title = "Project 6";
		config.fullscreen = true;
		java.awt.Dimension size = java.awt.Toolkit.getDefaultToolkit ().getScreenSize ();
		config.width = size.width;
		config.height = size.height;
		//config.width = 800;
		//config.height = 600;
		
		new LwjglApplication (new MyGame (), config);
	}
}
