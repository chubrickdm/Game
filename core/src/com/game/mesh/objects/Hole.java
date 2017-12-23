package com.game.mesh.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pools;

import com.game.mesh.objects.singletons.special.ObjectManager;
import com.game.messages.DestroyObjectMessage;
import com.game.render.*;

import com.introfog.primitiveIsometricEngine.*;

public class Hole extends GameObject{
	private static final float BODY_HOLE_W = UNIT * 2 + 2;
	private static final float BODY_HOLE_H = UNIT * ANGLE * 2 + 2;
	private static final float HOLE_W = UNIT * 2;
	private static final float HOLE_H = UNIT * ANGLE * 2;
	
	private float bodyShiftX;
	private float bodyShiftY;
	private TriggeredZone triggeredZone;
	private Sprite sprite;
	
	public Hole (){
		objectType = ObjectType.hole;
		
		bodyShiftX = -(BODY_HOLE_W - HOLE_W) / 2;
		bodyShiftY = -(BODY_HOLE_H - HOLE_H) / 2;
		triggeredZone = new TriggeredZone (0, 0, BODY_HOLE_W, BODY_HOLE_H, ZoneType.contains, Color.SKY);
		
		Texture texture = new Texture ("core/assets/images/other/hole.png");
		sprite = new Sprite (texture);
		sprite.setBounds (0, 0, HOLE_W, HOLE_H);
		
		dataRender = new DataRender (sprite, LayerType.below);
	}
	
	public void setSpritePosition (float x, float y){
		triggeredZone.setPosition (x + bodyShiftX, y + bodyShiftY);
		triggeredZone.setGhost (false);
		sprite.setPosition (x, y);
	}
	
	@Override
	public void update (){
		if (triggeredZone.getInZone ().size () > 0){
			for (BodyPIE tmpB : triggeredZone.getInZone ()){
				ObjectManager.getInstance ().addMessage (new DestroyObjectMessage (this, tmpB));
			}
		}
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	@Override
	public void clear (){
		triggeredZone.clear ();
		triggeredZone.setGhost (true);
		Pools.free (this);
	}
}