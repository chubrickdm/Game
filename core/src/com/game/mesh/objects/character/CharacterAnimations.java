package com.game.mesh.objects.character;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.objects.GameObject;
import com.game.render.DataRender;
import com.game.render.LayerType;

public class CharacterAnimations{
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
	private CharacterBody characterBody;
	
	
	public CharacterAnimations (CharacterBody characterBody){
		this.characterBody = characterBody;
		
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
}
