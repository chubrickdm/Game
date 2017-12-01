package com.game.addition.algorithms.aStar;

import java.util.ArrayList;

public interface Graph <E>{
	int cost (E current, E next);
	double heuristic (E begin, E end);
	ArrayList <E> neighbors (E current);
}
