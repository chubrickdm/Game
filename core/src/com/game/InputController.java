package com.game;

import com.badlogic.gdx.InputProcessor;
import com.game.messages.KeyDownMessage;
import com.game.messages.KeyUpMessage;
import com.game.objects.ObjectManager;

public class InputController implements InputProcessor{
	private static class InputControllerHolder{
		private final static InputController instance = new InputController ();
	}
	
	private InputController (){
	
	}
	
	
	public static InputController getInstance (){
		return InputControllerHolder.instance;
	}
	
	@Override
	public boolean keyDown (int keycode){
		ObjectManager.getInstance ().addMessage (new KeyDownMessage (null, keycode));
		return true;
	}
	
	@Override
	public boolean keyUp (int keycode){
		ObjectManager.getInstance ().addMessage (new KeyUpMessage (null, keycode));
		return true;
	}
	
	@Override
	public boolean keyTyped (char character){
		return false;
	}
	
	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button){
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
}
