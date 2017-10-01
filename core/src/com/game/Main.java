package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.object.ObjectManager;

public class Main extends Game{
	@Override
	public void create () {
		AppSystem.batch = new SpriteBatch ();
		setScreen (new StartGame (this, new ObjectManager ()));
	}
}
