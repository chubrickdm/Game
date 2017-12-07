package com.game.mesh.objects.singletons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import com.game.GameSystem;
import com.game.MyGame;
import com.game.mesh.objects.GameObject;
import com.game.messages.GameMessage;

public class Chain extends GameObject{ //по центру экрана проходит линия, которая не дает смешиваться свету от фонариков персонажей
	private static class ChainHolder{
		private final static Chain instance = new Chain ();
	}
	
	private Chain (){
		ChainShape shape = new ChainShape ();
		shape.createChain (new Vector2[] {new Vector2 (GameSystem.SCREEN_W / 2, 0),
				new Vector2 (GameSystem.SCREEN_W / 2, 10_000)});
		BodyDef bodyDef = new BodyDef ();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		
		Body body = MyGame.getInstance ().world.createBody (bodyDef);
		
		FixtureDef fixtureDef = new FixtureDef ();
		fixtureDef.shape = shape;
		
		body.createFixture (fixtureDef);
	}
	
	
	public static Chain getInstance (){
		return ChainHolder.instance;
	}
	
	@Override
	public void sendMessage (GameMessage message){ }
}
