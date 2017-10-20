package com.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Render{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ArrayList <DataRender> renderList;
	
	private void sortedScene (){
		renderList.sort ((tmp1, tmp2) -> tmp1.layerType.getValue () - tmp2.layerType.getValue ());
	}
	
	private static class RenderHolder{
		private final static Render instance = new Render ();
	}
	
	private Render (){
		camera = new OrthographicCamera (Gdx.graphics.getWidth (), Gdx.graphics.getHeight ());
		camera.setToOrtho (false);
		batch = new SpriteBatch ();
		renderList = new ArrayList <DataRender> ();
	}
	
	
	public static Render getInstance (){
		return RenderHolder.instance;
	}
	
	public void renderScene (){
		Gdx.gl.glClearColor (0, 0, 0, 1);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		
		sortedScene ();
		
		camera.update ();
		batch.setProjectionMatrix (camera.combined);
		
		batch.begin ();
		for (DataRender data : renderList){
			data.sprite.draw (batch);
		}
		batch.end ();
		
		renderList.clear ();
	}
	
	public void addDataForRender (DataRender data){
		renderList.add (data);
	}
}
