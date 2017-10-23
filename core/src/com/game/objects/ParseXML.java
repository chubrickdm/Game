package com.game.objects;

import com.game.GameSystem;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.game.objects.character.Character;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.util.ArrayList;

public abstract class ParseXML{
	private static boolean characterIsSelected = true;
	private static int levelW; //ширина уровня умноженная на аспект ратио
	private static int levelH; //высота уровня умноженная на аспект ратио
	private static int x;
	private static int y;
	private static int w;
	private static int h;
	
	
	private static void createWall (){
		Wall wall;
		y = (int) (levelH - y * GameObject.ASPECT_RATIO - h * GameObject.ASPECT_RATIO);
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
		y = (int) (levelH - y * GameObject.ASPECT_RATIO - w * GameObject.ASPECT_RATIO);
		if (characterIsSelected){
			character = new Character (true, x, y);
			characterIsSelected = false;
		}
		else{
			character = new Character (false, x, y);
		}
		ObjectManager.getInstance ().addObject (character);
	}
	
	
	public static void parseLVL (int level){
		float indent; //отсутп по оси Х
		String currObjectGroup;
		
		try{
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance ().newDocumentBuilder ();
			Document document = documentBuilder.parse ("core/assets/xml/levels/lvl" + String.valueOf (level) + ".tmx");
			
			Node map = document.getDocumentElement ();
			
			
			levelH = Integer.parseInt (map.getAttributes ().item (5).getTextContent ()); //tile height
			levelH *= Integer.parseInt (map.getAttributes ().item (0).getTextContent ()); //height
			levelH *= GameObject.ASPECT_RATIO;
			
			levelW = Integer.parseInt (map.getAttributes ().item (6).getTextContent ()); //tile width
			levelW *= Integer.parseInt (map.getAttributes ().item (8).getTextContent ()); //width
			levelW *= GameObject.ASPECT_RATIO;
			
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
							x = (int) (x * GameObject.ASPECT_RATIO + indent);
							y = Integer.parseInt (object.getAttributes ().item (4).getTextContent ());
							w = Integer.parseInt (object.getAttributes ().item (2).getTextContent ());
							h = Integer.parseInt (object.getAttributes ().item (0).getTextContent ());
							
							if (currObjectGroup.equals ("wall")){
								createWall ();
							}
							else if (currObjectGroup.equals ("characters")){
								createCharacter ();
							}
						}
					}
				}
			}
		}
		catch (SAXException | IOException | ParserConfigurationException ex){
			ex.printStackTrace (System.out);
		}
	}
}
