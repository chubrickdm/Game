package com.game.object;

import com.game.object.Messages.Message;
import com.game.object.Messages.MessageType;

import java.util.LinkedList;


public class ObjectManager{
	private static LinkedList <GameObject> objects;
	public static LinkedList <Message> messages = new LinkedList <Message> ();
	
	
	public ObjectManager (){
		objects = new LinkedList <GameObject> ();
		Player player = new Player ();
		objects.add (player);
	}
	
	public static void update (){
		for (GameObject obj : objects){
			obj.update ();
		}
		for (int i = 0; i < messages.size (); i++){
			for (GameObject obj : objects){
				obj.sendMessage (messages.remove ());
			}
		}
	}
	
	public static void sendMessage (Message message){
		if (message.type == MessageType.deleting){
			objects.remove (message.object);
		}
	}
}
