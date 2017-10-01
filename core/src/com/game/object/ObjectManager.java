package com.game.object;

import com.game.object.Messages.Message;
import java.util.List;
import java.util.Queue;

public class ObjectManager implements GameObject{
	private List <GameObject> objects;
	private Queue <Message> messages;
	
	
	@Override
	public void update (){
		for (GameObject obj : objects){
			obj.update ();
		}
		for (Message mes : messages){
			for (GameObject obj : objects){
				obj.sendMessage (mes);
			}
		}
	}
	
	@Override
	public void sendMessage (Message message){
	
	}
}
