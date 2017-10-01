package com.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.AppSystem;
import com.game.object.Messages.Message;
import com.game.object.Messages.MessageType;


public class Player implements GameObject{
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
			ObjectManager.sendMessage (new Message (MessageType.deleting, this));
		}
	}
	
	
	public Player (){
		Texture texture = new Texture("core\\assets\\player.png");
		sprite = new Sprite (texture);
		sprite.setBounds (100, 100, texture.getWidth (), texture.getHeight ());
	}
	
	@Override
	public void update (){
		inputControll ();
		
		AppSystem.batch.begin ();
		sprite.draw (AppSystem.batch);
		AppSystem.batch.end ();
	}
	
	@Override
	public void sendMessage (Message message){
	
	}
}
