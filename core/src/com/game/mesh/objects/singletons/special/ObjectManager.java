package com.game.mesh.objects.singletons.special;

import com.game.mesh.objects.GameObject;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.render.Render;

import java.util.LinkedList;

public class ObjectManager extends GameObject{
	private LinkedList <GameMessage> messagesForManager;
	private LinkedList <GameMessage> messages;
	private LinkedList <GameObject> objects;
	
	
	private void parseMessagesForManager (){
		//здесь обрабатываться сообщения для менеджера. Пришлось делать это т.к. если удалять объект из списка во время
		//работы с ним, будет ошибка и экстренное закрытие программы.
		while (!messagesForManager.isEmpty ()){
			GameMessage msg = messagesForManager.remove ();
			if (msg.type == MessageType.deleteObject){
				objects.remove (msg.object);
			}
			else if (msg.type == MessageType.addObject){
				objects.add (msg.object);
			}
		}
	}
	
	private static class ObjectManagerHolder{
		private final static ObjectManager instance = new ObjectManager ();
	}
	
	private ObjectManager (){
		messagesForManager = new LinkedList <GameMessage> ();
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
		while (!messages.isEmpty ()){
			GameMessage msg = messages.remove ();
			for (GameObject obj : objects){
				obj.sendMessage (msg);
			}
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		messagesForManager.add (message);
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
		//метод вызывается при закрытии уровня, или перехода к следующему, и служит для очистки перменных классов
		//сиглентонов (менеджер, камера) или удалить динамический свет.
		for (GameObject obj : objects){
			obj.clear ();
		}
		messagesForManager.clear ();
		messages.clear ();
		objects.clear ();
	}
	
	public void addMessage (GameMessage msg){
		messages.add (msg);
	}
	
	public void addObject (GameObject obj){
		objects.add (obj);
	}
}