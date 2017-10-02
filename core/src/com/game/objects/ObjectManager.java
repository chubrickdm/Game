package com.game.objects;

import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.render.Render;

import java.util.LinkedList;


public class ObjectManager implements GameObject{
	private LinkedList <GameObject> objects;
	public LinkedList <GameMessage> messages;
	
	
	private void initialize (){
		Character firstCharacter = new Character (true);
		Character secondCharacter = new Character (false);
		objects.add (firstCharacter);
		objects.add (secondCharacter);
	}
	
	private static class ObjectManagerHolder{
		private final static ObjectManager instance = new ObjectManager ();
	}
	
	private ObjectManager (){
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
		for (int i = 0; i < messages.size (); i++){
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
		Render.getInstance ().RenderScene ();
	}
}
