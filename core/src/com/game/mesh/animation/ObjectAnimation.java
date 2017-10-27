package com.game.mesh.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ObjectAnimation{
	private float frameW;
	private float frameH;
	private Animation <TextureRegion> animation;
	
	
	public ObjectAnimation (String fileName, float frameW, float frameH, int frameRows, int frameCols, float frameDuration){
		this.frameW = frameW;
		this.frameH = frameH;
		Texture texture = new Texture (fileName);
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
		animation = new Animation <TextureRegion> (frameDuration, frames);
	}
	
	public Sprite getCurrSprite (float time){
		Sprite currSprite = new Sprite (animation.getKeyFrame (time, true));
		currSprite.setBounds (0, 0, frameW, frameH);
		return currSprite;
	}
}
