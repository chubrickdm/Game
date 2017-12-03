package com.game.mesh.objects.singletons.special;

import com.game.mesh.objects.GameObject;
import com.game.messages.GameMessage;
import com.game.messages.MessageType;
import com.game.render.Render;

import java.util.LinkedList;

public class ObjectManager extends GameObject{
	private LinkedList <GameMessage> messages;
	private LinkedList <GameObject> objects;
	
	
	private static class ObjectManagerHolder{
		private final static ObjectManager instance = new ObjectManager ();
	}
	
	private ObjectManager (){
		messages = new LinkedList <GameMessage> ();
		objects = new LinkedList <GameObject> ();
	}
	
	
	public static ObjectManager getInstance (){
		return ObjectManagerHolder.instance;
	}
	
	@Override
	public void update (){
		//проверка на пустоту обязательно, т.к. может быть ситуация когда обновляется LevelManager и игрок нажимает на
		//на Escape и происходит очищение всех объектов в object, а i = object.size (), до удаления.
		for (int i = objects.size () - 1; i > -1 && !objects.isEmpty (); i--){
			objects.get (i).update ();
		}
		while (!messages.isEmpty ()){
			GameMessage msg = messages.remove ();
			
			for (int i = objects.size () - 1; i > -1 && !objects.isEmpty (); i--){
				objects.get (i).sendMessage (msg);
			}
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.deleteObject){
			objects.remove (message.object);
		}
		else if (message.type == MessageType.addObject){
			objects.add (message.object);
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
		//метод вызывается при закрытии уровня, или перехода к следующему, и служит для очистки перменных классов
		//сиглентонов (менеджер, камера) или удалить динамический свет.
		for (GameObject obj : objects){
			obj.clear ();
		}
		messages.clear ();
		objects.clear ();
	}
	
	public void addMessage (GameMessage msg){
		messages.add (msg);
	}
}