package com.game.level;

public class LevelManager{
	private static class LevelManagerHolder{
		private final static LevelManager instance = new LevelManager ();
	}
	
	private LevelManager (){
	}
	
	
	public static LevelManager getInstance (){
		return LevelManagerHolder.instance;
	}
}
