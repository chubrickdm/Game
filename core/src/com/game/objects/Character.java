package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.AppSystem;
import com.game.objects.messages.CharacterChangeMessage;
import com.game.objects.messages.DeleteMessage;
import com.game.objects.messages.GameMessage;
import com.game.objects.messages.MessageType;


public class Character implements GameObject{
	private boolean iSelected = false;
	private double deltaX;
	private double deltaY;
	private double speed = 100;
	private Sprite sprite;
	
	
	private void inputControll (){
		if (Gdx.input.isKeyPressed (Input.Keys.D)){
			deltaX = speed * Gdx.graphics.getDeltaTime ();
			sprite.setPosition (sprite.getX () + (float) deltaX, sprite.getY ());
		}
		if (Gdx.input.isKeyPressed (Input.Keys.A)){
			deltaX = speed * Gdx.graphics.getDeltaTime ();
			sprite.setPosition (sprite.getX () - (float) deltaX, sprite.getY ());
		}
		if (Gdx.input.isKeyPressed (Input.Keys.W)){
			deltaY = speed * Gdx.graphics.getDeltaTime ();
			sprite.setPosition (sprite.getX (), sprite.getY () + (float) deltaY);
		}
		if (Gdx.input.isKeyPressed (Input.Keys.S)){
			deltaY = speed * Gdx.graphics.getDeltaTime ();
			sprite.setPosition (sprite.getX (), sprite.getY () - (float) deltaY);
		}
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			ObjectManager.getInstance ().messages.add (new CharacterChangeMessage (this));
			//ObjectManager.getInstance ().sendMessage (new DeleteMessage (this));
		}
	}
	
	
	public Character (boolean iSelected){
		this.iSelected = iSelected;
		Texture texture = new Texture("core\\assets\\player.png");
		sprite = new Sprite (texture);
		sprite.setBounds (100, 100, texture.getWidth (), texture.getHeight ());
	}
	
	@Override
	public void update (){
		if (iSelected){
			inputControll ();
		}
		
		AppSystem.getInstance ().batch.begin ();
		sprite.draw (AppSystem.getInstance ().batch);
		AppSystem.getInstance ().batch.end ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.characterChange){
			if (iSelected){
				iSelected = false;
			}
			else{
				iSelected = true;
			}
		}
	}
}
