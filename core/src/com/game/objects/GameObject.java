package com.game.objects;

import com.game.messages.GameMessage;

public interface GameObject{
	void update ();
	void sendMessage (GameMessage message);
	void draw ();
}
