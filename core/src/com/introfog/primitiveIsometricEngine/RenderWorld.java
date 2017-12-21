package com.introfog.primitiveIsometricEngine;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

import java.util.LinkedList;

public class RenderWorld{
	private ShapeRenderer shapeRenderer;
	private LinkedList <Rectangle> rectangles;
	
	
	private static class RenderHolder{
		private final static RenderWorld instance = new RenderWorld ();
	}
	
	private RenderWorld (){
		shapeRenderer = new ShapeRenderer ();
		rectangles = new LinkedList <> ();
	}
	
	
	public static RenderWorld getInstance (){
		return RenderHolder.instance;
	}
	
	
	public void renderScene (Matrix4 matrix){
		shapeRenderer.setProjectionMatrix (matrix);
		shapeRenderer.begin (ShapeRenderer.ShapeType.Line);
		for (Rectangle tmpR : rectangles){
			shapeRenderer.setColor (tmpR.color);
			shapeRenderer.rect (tmpR.getX (), tmpR.getY (), tmpR.getW (), tmpR.getH ());
		}
		shapeRenderer.end ();
		
		rectangles.clear ();
	}
	
	public void addRectangle (Rectangle rectangle){
		rectangles.add (rectangle);
	}
}