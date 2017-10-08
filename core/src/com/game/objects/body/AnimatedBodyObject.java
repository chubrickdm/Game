package com.game.objects.body;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class AnimatedBodyObject extends BodyObject{
	private float spriteW;
	private float spriteH;
	private Animation<TextureRegion> animation;
	
	
	public AnimatedBodyObject (String fileName, float x, float y, float w, float h, float bodyW, float bodyH,
							   int frameRows, int frameCols, float frameDuration){
		super (fileName, x, y, w, h, bodyW, bodyH);
		spriteW = w;
		spriteH = h;
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
	
	public void updateCurrAnimationFrame (float time){
		time += Gdx.graphics.getDeltaTime ();
		sprite.set (new Sprite (animation.getKeyFrame (time, true)));
		sprite.setBounds (bodyRect.getX () - bodyShiftX, bodyRect.getY () - bodyShiftY, spriteW, spriteH);
	}
}
