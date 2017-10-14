package com.game.objects;

import com.game.messages.GameMessage;
import static java.awt.Toolkit.getDefaultToolkit;

public interface GameObject{
	float SCREEN_W = getDefaultToolkit ().getScreenSize ().width;
	float SCREEN_H = getDefaultToolkit ().getScreenSize ().height;
	//float SCREEN_W = 1100;
	//float SCREEN_H = 600;
	float ASPECT_RATIO = (float) ((SCREEN_W / 2 < SCREEN_H) ? SCREEN_W / 1366 : SCREEN_H / 768);
	
	
	void update ();
	void sendMessage (GameMessage message);
	void draw ();
}