package com.game.mesh.objects.singletons.special;

import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.mesh.objects.GameObject;
import com.game.render.Render;

import java.util.LinkedList;

public class ObjectManager extends GameObject{
	private int iterator = 0;
	private LinkedList <GameMessage> messageForManager;
	private LinkedList <GameMessage> messages;
	private LinkedList <GameObject> objects;
	
	
	private void parseMessagesForManager (){
		if (messageForManager.size () != 0){
			for (GameMessage msg : messageForManager){
				if (msg.type == MessageType.deleteObject){
					objects.remove (msg.object);
				}
			}
		}
	}
	
	private static class ObjectManagerHolder{
		private final static ObjectManager instance = new ObjectManager ();
	}
	
	private ObjectManager (){
		iterator = 0;
		messageForManager = new LinkedList <GameMessage> ();
		messages = new LinkedList <GameMessage> ();
		objects = new LinkedList <GameObject> ();
	}
	
	
	public static ObjectManager getInstance (){
		return ObjectManagerHolder.instance;
	}
	
	@Override
	public void update (){
		parseMessagesForManager ();
		
		for (GameObject obj : objects){
			obj.update ();
		}
		for (iterator = 0; iterator < messages.size (); iterator++){
			GameMessage msg = messages.remove ();
			for (GameObject obj : objects){
				obj.sendMessage (msg);
			}
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		messageForManager.add (message);
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
		iterator = 0;
		messageForManager.clear ();
		messages.clear ();
		objects.clear ();
	}
	
	public void addMessage (GameMessage msg){
		messages.add (msg);
		iterator--;
	}
	
	public void addObject (GameObject obj){
		objects.add (obj);
	}
}