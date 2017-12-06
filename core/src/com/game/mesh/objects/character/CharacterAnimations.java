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
	
	private ObjectAnimation[] stand;
	private ObjectAnimation[] walk;
	private ObjectAnimation[] fall;
	private ObjectAnimation[] choke;
	
	
	private void updateStandAnimation (){
		currSprite = stand[character.currentDirection.ordinal ()].getFirstFrame ();
	}
	
	private void updateMoveAnimation (){
		currSprite = walk[character.currentDirection.ordinal ()].getCurrSprite ();
		
	}
	
	private void updateFallAnimation (){
		//игрок проиграл если он упал (т.е. закончилась прокрутка анимации падения)
		if (fall[character.currentDirection.ordinal ()].isAnimationFinished ()){
			ObjectManager.getInstance ().addMessage (new PlayerLostMessage ());
		}
		
		currSprite = fall[character.currentDirection.ordinal ()].getCurrSprite ();
	}
	
	private void updateChokeAnimation (){
		if (choke[character.currentDirection.ordinal ()].isAnimationFinished ()){
			ObjectManager.getInstance ().addMessage (new PlayerLostMessage ());
		}
		
		currSprite = choke[character.currentDirection.ordinal ()].getCurrSprite ();
	}
	
	
	public CharacterAnimations (Character character){
		this.character = character;
		
		stand = new ObjectAnimation[Direction.values ().length];
		walk = new ObjectAnimation[Direction.values ().length];
		fall = new ObjectAnimation[Direction.values ().length];
		choke = new ObjectAnimation[Direction.values ().length];
		
		for (int i = 0; i < Direction.values ().length; i++){
			stand[i] = new ObjectAnimation (path + "walking_" + Direction.values ()[i] + ".png", CHARACTER_W, CHARACTER_H,
					FRAME_ROWS, FRAME_COLS, 0.15f);
			walk[i] = new ObjectAnimation (path + "walking_" + Direction.values ()[i] + ".png", CHARACTER_W, CHARACTER_H,
					FRAME_ROWS, FRAME_COLS, 0.15f);
			fall[i] = new ObjectAnimation (path + "fall_" + Direction.values ()[i] + ".png", CHARACTER_W, CHARACTER_H,
					FRAME_ROWS, FRAME_COLS, 0.15f);
			choke[i] = new ObjectAnimation (path + "choke_" + Direction.values ()[i] + ".png", CHARACTER_W, CHARACTER_H,
					FRAME_ROWS, FRAME_COLS, 0.3f);
		}
		
		dataRender = new DataRender (currSprite, LayerType.normal);
	}
	
	@Override
	public void update (){
		switch (character.state){
		case stand:
			updateStandAnimation ();
			break;
		case move:
			updateMoveAnimation ();
			break;
		case fall:
			updateFallAnimation ();
			break;
		case choke:
			updateChokeAnimation ();
			break;
		}
		currSprite.setPosition (character.getSpriteX (), character.getSpriteY ());
	}
	
	@Override
	public void draw (){
		dataRender.sprite = currSprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
}
