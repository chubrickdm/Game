package com.game.addition.algorithms.aStar.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;

public class AlgorithmAStar <E extends Node>{
	private HashMap <E, E> cameFrom;
	private HashMap <E, Integer> cost;
	
	
	private ArrayList <E> findPath (E start, E finish){
		ArrayList <E> path = new ArrayList <> ();
		E current = finish;
		while (true){
			path.add (current);
			
			if (start.equals (current)){
				Collections.reverse (path);
				return path;
			}
			
			current = cameFrom.get (current);
			if (current == null){
				return null;
			}
		}
	}
	
	
	public AlgorithmAStar (){
		cameFrom = new HashMap <> ();
		cost = new HashMap <> ();
	}
	
	public ArrayList <E> findWay (Graph <E> graph, E start, E finish){
		TreeSet <E> frontier = new TreeSet <> ((a, b) -> {
			if (a.priority == b.priority && a != b){
				return 1;
			}
			return a.priority - b.priority;
		});
		frontier.add (start);
		cost.put (start, 0);
		
		while (!frontier.isEmpty ()){
			E current = frontier.pollFirst ();
			
			if (finish.equals (current)){
				return findPath (start, current);
			}
			
			for (E next : graph.neighbors (current)){
				int newCost = cost.get (current) + graph.cost (current, next);
				if (!cost.containsKey (next) || newCost < cost.get (next)){
					cost.remove (next);
					cost.put (next, newCost);
					
					next.priority = newCost + (int) graph.heuristic (next, finish);
					frontier.add (next);
					cameFrom.put (next, current);
				}
			}
		}
		return null;
	}
}
