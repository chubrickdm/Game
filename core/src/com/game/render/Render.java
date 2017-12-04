package com.game.render;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.game.GameSystem;
import com.game.MyGame;
import com.game.addition.parsers.ParseLevel;
import com.game.mesh.Floor;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.singletons.Camera;

import java.util.ArrayList;
import java.util.LinkedList;

public class Render{
	private boolean showLight = false;
	private Floor floor;
	private SpriteBatch batch;
	private LinkedList <DataRender> renderList;
	
	public RayHandler handler;
	
	
	private void sortedScene (){
		renderList.sort ((tmp1, tmp2) -> {
			if (tmp1.layerType == LayerType.over || tmp2.layerType == LayerType.below){
				return 1;
			}
			else if (tmp2.layerType == LayerType.over || tmp1.layerType == LayerType.below){
				return -1;
			}
			
			return (int) (tmp2.sprite.getY () - tmp1.sprite.getY ());
		});
	}
	
	private static class RenderHolder{
		private final static Render instance = new Render ();
	}
	
	private Render (){
		floor = new Floor ();
		batch = new SpriteBatch ();
		renderList = new LinkedList <> ();
		
		handler = new RayHandler (MyGame.getInstance ().world);
	}
	
	
	public static Render getInstance (){
		return RenderHolder.instance;
	}
	
	public void renderScene (){
		Gdx.gl.glClearColor (0, 0, 0, 1);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		
		sortedScene ();
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.L)){
			showLight = !showLight;
		}
		
		Camera.getInstance ().update ();
		batch.setProjectionMatrix (Camera.getInstance ().getProjectionMatrix ());
		
		batch.begin ();
		floor.draw (batch);
		for (DataRender data : renderList){
			data.sprite.draw (batch);
		}
		batch.end ();
		
		if (showLight){
			handler.setCombinedMatrix (Camera.getInstance ().getCamera ());
			handler.updateAndRender ();
		}
		
		renderList.clear ();
	}
	
	public void addDataForRender (DataRender data){
		renderList.add (data);
	}
}
