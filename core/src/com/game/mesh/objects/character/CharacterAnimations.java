package com.game.mesh.objects.character;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.PlayerLostMessage;
import com.game.render.DataRender;
import com.game.render.LayerType;

public class CharacterAnimations extends CharacterAddition{
	public static final float CHARACTER_W = GameObject.UNIT;
	public static final float CHARACTER_H = GameObject.UNIT;
	
	private static final float CHARACTER_SPEED = 80 * GameObject.ASPECT_RATIO;
	private static final int FRAME_COLS = 7;
	private static final int FRAME_ROWS = 1;
	
	private float deltaX = 0;
	private float deltaY = 0;
	private ActionType action;
	private DataRender dataRender;
	private Sprite currSprite;
	private ObjectAnimation leftWalk;
	private ObjectAnimation rightWalk;
	private ObjectAnimation forwardWalk;
	private ObjectAnimation backWalk;
	private ObjectAnimation backFall;
	
	
	public CharacterAnimations (Character character){
		this.character = character;
		
		action = ActionType.forwardWalk;
		
		leftWalk = new ObjectAnimation ("core/assets/images/walking_left.png", CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		rightWalk = new ObjectAnimation ("core/assets/images/walking_right.png", CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		forwardWalk = new ObjectAnimation ("core/assets/images/walking_forward.png", CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		backWalk = new ObjectAnimation ("core/assets/images/walking_back.png", CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		backFall = new ObjectAnimation ("core/assets/images/fall_back.png", false, CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		
		dataRender = new DataRender (currSprite, LayerType.character);
	}
	
	private void updateMoveAnimation (){
		if ((deltaX != 0 || deltaY != 0) && isSelected){
			switch (action){
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
			switch (action){
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
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
	}
	
	private void updateFallAnimation (){
		if (backFall.isAnimationFinished ()){
			ObjectManager.getInstance ().addMessage (new PlayerLostMessage ());
		}
		if (action == ActionType.fall){
			currSprite = backFall.getCurrSprite ();
		}
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
	}
	
	@Override
	public void update (){ }
}
