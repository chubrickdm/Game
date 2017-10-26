package com.game.objects;

import com.badlogic.gdx.Gdx;

import com.game.GameSystem;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.objects.camera.Camera;
import com.game.render.Render;
import com.game.objects.character.Character;

import java.util.LinkedList;


public class ObjectManager implements GameObject{
	private int iterator = 0;
	private LinkedList <GameObject> objects;
	private LinkedList <GameMessage> messages;
	
	
	private void initialize (){
		objects.add (ActionWheel.getInstance ()); //начальная позиция инициализируется в классе Character
		objects.add (Camera.getInstance ()); //инициализируются позиции персонажей в классе Character
		objects.add (LevelManager.getInstance ());
	}
	
	private static class ObjectManagerHolder{
		private final static ObjectManager instance = new ObjectManager ();
	}
	
	private ObjectManager (){
		iterator = 0;
		messages = new LinkedList <GameMessage> ();
		objects = new LinkedList <GameObject> ();
		initialize ();
	}
	
	
	public static ObjectManager getInstance (){
		return ObjectManagerHolder.instance;
	}
	
	@Override
	public void update (){
		for (GameObject obj : objects){
			obj.update ();
		}
		iterator = 0;
		for (; iterator < messages.size (); iterator++){
			GameMessage msg = messages.remove ();
			for (GameObject obj : objects){
				obj.sendMessage (msg);
			}
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.deleting){
			objects.remove (message.object);
		}
	}
	
	@Override
	public void draw (){
		for (GameObject obj : objects){
			obj.draw ();
		}
		Render.getInstance ().renderScene ();
	}
	
	public void addMessage (GameMessage msg){
		messages.add (msg);
		iterator--;
	}
	
	public void addObject (GameObject obj){
		objects.add (obj);
	}
}
