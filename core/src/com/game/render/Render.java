package com.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

public class Render{
	private SpriteBatch batch;
	private ArrayList <DataRender> renderList;
	
	
	private static class RenderHolder{
		private final static Render instance = new Render ();
	}
	
	private Render (){
		batch = new SpriteBatch ();
		renderList = new ArrayList <DataRender> ();
	}
	
	
	public static Render getInstance (){
		return RenderHolder.instance;
	}
	
	public void RenderScene (){
		Gdx.gl.glClearColor (0, 0, 0, 1);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		
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
