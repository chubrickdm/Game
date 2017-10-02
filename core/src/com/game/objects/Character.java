package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.messages.*;
import com.game.render.DataForRender;
import com.game.render.LayerType;
import com.game.render.Render;


public class Character implements GameObject{
	private boolean iSelected = false;
	private double deltaX;
	private double deltaY;
	private double speed = 100;
	private Sprite sprite;
	private DataForRender dataForRender;
	
	
	private void inputControll (){
		deltaY = 0; deltaX = 0;
		if (Gdx.input.isKeyPressed (Input.Keys.D)){
			deltaX = speed * Gdx.graphics.getDeltaTime ();
			sprite.setPosition (sprite.getX () + (float) deltaX, sprite.getY ());
		}
		else if (Gdx.input.isKeyPressed (Input.Keys.A)){
			deltaX = -speed * Gdx.graphics.getDeltaTime ();
			sprite.setPosition (sprite.getX () + (float) deltaX, sprite.getY ());
		}
		if (Gdx.input.isKeyPressed (Input.Keys.W)){
			deltaY = speed * Gdx.graphics.getDeltaTime ();
			sprite.setPosition (sprite.getX (), sprite.getY () + (float) deltaY);
		}
		else if (Gdx.input.isKeyPressed (Input.Keys.S)){
			deltaY = -speed * Gdx.graphics.getDeltaTime ();
			sprite.setPosition (sprite.getX (), sprite.getY () + (float) deltaY);
		}
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			ObjectManager.getInstance ().messages.add (new CharacterChangeMessage (this));
			//ObjectManager.getInstance ().sendMessage (new DeleteMessage (this));
		}
		if (deltaX != 0 || deltaY != 0){
			ObjectManager.getInstance ().messages.add (new MoveMessage (this,
					sprite.getX () - deltaX, sprite.getY () - deltaY, sprite.getBoundingRectangle ()));
		}
	}
	
	
	public Character (boolean iSelected){
		this.iSelected = iSelected;
		Texture texture = new Texture("core\\assets\\player.png");
		sprite = new Sprite (texture);
		if (iSelected){
			sprite.setBounds (100, 100, texture.getWidth (), texture.getHeight ());
		}
		else{
			sprite.setBounds (500, 100, texture.getWidth (), texture.getHeight ());
		}
		dataForRender = new DataForRender (sprite, LayerType.character);
	}
	
	@Override
	public void update (){
		if (iSelected){
			inputControll ();
		}
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
		else if (message.type == MessageType.movement && message.object != this){
			MoveMessage msg = (MoveMessage) message;
			
			if (sprite.getBoundingRectangle ().contains ((float) msg.newX, (float) msg.newY)){
				ObjectManager.getInstance ().messages.add (new PushOutMessage (msg.object, msg.oldX, msg.oldY));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == this){
			PushOutMessage msg = (PushOutMessage) message;
			sprite.setPosition ((float) msg.whereX, (float) msg.whereY);
		}
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataForRender);
	}
}
