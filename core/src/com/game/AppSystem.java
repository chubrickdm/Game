package com.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AppSystem{
	public SpriteBatch batch;
	
	
	private static class AppSystemHolder{
		private final static AppSystem instance = new AppSystem ();
	}
	
	private AppSystem (){
		batch = new SpriteBatch ();
	}
	
	
	public static AppSystem getInstance (){
		return AppSystemHolder.instance;
	}
}
