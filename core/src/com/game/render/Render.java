package com.game.render;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.game.GameSystem;
import com.game.MyGame;
import com.game.addition.parsers.ParseLevel;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.singletons.Camera;

import java.util.ArrayList;

public class Render{
	private SpriteBatch batch;
	private ArrayList <DataRender> renderList;
	////////////////////////
	private ShapeRenderer shapes;
	//////////////////////
	
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
		/////////////////////////////////////////////////
		shapes = new ShapeRenderer ();
		shapes.setColor (Color.GRAY);
		////////////////////////////////////////////////
		
		batch = new SpriteBatch ();
		renderList = new ArrayList <DataRender> ();
		
		handler = new RayHandler (MyGame.getInstance ().world);
	}
	
	
	public static Render getInstance (){
		return RenderHolder.instance;
	}
	
	public void renderScene (){
		Gdx.gl.glClearColor (0, 0, 0, 1);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		
		sortedScene ();
		
		Camera.getInstance ().update ();
		batch.setProjectionMatrix (Camera.getInstance ().getProjectionMatrix ());
		
		batch.begin ();
		for (DataRender data : renderList){
			data.sprite.draw (batch);
		}
		batch.end ();
		
		////////////////////////////////////////////////////////////////////////////
		shapes.setProjectionMatrix (Camera.getInstance ().getProjectionMatrix ());
		shapes.begin (ShapeRenderer.ShapeType.Line);
		for (int i = 0; i < 19; i++){
			shapes.line (i * GameObject.UNIT + GameSystem.INDENT_BETWEEN_SCREEN_LEVEL, 0, i * GameObject.UNIT + GameSystem.INDENT_BETWEEN_SCREEN_LEVEL, 2000);
		}
		for (int i = 0; i < 40; i++){
			shapes.line (GameSystem.INDENT_BETWEEN_SCREEN_LEVEL, i * GameObject.UNIT * GameObject.ANGLE, 19 * GameObject.UNIT + GameSystem.INDENT_BETWEEN_SCREEN_LEVEL, i * GameObject.UNIT * GameObject.ANGLE);
		}
		shapes.end ();
		///////////////////////////////////////////////////////////////////////////
		
		//handler.setCombinedMatrix (Camera.getInstance ().getCamera ());
		//handler.updateAndRender ();
		
		renderList.clear ();
	}
	
	public void addDataForRender (DataRender data){
		renderList.add (data);
	}
}
