package com.game.screens;

import com.badlogic.gdx.Screen;
import com.game.addition.ParseXML;
import com.game.mesh.objects.special.ObjectManager;


public class PlayScreen implements Screen{
	@Override
	public void show (){
		ObjectManager.getInstance ();
		ParseXML.parseLVL (1);
	}
	
	@Override
	public void render (float delta){
		ObjectManager.getInstance ().update ();
		ObjectManager.getInstance ().draw ();
	}
	
	@Override
	public void resize (int width, int height){
		
	}
	
	@Override
	public void pause (){
		
	}
	
	@Override
	public void resume (){
		
	}
	
	@Override
	public void hide (){
		
	}
	
	@Override
	public void dispose (){
	
	}
}
