package com.game.objects.body;

import com.game.math.BodyRectangle;

public interface Body{
	void setBodyPosition (float x, float y);
	
	void move (float deltaX, float deltaY);
	
	boolean intersects (BodyRectangle bodyRectangle);
	
	float getBodyX ();
	
	float getBodyY ();
}
