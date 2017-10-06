package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.math.BodyRectangle;
import com.game.render.Render;
import com.sun.org.apache.regexp.internal.RE;


public class BodyObject{
	private float time = 0;
	private Sprite sprite;
	private BodyRectangle bodyRect;
	private Animation <TextureRegion> animation;
	
	
	public BodyObject (String fileName, float x, float y, float w, float h){
		Texture texture = new Texture (fileName);
		sprite = new Sprite (texture);
		sprite.setBounds (x, y, w, h);
		bodyRect = new BodyRectangle (x, y, w, h);
	}
	
	public BodyObject (String fileName, float x, float y, float w, float h, int frameRows, int frameCols){
		Texture texture = new Texture (fileName);
		bodyRect = new BodyRectangle (x, y, w, h);
		int regionW = texture.getWidth () / frameCols;
		int regionH = texture.getHeight () / frameRows;
		TextureRegion[][] tmp = TextureRegion.split (texture, regionW, regionH);
		TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
		int index = 0;
		for (int i = 0; i < frameRows; i++){
			for (int j = 0; j < frameCols; j++){
				frames[index++] = tmp[i][j];
			}
		}
		animation = new Animation <TextureRegion> (0.18f, frames);
		sprite = new Sprite (frames[0]);
	}
	
	public void setPosition (float x, float y){
		sprite.setPosition (x, y);
		bodyRect.setPosition (x, y);
	}
	
	public boolean intersects (BodyObject body){
		return bodyRect.intersects (body.bodyRect);
	}
	
	public float getX (){
		return sprite.getX ();
	}
	
	public float getY (){
		return sprite.getY ();
	}
	
	public void move (float deltaX, float deltaY){
		sprite.setPosition (sprite.getX () + deltaX, sprite.getY () + deltaY);
		bodyRect.move (deltaX, deltaY);
	}
	
	public void updateCurrAnimationFrame (){
		time += Gdx.graphics.getDeltaTime ();
		sprite.set (new Sprite (animation.getKeyFrame (time, true)));
		//sprite.setTexture (animation.getKeyFrame (time, true).getTexture ());
		sprite.setBounds (bodyRect.getX (), bodyRect.getY (), bodyRect.getW (), bodyRect.getH ());
	}
	
	public Sprite getSprite (){
		return sprite;
	}
}
