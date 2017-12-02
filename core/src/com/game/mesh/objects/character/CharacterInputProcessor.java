package com.game.mesh.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.game.GameSystem;
import com.game.addition.parsers.ParseLevel;
import com.game.mesh.objects.GameObject;

public class CharacterInputProcessor extends Character implements InputProcessor{
	private static InputMultiplexer multiplexer;
	private static boolean creteMultiplexer = false;
	private Character character;
	
	
	public CharacterInputProcessor (Character character){
		if (!creteMultiplexer){
			multiplexer = new InputMultiplexer ();
			creteMultiplexer = true;
		}
		multiplexer.addProcessor (this);
		Gdx.input.setInputProcessor (multiplexer);
		this.character = character;
	}
	
	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button){
		if (character.isSelected){
			screenY = (int) (character.getSpriteY () + character.getSpriteH () / 2 - GameSystem.SCREEN_H / 2) + (int) GameSystem.SCREEN_H - screenY;
			System.out.println ((int) ((screenX - ParseLevel.indent) / GameObject.UNIT) + " " + (int) (screenY / (GameObject.UNIT * GameObject.ANGLE)));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button){ return false; }
	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer){ return false; }
	@Override
	public boolean mouseMoved (int screenX, int screenY){ return false; }
	@Override
	public boolean scrolled (int amount){ return false; }
	@Override
	public boolean keyDown (int keycode){ return false; }
	@Override
	public boolean keyUp (int keycode){ return false; }
	@Override
	public boolean keyTyped (char character){ return false; }
}
