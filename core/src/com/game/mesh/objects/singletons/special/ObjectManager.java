package com.game.mesh.objects.singletons.special;

import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.mesh.objects.GameObject;
import com.game.render.Render;

import java.util.LinkedList;

public class ObjectManager extends GameObject{
	private int iterator = 0;
	private LinkedList <GameMessage> messages;
	private LinkedList <GameObject> objects;
	
	
	private static class ObjectManagerHolder{
		private final static ObjectManager instance = new ObjectManager ();
	}
	
	private ObjectManager (){
		iterator = 0;
		messages = new LinkedList <GameMessage> ();
		objects = new LinkedList <GameObject> ();
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
		if (message.type == MessageType.deleteObject){
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
	
	@Override
	public void clear (){
		for (GameObject obj : objects){
			obj.clear ();
		}
		messages.clear ();
		objects.clear ();
		iterator = 0;
	}
	
	public void addMessage (GameMessage msg){
		messages.add (msg);
		iterator--;
	}
	
	public void addObject (GameObject obj){
		objects.add (obj);
	}
}
