package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class Main extends Game{
	@Override
	public void create () {
		//InputController inputProcessor = new InputController ();
		//Gdx.input.setInputProcessor(inputProcessor);
		setScreen (new StartGame (this));
	}
}
