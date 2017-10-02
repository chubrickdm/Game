package com.game.objects;

import com.game.objects.messages.GameMessage;

public interface GameObject{
	void update ();
	void sendMessage (GameMessage message);
}
