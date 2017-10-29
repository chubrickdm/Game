package com.game.addition.parsers;

import com.game.mesh.objects.ActionWheel;
import com.game.mesh.objects.InvisibleWall;
import com.game.mesh.objects.camera.Camera;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.game.GameSystem;
import com.game.mesh.objects.special.ObjectManager;
import com.game.mesh.objects.Wall;
import com.game.mesh.objects.character.Character;

import static com.game.mesh.objects.GameObject.ASPECT_RATIO;

public abstract class ParseLevel extends ParseBasis{
	private static boolean characterIsSelected = true;
	private static int levelW; //ширина уровня умноженная на аспект ратио
	private static int levelH; //высота уровня умноженная на аспект ратио
	private static int x;
	private static int y;
	private static int w;
	private static int h;
	
	
	private static void createWall (){
		Wall wall;
		y = (int) (levelH - y * ASPECT_RATIO - h * ASPECT_RATIO);
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
		y = (int) (levelH - y * ASPECT_RATIO - w * ASPECT_RATIO);
		if (characterIsSelected){
			character = new Character (true, x, y);
			
			//Обязательно надо установить позицию колеса, а то оно будет появляться не в том месте!
			ActionWheel.getInstance ().initializePosition (x, y);
			Camera.getInstance ().setFirstCharacterBodyPosition (x, y);
			
			characterIsSelected = false;
		}
		else{
			Camera.getInstance ().setSecondCharacterBodyPosition (x, y);
			character = new Character (false, x, y);
		}
		ObjectManager.getInstance ().addObject (character);
	}
	
	private static void createInvisibleWall (){
		InvisibleWall invisibleWall;
		y = (int) (levelH - y * ASPECT_RATIO - h * ASPECT_RATIO);
		if (w > h){
			invisibleWall = new InvisibleWall (true, x, y);
		}
		else{
			invisibleWall = new InvisibleWall (false, x, y);
		}
		ObjectManager.getInstance ().addObject (invisibleWall);
	}
	
	
	public static void parseLVL (int level){
		float indent; //отсутп по оси Х
		String currObjectGroup;
		Document document = getDocument ("core/assets/xml/levels/lvl" + String.valueOf (level) + ".tmx",
				"/resourse/xml/levels/lvl" + String.valueOf (level) + ".tmx");
		
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
						x = Integer.parseInt (object.getAttributes ().item (3).getTextContent ());
						x = (int) (x * ASPECT_RATIO + indent);
						y = Integer.parseInt (object.getAttributes ().item (4).getTextContent ());
						w = Integer.parseInt (object.getAttributes ().item (2).getTextContent ());
						h = Integer.parseInt (object.getAttributes ().item (0).getTextContent ());
						
						if (currObjectGroup.equals ("wall")){
							createWall ();
						}
						else if (currObjectGroup.equals ("characters")){
							createCharacter ();
						}
						else if (currObjectGroup.equals ("invisibleWall")){
							createInvisibleWall ();
						}
					}
				}
			}
		}
	}
}