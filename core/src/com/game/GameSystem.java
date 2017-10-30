package com.game;

import static java.awt.Toolkit.getDefaultToolkit;


public abstract class GameSystem{
	public static final String NAME_JAR_ARCHIVE = "project6.jar";
	public static final boolean fullScreen = true;
	//public static final boolean fullScreen = false;
	public static final float SCREEN_W = getDefaultToolkit ().getScreenSize ().width;
	public static final float SCREEN_H = getDefaultToolkit ().getScreenSize ().height;
	//public static final float SCREEN_W = 800;
	//public static final float SCREEN_H = 600;
	
	public static boolean GAME_OVER = false;
	public static boolean IS_FIRST_GAME_START = true;
	public static int NUM_PASSED_LEVELS = 0;
	public static int CURRENT_LEVEL = 0;
	public static int NUM_LEVELS = 0;
}
