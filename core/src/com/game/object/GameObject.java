package com.game.object;

import com.game.object.Messages.Message;

public interface GameObject{
	void update ();
	void sendMessage (Message message);
}
