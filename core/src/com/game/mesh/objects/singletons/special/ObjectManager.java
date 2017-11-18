package com.game.mesh.objects.singletons.special;

import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.mesh.objects.GameObject;
import com.game.render.Render;

import java.util.LinkedList;

public class ObjectManager extends GameObject{
	private int iterator = 0;
	private LinkedList <GameMessage> messagesForManager;
	private LinkedList <GameMessage> messages;
	private LinkedList <GameObject> objects;
	
	
	private void parseMessagesForManager (){
		//здесь обрабатываться сообщения для менеджера (пока что это только удаление объекта из списка).
		//Пришлось делать это т.к. если удалять объект из списка во время работы с ним, будет ошибка и экстренное
		//закрытие программы.
		for (GameMessage msg : messagesForManager){
			if (msg.type == MessageType.deleteObject){
				objects.remove (msg.object);
			}
		}
	}
	
	private static class ObjectManagerHolder{
		private final static ObjectManager instance = new ObjectManager ();
	}
	
	private ObjectManager (){
		iterator = 0;
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
		for (iterator = 0; iterator < messages.size (); iterator++){
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
		//сиглентонов (менеджер, камера).
		for (GameObject obj : objects){
			obj.clear ();
		}
		iterator = 0;
		messagesForManager.clear ();
		messages.clear ();
		objects.clear ();
	}
	
	public void addMessage (GameMessage msg){
		messages.add (msg);
		iterator--; //вот эта операция обязательна, т.к. мы извлекаем из списка сообщение когда рассылаем его по объектам
		//и при добавлении увеличиваем, получается что количество сообщений не изменилось, но итератор должен стать
		//на позицию раньше
	}
	
	public void addObject (GameObject obj){
		objects.add (obj);
	}
}