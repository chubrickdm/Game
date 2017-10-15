package com.game.objects;

import com.game.messages.GameMessage;
import static com.game.GameSystem.SCREEN_H;
import static com.game.GameSystem.SCREEN_W;


public interface GameObject{
	float ASPECT_RATIO = (float) ((SCREEN_W / 2 < SCREEN_H) ? SCREEN_W / 1366 : SCREEN_H / 768);
	
	
	void update ();
	void sendMessage (GameMessage message);
	void draw ();
}