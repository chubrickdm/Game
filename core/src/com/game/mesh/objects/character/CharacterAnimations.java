package com.game.mesh.objects.character;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.PlayerLostMessage;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class CharacterAnimations extends Character{
	private static final int FRAME_COLS = 7;
	private static final int FRAME_ROWS = 1;
	private static final String path = "core/assets/images/character/";
	
	private Character character;
	private Sprite currSprite;
	private ObjectAnimation leftWalk;
	private ObjectAnimation rightWalk;
	private ObjectAnimation forwardWalk;
	private ObjectAnimation backWalk;
	
	private ObjectAnimation leftFall;
	private ObjectAnimation rightFall;
	private ObjectAnimation forwardFall;
	private ObjectAnimation backFall;
	
	private ObjectAnimation leftChoke;
	private ObjectAnimation rightChoke;
	private ObjectAnimation forwardChoke;
	private ObjectAnimation backChoke;
	
	
	private void updateMoveAnimation (){
		if (character.isMove){
			switch (character.action){
			case forwardWalk:
				currSprite = forwardWalk.getCurrSprite ();
				break;
			case rightWalk:
				currSprite = rightWalk.getCurrSprite ();
				break;
			case backWalk:
				currSprite = backWalk.getCurrSprite ();
				break;
			case leftWalk:
				currSprite = leftWalk.getCurrSprite ();
				break;
			}
		}
		else{
			switch (character.action){
			case forwardWalk:
				currSprite = forwardWalk.getFirstFrame ();
				break;
			case rightWalk:
				currSprite = rightWalk.getFirstFrame ();
				break;
			case backWalk:
				currSprite = backWalk.getFirstFrame ();
				break;
			case leftWalk:
				currSprite = leftWalk.getFirstFrame ();
				break;
			}
		}
		currSprite.setPosition (character.getSpriteX (), character.getSpriteY ());
	}
	
	private void updateFallAnimation (){
		//игрок проиграл если он упал (т.е. закончилась прокрутка анимации падения)
		if (leftFall.isAnimationFinished ()){
			ObjectManager.getInstance ().addMessage (new PlayerLostMessage ());
		}
		else if (rightFall.isAnimationFinished ()){
			ObjectManager.getInstance ().addMessage (new PlayerLostMessage ());
		}
		else if (forwardFall.isAnimationFinished ()){
			ObjectManager.getInstance ().addMessage (new PlayerLostMessage ());
		}
		else if (backFall.isAnimationFinished ()){
			ObjectManager.getInstance ().addMessage (new PlayerLostMessage ());
		}
		
		switch (character.action){
		case forwardFall:
			currSprite = forwardFall.getCurrSprite ();
			break;
		case rightFall:
			currSprite = rightFall.getCurrSprite ();
			break;
		case backFall:
			currSprite = backFall.getCurrSprite ();
			break;
		case leftFall:
			currSprite = leftFall.getCurrSprite ();
			break;
		}
		
		currSprite.setPosition (character.getSpriteX (), character.getSpriteY ());
	}
	
	private void selectFallAnimation (){
		if (character.action == ActionType.leftWalk){
			character.action = ActionType.leftFall;
		}
		else if (character.action == ActionType.rightWalk){
			character.action = ActionType.rightFall;
		}
		else if (character.action == ActionType.forwardWalk){
			character.action = ActionType.forwardFall;
		}
		else if (character.action == ActionType.backWalk){
			character.action = ActionType.backFall;
		}
	}
	
	private void updateChokeAnimation (){
		if (leftChoke.isAnimationFinished ()){
			ObjectManager.getInstance ().addMessage (new PlayerLostMessage ());
		}
		else if (rightChoke.isAnimationFinished ()){
			ObjectManager.getInstance ().addMessage (new PlayerLostMessage ());
		}
		else if (forwardChoke.isAnimationFinished ()){
			ObjectManager.getInstance ().addMessage (new PlayerLostMessage ());
		}
		else if (backChoke.isAnimationFinished ()){
			ObjectManager.getInstance ().addMessage (new PlayerLostMessage ());
		}
		
		switch (character.action){
		case forwardChoke:
			currSprite = forwardChoke.getCurrSprite ();
			break;
		case rightChoke:
			currSprite = rightChoke.getCurrSprite ();
			break;
		case backChoke:
			currSprite = backChoke.getCurrSprite ();
			break;
		case leftChoke:
			currSprite = leftChoke.getCurrSprite ();
			break;
		}
		
		currSprite.setPosition (character.getSpriteX (), character.getSpriteY ());
	}
	
	private void selectChokeAnimation (){
		if (character.action == ActionType.leftWalk){
			character.action = ActionType.leftChoke;
		}
		else if (character.action == ActionType.rightWalk){
			character.action = ActionType.rightChoke;
		}
		else if (character.action == ActionType.forwardWalk){
			character.action = ActionType.forwardChoke;
		}
		else if (character.action == ActionType.backWalk){
			character.action = ActionType.backChoke;
		}
	}
	
	
	public CharacterAnimations (Character character){
		this.character = character;
		character.action = ActionType.forwardWalk;
		
		leftWalk = new ObjectAnimation (path + "walking_left.png", CHARACTER_W, CHARACTER_H,
				FRAME_ROWS, FRAME_COLS, 0.15f);
		rightWalk = new ObjectAnimation (path + "walking_right.png", CHARACTER_W, CHARACTER_H,
				FRAME_ROWS, FRAME_COLS, 0.15f);
		forwardWalk = new ObjectAnimation (path + "walking_forward.png", CHARACTER_W, CHARACTER_H,
				FRAME_ROWS, FRAME_COLS, 0.15f);
		backWalk = new ObjectAnimation (path + "walking_back.png", CHARACTER_W, CHARACTER_H,
				FRAME_ROWS, FRAME_COLS, 0.15f);
		
		
		leftFall = new ObjectAnimation (path + "fall_left.png", false, CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		rightFall = new ObjectAnimation (path + "fall_right.png", false, CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		forwardFall = new ObjectAnimation (path + "fall_forward.png", false, CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		backFall = new ObjectAnimation (path + "fall_back.png", false, CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		
		leftChoke = new ObjectAnimation (path + "choke_left.png", false, CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, 5, 0.3f);
		rightChoke = new ObjectAnimation (path + "choke_right.png", false, CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, 5, 0.3f);
		forwardChoke = new ObjectAnimation (path + "choke_forward.png", false, CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, 5, 0.3f);
		backChoke = new ObjectAnimation (path + "choke_back.png", false, CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, 5, 0.3f);
		
		dataRender = new DataRender (currSprite, LayerType.normal);
	}
	
	@Override
	public void update (){
		if (character.isFall){
			selectFallAnimation ();
			updateFallAnimation ();
		}
		else if (character.isChoke){
			selectChokeAnimation ();
			updateChokeAnimation ();
		}
		else{
			updateMoveAnimation ();
		}
	}
	
	@Override
	public void draw (){
		dataRender.sprite = currSprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
}
