package com.game.mesh.objects.character.second;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.body.Body;
import com.game.mesh.body.NoSpriteObject;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.character.ActionType;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.PlayerLostMessage;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class CharacterAnimations{
	public static final float CHARACTER_W = GameObject.UNIT;
	public static final float CHARACTER_H = GameObject.UNIT;
	private static final float CHARACTER_SPEED = 80 * GameObject.ASPECT_RATIO;
	private static final int FRAME_COLS = 7;
	private static final int FRAME_ROWS = 1;
	
	private boolean isMove = false;
	private boolean isFall = false;
	private ActionType action;
	private DataRender dataRender;
	private Sprite currSprite;
	private NoSpriteObject body;
	private ObjectAnimation leftWalk;
	private ObjectAnimation rightWalk;
	private ObjectAnimation forwardWalk;
	private ObjectAnimation backWalk;
	private ObjectAnimation leftFall;
	private ObjectAnimation rightFall;
	private ObjectAnimation forwardFall;
	private ObjectAnimation backFall;
	
	
	private void updateMoveAnimation (){
		if (isMove){
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
		
		switch (action){
		case forwardWalk:
			currSprite = forwardFall.getCurrSprite ();
			break;
		case rightWalk:
			currSprite = rightFall.getCurrSprite ();
			break;
		case backWalk:
			currSprite = backFall.getCurrSprite ();
			break;
		case leftWalk:
			currSprite = leftFall.getCurrSprite ();
			break;
		}
		
		currSprite.setPosition (body.getSpriteX (), body.getSpriteY ());
	}
	
	private void selectFallAnimation (){
		if (action == ActionType.leftWalk){
			action = ActionType.leftFall;
		}
		else if (action == ActionType.rightWalk){
			action = ActionType.rightFall;
		}
		else if (action == ActionType.forwardWalk){
			action = ActionType.forwardFall;
		}
		else if (action == ActionType.backWalk){
			action = ActionType.backFall;
		}
	}
	
	
	public CharacterAnimations (Body body){
		this.body = (NoSpriteObject) body;
		
		action = ActionType.backWalk;
		
		leftWalk = new ObjectAnimation ("core/assets/images/walking_left.png", CHARACTER_W, CHARACTER_H,
				FRAME_ROWS, FRAME_COLS, 0.15f);
		rightWalk = new ObjectAnimation ("core/assets/images/walking_right.png", CHARACTER_W, CHARACTER_H,
				FRAME_ROWS, FRAME_COLS, 0.15f);
		forwardWalk = new ObjectAnimation ("core/assets/images/walking_forward.png", CHARACTER_W, CHARACTER_H,
				FRAME_ROWS, FRAME_COLS, 0.15f);
		backWalk = new ObjectAnimation ("core/assets/images/walking_back.png", CHARACTER_W, CHARACTER_H,
				FRAME_ROWS, FRAME_COLS, 0.15f);
		
		
		leftFall = new ObjectAnimation ("core/assets/images/fall_left.png", false, CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		rightFall = new ObjectAnimation ("core/assets/images/fall_right.png", false, CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		forwardFall = new ObjectAnimation ("core/assets/images/fall_forward.png", false, CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		backFall = new ObjectAnimation ("core/assets/images/fall_back.png", false, CHARACTER_W,
				CHARACTER_H, FRAME_ROWS, FRAME_COLS, 0.15f);
		
		dataRender = new DataRender (currSprite, LayerType.character);
	}
	
	public void update (ActionType action, boolean isFall, boolean isMove){
		this.isFall = isFall;
		this.isMove = isMove;
		if (isFall){
			selectFallAnimation ();
			updateFallAnimation ();
		}
		this.action = action;
		updateMoveAnimation ();
	}
	
	public void draw (){
		dataRender.sprite = currSprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	public ActionType getAction (){
		return action;
	}
}
