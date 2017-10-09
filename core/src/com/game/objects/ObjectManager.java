package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.game.InputController;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.objects.character.Character;
import com.game.render.Render;

import java.util.LinkedList;


public class ObjectManager implements GameObject{
	private int iterator;
	private LinkedList <GameObject> objects;
	private LinkedList <GameMessage> messages;
	
	
	private void initialize (){
		InputController inputProcessor = new InputController ();
		Gdx.input.setInputProcessor (inputProcessor);
		
		Character firstCharacter = new Character (true);
		Character secondCharacter = new Character (false);
		Wall wall = new Wall (false, 400, 100);
		Wall wall2 = new Wall (true, 400, 292);
		objects.add (wall);
		objects.add (wall2);
		objects.add (firstCharacter);
		objects.add (secondCharacter);
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
		Render.getInstance ().RenderScene ();
	}
	
	public void addMessage (GameMessage msg){
		messages.add (msg);
		iterator--;
	}
}
