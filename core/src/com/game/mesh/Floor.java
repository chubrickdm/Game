package com.game.mesh;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import com.game.GameSystem;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.singletons.special.LevelManager;

import javafx.util.Pair;

import java.util.ArrayList;

public class Floor{
	private ArrayList <Pair <Pair <Integer, Integer>, Integer>> coordinates;
	private ArrayList <Sprite> floor;
	
	
	public Floor (){
		Texture texture = new Texture ("core/assets/images/other/floor.png");
		int numRegions = (int) (texture.getWidth () / (GameObject.UNIT / GameObject.ASPECT_RATIO));
		int w = texture.getWidth () / numRegions;
		int h = texture.getHeight ();
		TextureRegion[] regions = new TextureRegion[numRegions];
		
		for (int i = 0; i < numRegions; i++){
			regions[i] = new TextureRegion (texture, i * w, 0, w, h);
		}
		
		floor = new ArrayList <> (numRegions);
		for (int i = 0; i < numRegions; i++){
			Sprite sprite = new Sprite (regions[i]);
			sprite.setSize (GameObject.UNIT, GameObject.UNIT * GameObject.ANGLE);
			floor.add (sprite);
		}
		
		coordinates = new ArrayList <> ();
		int sprite;
		int numFloorW = (int) ((GameSystem.SCREEN_W - 2 * GameSystem.INDENT_BETWEEN_SCREEN_LEVEL) / GameObject.UNIT);
		for (int i = 0; i < numFloorW; i++){
			for (int j = 0; j < 100; j++){
				sprite = MathUtils.random (0, numRegions - 1);
				coordinates.add (new Pair <Pair <Integer, Integer>, Integer> (new Pair <Integer, Integer>(i, j), sprite));
			}
		}
	}
	
	public void draw (SpriteBatch batch){
		int x;
		int y;
		for (Pair <Pair <Integer, Integer>, Integer> tmpP : coordinates){
			x = (int) (tmpP.getKey ().getKey () * GameObject.UNIT + GameSystem.INDENT_BETWEEN_SCREEN_LEVEL);
			y = (int) (tmpP.getKey ().getValue () * GameObject.UNIT * GameObject.ANGLE);
			floor.get (tmpP.getValue ()).setPosition (x, y);
			floor.get (tmpP.getValue ()).draw (batch);
		}
	}
}
