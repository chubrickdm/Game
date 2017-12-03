package com.game.addition.parsers;

import com.game.GameSystem;
import com.game.mesh.objects.*;
import com.game.mesh.objects.character.Character;
import com.game.mesh.objects.singletons.special.LevelManager;
import com.game.mesh.objects.singletons.special.ObjectManager;

import com.game.messages.AddObjectMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static com.game.mesh.objects.GameObject.ASPECT_RATIO;

public abstract class ParseLevel extends ParseBasis{
	private static float x;
	private static float y;
	private static float w;
	private static float h;
	
	private static int levelW; //ширина уровня умноженная на аспект ратио
	private static int levelH; //высота уровня умноженная на аспект ратио
	
	
	private static void additionalCalculates (Node map){
		levelH = Integer.parseInt (map.getAttributes ().item (5).getTextContent ()); //tile height
		int height = Integer.parseInt (map.getAttributes ().item (0).getTextContent ());
		levelH *= height;
		levelH *= ASPECT_RATIO;
		
		levelW = Integer.parseInt (map.getAttributes ().item (6).getTextContent ()); //tile width
		int width = Integer.parseInt (map.getAttributes ().item (8).getTextContent ());
		levelW *= width;
		levelW *= ASPECT_RATIO;
		
		GameSystem.INDENT_BETWEEN_SCREEN_LEVEL = (GameSystem.SCREEN_W - levelW) / 2;
		
		LevelManager.getInstance ().level.setSize (width, height);
	}
	
	private static void parseCoordinates (Node object){
		w = Float.parseFloat (object.getAttributes ().item (2).getTextContent ());
		w *= ASPECT_RATIO;
		h = Float.parseFloat (object.getAttributes ().item (0).getTextContent ());
		h *= ASPECT_RATIO;
		
		x = Float.parseFloat (object.getAttributes ().item (3).getTextContent ());
		x = x * ASPECT_RATIO + GameSystem.INDENT_BETWEEN_SCREEN_LEVEL;
		y = Float.parseFloat (object.getAttributes ().item (4).getTextContent ());
		y = levelH - y * ASPECT_RATIO - h;
	}
	
	private static void createObject (String currObjectGroup){
		switch (currObjectGroup){
		case "wall":
			Wall wall = new Wall (x, y);
			ObjectManager.getInstance ().sendMessage (new AddObjectMessage (wall));
			LevelManager.getInstance ().level.addWall (x, y);
			break;
		case "characters":
			Character character = new Character (x, y);
			ObjectManager.getInstance ().sendMessage (new AddObjectMessage (character));
			break;
		case "invisibleWall":
			InvisibleWall invisibleWall = new InvisibleWall (x, y, w, h);
			ObjectManager.getInstance ().sendMessage (new AddObjectMessage (invisibleWall));
			LevelManager.getInstance ().level.addInvisibleWall (x, y, w, h);
			break;
		case "finishLevel":
			FinishLevel finish = new FinishLevel (x, y, w, h);
			ObjectManager.getInstance ().sendMessage (new AddObjectMessage (finish));
			break;
		case "box":
			Box box = new Box (x, y);
			ObjectManager.getInstance ().sendMessage (new AddObjectMessage (box));
			break;
		case "hole":
			Hole hole = new Hole (x, y);
			ObjectManager.getInstance ().sendMessage (new AddObjectMessage (hole));
			break;
		case "mushrooms":
			Mushrooms mushrooms = new Mushrooms (x, y);
			ObjectManager.getInstance ().sendMessage (new AddObjectMessage (mushrooms));
			break;
		}
	}
	
	
	public static void parseLVL (int level){
		String currObjectGroup;
		Document document = getDocument ("core/assets/xml/levels/lvl" + String.valueOf (level) + ".tmx",
				"/resource/xml/levels/lvl" + String.valueOf (level) + ".tmx");
		Node map = document.getDocumentElement ();
		additionalCalculates (map);
		
		NodeList objectGroups = map.getChildNodes ();
		for (int i = 0; i < objectGroups.getLength (); i++){
			Node objectGroup = objectGroups.item (i);
			
			if (objectGroup.getNodeType () != Node.TEXT_NODE){
				currObjectGroup = objectGroup.getAttributes ().item (0).getTextContent (); //запоминаем имя группы
				
				NodeList objects = objectGroup.getChildNodes ();
				for (int j = 0; j < objects.getLength (); j++){
					Node object = objects.item (j);
					
					if (object.getNodeType () != Node.TEXT_NODE){
						parseCoordinates (object);
						
						createObject (currObjectGroup);
					}
				}
			}
		}
	}
}
