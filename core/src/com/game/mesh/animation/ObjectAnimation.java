package com.game.mesh.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ObjectAnimation{
	private boolean looping = true; //зацикливащаяся анимация или нет
	private float time;
	private float frameW;
	private float frameH;
	private Animation <TextureRegion> animation;
	
	
	public ObjectAnimation (String fileName, float frameW, float frameH, int frameRows, int frameCols, float frameDuration){
		//frameDuration - время между кадрами
		this.frameW = frameW;
		this.frameH = frameH;
		Texture texture = new Texture (fileName);
		int regionW = texture.getWidth () / frameCols;
		int regionH = texture.getHeight () / frameRows;
		TextureRegion[][] tmp = TextureRegion.split (texture, regionW, regionH);
		TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
		for (int i = 0, index = 0; i < frameRows; i++){
			for (int j = 0; j < frameCols; j++){
				frames[index++] = tmp[i][j];
			}
		}
		animation = new Animation <TextureRegion> (frameDuration, frames);
	}
	
	public ObjectAnimation (String fileName, boolean looping, float frameW, float frameH, int frameRows, int frameCols, float frameDuration){
		this (fileName, frameW, frameH, frameRows, frameCols, frameDuration); //конструктор для задания повторения анимации
		this.looping = looping;
	}
	
	public Sprite getCurrSprite (){
		animation.setPlayMode (Animation.PlayMode.NORMAL);
		time += Gdx.graphics.getDeltaTime ();
		Sprite currSprite = new Sprite (animation.getKeyFrame (time, looping));
		currSprite.setBounds (0, 0, frameW, frameH);
		return currSprite;
	}
	
	public boolean isAnimationFinished (){
		return !looping && animation.isAnimationFinished (time);
	}
	
	public Sprite getFirstFrame (){
		time = -Gdx.graphics.getDeltaTime ();
		return getCurrSprite ();
	}
	
	public Sprite getReversedCurrSprite (){
		animation.setPlayMode (Animation.PlayMode.REVERSED);
		time += Gdx.graphics.getDeltaTime ();
		Sprite currSprite = new Sprite (animation.getKeyFrame (time, looping));
		currSprite.setBounds (0, 0, frameW, frameH);
		return currSprite;
	}
	
	public void resetTime (){
		time = 0;
	}
}