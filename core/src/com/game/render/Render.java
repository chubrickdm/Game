package com.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.game.mesh.objects.singletons.camera.Camera;

import java.util.ArrayList;

public class Render{
	private SpriteBatch batch;
	private ArrayList <DataRender> renderList;
	
	
	private void sortedScene (){
		renderList.sort ((tmp1, tmp2) -> {
			//яма всегда рисуется последней
			if (tmp1.layerType == LayerType.actionWheel || tmp2.layerType == LayerType.hole){
				return 1;
			}
			else if (tmp2.layerType == LayerType.actionWheel || tmp1.layerType == LayerType.hole){
				return -1;
			}
			
			return (int) (tmp2.sprite.getY () - tmp1.sprite.getY ());
		});
	}
	
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
		
		renderList.clear ();
	}
	
	public void addDataForRender (DataRender data){
		renderList.add (data);
	}
}
