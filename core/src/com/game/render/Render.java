package com.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

public class Render{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ArrayList <DataRender> renderList;
	
	
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
	
	public void RenderScene (){
		Gdx.gl.glClearColor (0, 0, 0, 1);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		
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
