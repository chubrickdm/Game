package com.game.objects;

import com.game.messages.GameMessage;
import java.awt.Toolkit;

public interface GameObject{
	float ASPECT_RATIO = (float) Toolkit.getDefaultToolkit ().getScreenSize ().height / 768;
	//float ASPECT_RATIO = (float) 600 / 768;
	
	
	void update ();
	void sendMessage (GameMessage message);
	void draw ();
}