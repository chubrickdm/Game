package com.game.mesh.objects;

import com.game.messages.GameMessage;
import com.game.mesh.body.Body;
import com.game.render.DataRender;

import static com.game.GameSystem.SCREEN_H;
import static com.game.GameSystem.SCREEN_W;

public abstract class GameObject{
	public static final float ASPECT_RATIO = (float) ((SCREEN_W / 2 < SCREEN_H) ? SCREEN_W / 1366 : SCREEN_H / 768);
	public static final float UNIT = 64 * ASPECT_RATIO;
	
	public ObjectType objectType = ObjectType.unknown;
	
	protected DataRender dataRender;
	protected Body body;
	
	
	public void update (){ }
	abstract public void sendMessage (GameMessage message);
	public void draw (){ }
	public void clear (){ }
}