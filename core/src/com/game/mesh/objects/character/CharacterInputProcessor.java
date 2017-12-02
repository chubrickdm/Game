package com.game.mesh.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.game.GameSystem;
import com.game.addition.algorithms.aStar.AlgorithmAStar;
import com.game.addition.algorithms.aStar.realisation.ConcreteNode;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.singletons.special.LevelManager;

import java.util.ArrayList;

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
			
			ConcreteNode start = new ConcreteNode (0, 0);
			start.x = (int) ((character.getBodyX () + character.getBodyW () / 2 - GameSystem.INDENT_BETWEEN_SCREEN_LEVEL) / GameObject.UNIT);
			start.y = (int) ((character.getBodyY () + character.getBodyH () / 2) / (GameObject.UNIT * GameObject.ANGLE));
			System.out.println ("Character: " + start.x + " " + start.y);
			
			ConcreteNode finish = new ConcreteNode (0, 0);
			finish.x = (int) ((screenX - GameSystem.INDENT_BETWEEN_SCREEN_LEVEL) / GameObject.UNIT);
			finish.y = (int) (screenY / (GameObject.UNIT * GameObject.ANGLE));
			System.out.println ("Mouse: " + finish.x + " " + finish.y);
			
			
			AlgorithmAStar <ConcreteNode> algorithm = new AlgorithmAStar <> ();
			ArrayList <ConcreteNode> path = algorithm.findWay (LevelManager.getInstance ().level, start, finish);
			
			if (path == null){
				System.out.println ("Don't exist path.");
			}
			else{
				System.out.println ("Start.");
				for (ConcreteNode tmpN : path){
					System.out.println (tmpN.x + " " + tmpN.y);
				}
				System.out.println ("Finish.");
			}
			
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
