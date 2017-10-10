package com.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.Main;

import java.awt.*;

public class DesktopLauncher{
	public static void main (String[] arg){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration ();
		
		config.title = "Project 6";
		config.fullscreen = true;
		java.awt.Dimension sSize = java.awt.Toolkit.getDefaultToolkit ().getScreenSize ();
		config.width = sSize.width;
		config.height = sSize.height;
		//config.width = 800;
		//config.height = 600;
		
		new LwjglApplication (new Main (), config);
	}
}
