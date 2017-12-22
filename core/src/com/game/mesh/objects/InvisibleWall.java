package com.game.mesh.objects;

import com.badlogic.gdx.graphics.Color;

import com.introfog.primitiveIsometricEngine.BodyPIE;

public class InvisibleWall extends GameObject{
	private BodyPIE bodyPIE;
	
	
	public InvisibleWall (){
		objectType = ObjectType.invisibleWall;
		bodyPIE = new BodyPIE (0, 0, 1, 1, Color.CORAL);
	}
	
	public void setBodyBounds (float x, float y, float w, float h){
		bodyPIE.setBounds (x, y, w, h);
	}
}