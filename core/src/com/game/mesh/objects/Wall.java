package com.game.mesh.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pools;

import com.game.render.*;

import com.introfog.primitiveIsometricEngine.Body;
import com.introfog.primitiveIsometricEngine.World;

public class Wall extends GameObject{
	private static final float BODY_WALL_W = UNIT;
	private static final float BODY_WALL_H = UNIT * ANGLE;
	private static final float WALL_W = UNIT;
	private static final float WALL_H = UNIT * 2 + UNIT * ANGLE;
	
	private Body PIEBody;
	private Sprite sprite;
	
	
	public Wall (){
		objectType = ObjectType.wall;
		
		Texture texture = new Texture ("core/assets/images/other/wall_2.png");
		sprite = new Sprite (texture);
		sprite.setBounds (0, 0, WALL_W, WALL_H);
		dataRender = new DataRender (sprite, LayerType.normal);
		
		PIEBody = new Body (0, 0, BODY_WALL_W, BODY_WALL_H);
		World.getInstance ().addObject (PIEBody);
	}
	
	public void setPosition (float x, float y){
		sprite.setPosition (x, y);
		PIEBody.setPosition (x, y);
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	@Override
	public void clear (){
		Pools.free (this);
	}
}