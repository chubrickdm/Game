package com.game.mesh.objects.singletons.special;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import com.game.mesh.objects.*;
import com.game.mesh.objects.box.Box;
import com.game.mesh.objects.character.Character;
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
		
		Pools.set (Wall.class, new Pool <Wall> (200, 400){
			@Override
			protected Wall newObject (){
				return new Wall ();
			}
		});
		Pools.set (Box.class, new Pool <Box> (15, 30){
			@Override
			protected Box newObject (){
				return new Box (true);
			}
		});
		Pools.set (Mushrooms.class, new Pool <Mushrooms> (10, 20){
			@Override
			protected Mushrooms newObject (){
				return new Mushrooms ();
			}
		});
		Pools.set (Hole.class, new Pool <Hole> (15, 30){
			@Override
			protected Hole newObject (){
				return new Hole ();
			}
		});
		Pools.set (FinishLevel.class, new Pool <FinishLevel> (15, 30){
			@Override
			protected FinishLevel newObject (){
				return new FinishLevel ();
			}
		});
		Pools.set (InvisibleWall.class, new Pool <InvisibleWall> (15, 30){
			@Override
			protected InvisibleWall newObject (){
				return new InvisibleWall ();
			}
		});
		Pools.set (Inventory.class, new Pool <Inventory> (2, 2){
			@Override
			protected Inventory newObject (){
				return new Inventory ();
			}
		});
		Pools.set (Character.class, new Pool <Character> (2, 2){
			@Override
			protected Character newObject (){
				return new Character (true);
			}
		});
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
		//метод вызывается при закрытии уровня, или перехода к следующему, и служит для очистки перменных классов.
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