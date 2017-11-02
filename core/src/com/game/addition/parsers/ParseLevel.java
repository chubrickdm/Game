package com.game.addition.parsers;

import com.game.mesh.objects.FinishLevel;
import com.game.mesh.objects.InvisibleWall;
import com.game.GameSystem;
import com.game.mesh.objects.special.ObjectManager;
import com.game.mesh.objects.Wall;
import com.game.mesh.objects.character.Character;

import static com.game.mesh.objects.GameObject.ASPECT_RATIO;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class ParseLevel extends ParseBasis{
	private static boolean characterIsSelected = true;
	private static float x;
	private static float y;
	private static float w;
	private static float h;
	
	
	private static void createWall (){
		Wall wall;
		if (w > h){
			wall = new Wall (true, x, y);
		}
		else{
			wall = new Wall (false, x, y);
		}
		ObjectManager.getInstance ().addObject (wall);
	}
	
	private static void createCharacter (){
		Character character;
		if (characterIsSelected){
			character = new Character (x, y);
			characterIsSelected = false;
		}
		else{
			character = new Character (x, y);
		}
		ObjectManager.getInstance ().addObject (character);
	}
	
	private static void createInvisibleWall (){
		InvisibleWall invisibleWall;
		invisibleWall = new InvisibleWall (x, y, w, h);
		ObjectManager.getInstance ().addObject (invisibleWall);
	}
	
	private static void createLevelFinish (){
		FinishLevel.getInstance ().initialize (x, y, w, h);
		ObjectManager.getInstance ().addObject (FinishLevel.getInstance ());
	}
	
	
	public static void parseLVL (int level){
		characterIsSelected = true;
		int levelW; //ширина уровня умноженная на аспект ратио
		int levelH; //высота уровня умноженная на аспект ратио
		float indent; //отсутп по оси Х
		String currObjectGroup;
		Document document = getDocument ("core/assets/xml/levels/lvl" + String.valueOf (level) + ".tmx", "/resourse/xml/levels/lvl" + String.valueOf (level) + ".tmx");
		
		Node map = document.getDocumentElement ();
		
		
		levelH = Integer.parseInt (map.getAttributes ().item (5).getTextContent ()); //tile height
		levelH *= Integer.parseInt (map.getAttributes ().item (0).getTextContent ()); //height
		levelH *= ASPECT_RATIO;
		
		levelW = Integer.parseInt (map.getAttributes ().item (6).getTextContent ()); //tile width
		levelW *= Integer.parseInt (map.getAttributes ().item (8).getTextContent ()); //width
		levelW *= ASPECT_RATIO;
		
		indent = (GameSystem.SCREEN_W - levelW) / 2;
		
		
		NodeList objectGroups = map.getChildNodes ();
		for (int i = 0; i < objectGroups.getLength (); i++){
			Node objectGroup = objectGroups.item (i);
			
			if (objectGroup.getNodeType () != Node.TEXT_NODE){
				//запоминаем имя группы
				currObjectGroup = objectGroup.getAttributes ().item (0).getTextContent ();
				NodeList objects = objectGroup.getChildNodes ();
				for (int j = 0; j < objects.getLength (); j++){
					Node object = objects.item (j);
					
					if (object.getNodeType () != Node.TEXT_NODE){
						w = Float.parseFloat (object.getAttributes ().item (2).getTextContent ());
						w *= ASPECT_RATIO;
						h = Float.parseFloat (object.getAttributes ().item (0).getTextContent ());
						h *= ASPECT_RATIO;
						
						x = Float.parseFloat (object.getAttributes ().item (3).getTextContent ());
						x = x * ASPECT_RATIO + indent;
						y = Float.parseFloat (object.getAttributes ().item (4).getTextContent ());
						y = levelH - y * ASPECT_RATIO - h;
						
						
						switch (currObjectGroup){
						case "wall":
							createWall ();
							break;
						case "characters":
							createCharacter ();
							break;
						case "invisibleWall":
							createInvisibleWall ();
							break;
						case "finishLevel":
							createLevelFinish ();
							break;
						}
					}
				}
			}
		}
	}
}
