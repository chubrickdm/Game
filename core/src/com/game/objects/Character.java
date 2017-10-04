package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.messages.*;
import com.game.render.DataForRender;
import com.game.render.LayerType;
import com.game.render.Render;

import java.awt.Rectangle;


public class Character implements GameObject{
	private boolean iSelected = false;
	private float deltaX;
	private float deltaY;
	private float speed = 100;
	private BodyObject body;
	private DataForRender dataForRender;
	
	
	private void inputControll (){
		deltaY = 0; deltaX = 0;
		if (Gdx.input.isKeyPressed (Input.Keys.D)){
			deltaX = speed * Gdx.graphics.getDeltaTime ();
		}
		else if (Gdx.input.isKeyPressed (Input.Keys.A)){
			deltaX = -speed * Gdx.graphics.getDeltaTime ();
		}
		if (Gdx.input.isKeyPressed (Input.Keys.W)){
			deltaY = speed * Gdx.graphics.getDeltaTime ();
		}
		else if (Gdx.input.isKeyPressed (Input.Keys.S)){
			deltaY = -speed * Gdx.graphics.getDeltaTime ();
		}
		if (deltaX != 0 || deltaY != 0){
			body.move (deltaX, deltaY);
			ObjectManager.getInstance ().messages.add (new MoveMessage (this,
					body.getX () - deltaX, body.getY () - deltaY, body));
		}
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.TAB)){
			ObjectManager.getInstance ().messages.add (new CharacterChangeMessage (this));
			//ObjectManager.getInstance ().sendMessage (new DeleteMessage (this));
		}
		
	}
	
	
	public Character (boolean iSelected){
		this.iSelected = iSelected;
		if (iSelected){
			body = new BodyObject ("core\\assets\\example2.png", Constants.START_FIRST_X,
					Constants.START_FIRST_Y, Constants.CHARACTER_W, Constants.CHARACTER_H);
		}
		else{
			body = new BodyObject ("core\\assets\\example2.png", Constants.START_SECOND_X,
					Constants.START_SECOND_Y, Constants.CHARACTER_W, Constants.CHARACTER_H);
		}
		dataForRender = new DataForRender (body.sprite, LayerType.character);
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
			if (body.intersects (msg.body)){
				ObjectManager.getInstance ().messages.add (new PushOutMessage (msg.object, msg.oldX, msg.oldY));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == this){
			PushOutMessage msg = (PushOutMessage) message;
			body.setPosition (msg.whereX, msg.whereY);
		}
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataForRender);
	}
}
