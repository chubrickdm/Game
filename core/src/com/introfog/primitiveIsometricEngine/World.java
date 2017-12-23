package com.introfog.primitiveIsometricEngine;

import com.badlogic.gdx.math.Matrix4;
import com.introfog.primitiveIsometricEngine.messages.WorldMessage;

import java.util.LinkedList;

public class World{
	private LinkedList <BodyPIE> objects;
	
	
	private static class ObjectManagerHolder{
		private final static World instance = new World ();
	}
	
	private World (){
		objects = new LinkedList <> ();
	}
	
	
	public static World getInstance (){
		return ObjectManagerHolder.instance;
	}
	
	public void addObject (BodyPIE bodyPIE){
		objects.add (bodyPIE);
	}
	
	public void addMessage (WorldMessage message){
		for (int i = objects.size () - 1; i > -1 && !objects.isEmpty (); i--){
			if (!objects.get (i).isGhost ()){
				objects.get (i).sendMessage (message);
			}
		}
	}
	
	public void drawBody (Matrix4 matrix){
		for (BodyPIE tmpB : objects){
			if (!tmpB.isGhost){
				tmpB.drawBody ();
			}
		}
		RenderWorld.getInstance ().renderScene (matrix);
	}
	
	public void clear (){
		objects.clear ();
	}
}