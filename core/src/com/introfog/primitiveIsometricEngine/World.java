package com.introfog.primitiveIsometricEngine;

import com.badlogic.gdx.math.Matrix4;
import com.introfog.primitiveIsometricEngine.messages.WorldMessage;

import java.util.LinkedList;

public class World{
	private LinkedList <Body> objects;
	
	
	private static class ObjectManagerHolder{
		private final static World instance = new World ();
	}
	
	private World (){
		objects = new LinkedList <> ();
	}
	
	
	public static World getInstance (){
		return ObjectManagerHolder.instance;
	}
	
	public void addObject (Body body){
		objects.add (body);
	}
	
	public void addMessage (WorldMessage message){
		for (int i = objects.size () - 1; i > -1 && !objects.isEmpty (); i--){
			objects.get (i).sendMessage (message);
		}
	}
	
	public void drawBody (Matrix4 matrix){
		for (Body tmpB : objects){
			tmpB.drawBody ();
		}
		RenderWorld.getInstance ().renderScene (matrix);
	}
	
	public void clear (){
		objects.clear ();
	}
}