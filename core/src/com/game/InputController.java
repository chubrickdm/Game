package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;


public class InputController implements InputProcessor{
	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button){
		System.out.println (screenX + " " + Math.abs (screenY - Gdx.graphics.getHeight ()));
		return false;
	}
	
	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button){
		return false;
	}
	
	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer){
		return false;
	}
	
	@Override
	public boolean mouseMoved (int screenX, int screenY){
		return false;
	}
	
	@Override
	public boolean scrolled (int amount){
		return false;
	}
	
	@Override
	public boolean keyTyped (char character){
		return false;
	}
	
	@Override
	public boolean keyDown (int keycode){
		return true;
	}
	
	@Override
	public boolean keyUp (int keycode){
		return true;
	}
}
