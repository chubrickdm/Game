package com.game.mesh.objects.character;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.game.mesh.animation.ObjectAnimation;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.PlayerLostMessage;
import com.game.render.DataRender;
import com.game.render.LayerType;
import com.game.render.Render;

public class CharacterAnimations extends Character{
	private Character character;
	private Sprite currSprite;
	
	private ObjectAnimation[] stand;
	private ObjectAnimation[] walk;
	private ObjectAnimation[] fall;
	private ObjectAnimation[] choke;
	private ObjectAnimation[] push;
	
	
	public CharacterAnimations (Character character){
		this.character = character;
		String path;
		if (character.getName () == CharacterName.first){
			path = "core/assets/images/character/first/";
		}
		else{
			path = "core/assets/images/character/second/";
		}
		
		stand = new ObjectAnimation[Direction.values ().length];
		walk = new ObjectAnimation[Direction.values ().length];
		fall = new ObjectAnimation[Direction.values ().length];
		choke = new ObjectAnimation[Direction.values ().length];
		push = new ObjectAnimation[Direction.values ().length];
		
		float region = GameObject.UNIT / GameObject.ASPECT_RATIO;
		for (int i = 0; i < Direction.values ().length; i++){
			stand[i] = new ObjectAnimation (path + "stand/stand_" + Direction.values ()[i] + ".png", region,
					region, CHARACTER_W, CHARACTER_H, 0.15f);
			walk[i] = new ObjectAnimation (path + "walk/walk_" + Direction.values ()[i] + ".png", region,
					region, CHARACTER_W, CHARACTER_H, 0.15f);
			fall[i] = new ObjectAnimation (path + "fall/fall_" + Direction.values ()[i] + ".png",
					false, region, region, CHARACTER_W, CHARACTER_H, 0.15f);
			choke[i] = new ObjectAnimation (path + "choke/choke_" + Direction.values ()[i] + ".png",
					false, region, region, CHARACTER_W, CHARACTER_H, 0.3f);
			push[i] = new ObjectAnimation (path + "push/push_" + Direction.values ()[i] + ".png", region,
					region, CHARACTER_W, CHARACTER_H, 0.15f);
		}
		
		dataRender = new DataRender (currSprite, LayerType.normal);
	}
	
	@Override
	public void update (){
		switch (character.state){
		case stand:
			currSprite = stand[character.currentDirection.ordinal ()].getFirstFrame ();
			break;
		case move:
			currSprite = walk[character.currentDirection.ordinal ()].getCurrSprite ();
			break;
		case fall:
			if (fall[character.currentDirection.ordinal ()].isAnimationFinished ()){
				ObjectManager.getInstance ().addMessage (new PlayerLostMessage ());
			}
			currSprite = fall[character.currentDirection.ordinal ()].getCurrSprite ();
			break;
		case choke:
			if (choke[character.currentDirection.ordinal ()].isAnimationFinished ()){
				ObjectManager.getInstance ().addMessage (new PlayerLostMessage ());
			}
			currSprite = choke[character.currentDirection.ordinal ()].getCurrSprite ();
			break;
		case push:
			currSprite = push[character.currentDirection.ordinal ()].getCurrSprite ();
			break;
		}
		currSprite.setPosition (character.getSpriteX (), character.getSpriteY ());
	}
	
	@Override
	public void draw (){
		dataRender.sprite = currSprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	@Override
	public void clear (){
		for (int i = 0; i < Direction.values ().length; i++){
			stand[i].resetTime ();
			walk[i].resetTime ();
			fall[i].resetTime ();
			choke[i].resetTime ();
			push[i].resetTime ();
		}
	}
}