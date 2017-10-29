package com.game.addition.parsers;

import com.game.GameSystem;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseSettings extends ParseBasis{
	public static void parseSettings (){
		String currField;
		Document document = getDocument ("core/assets/xml/settings.xml", "/resourse/xml/settings.xml");
		
		
		Node root = document.getDocumentElement ();
		
		NodeList fieldList = root.getChildNodes ();
		for (int i = 0; i < fieldList.getLength (); i++){
			Node field = fieldList.item (i);
			
			if (field.getNodeType () != Node.TEXT_NODE){
				currField = field.getAttributes ().item (0).getTextContent ();
				
				if (currField.equals ("numLevels")){
					GameSystem.NUM_LEVELS = Integer.parseInt (field.getTextContent ());
				}
				else if (currField.equals ("isFirstGameStart")){
					GameSystem.IS_FIRST_GAME_START = Boolean.parseBoolean (field.getTextContent ());
				}
				else if (currField.equals ("numPassedLevels")){
					GameSystem.NUM_PASSED_LEVELS = Integer.parseInt (field.getTextContent ());
				}
				else if (currField.equals ("currentLevel")){
					GameSystem.CURRENT_LEVEL = Integer.parseInt (field.getTextContent ());
				}
			}
		}
	}
	
	public static void writeSettings (){
		String currField;
		Document document = getDocument ("core/assets/xml/settings.xml", "/resourse/xml/settings.xml");
		
		
		Node root = document.getDocumentElement ();
		
		NodeList fieldList = root.getChildNodes ();
		for (int i = 0; i < fieldList.getLength (); i++){
			Node field = fieldList.item (i);
			
			if (field.getNodeType () != Node.TEXT_NODE){
				currField = field.getAttributes ().item (0).getTextContent ();
				
				if (currField.equals ("numLevels")){
					field.setNodeValue (String.valueOf (GameSystem.NUM_LEVELS));
				}
				else if (currField.equals ("isFirstGameStart")){
					field.setNodeValue (String.valueOf (GameSystem.IS_FIRST_GAME_START));
				}
				else if (currField.equals ("numPassedLevels")){
					field.setNodeValue (String.valueOf (GameSystem.NUM_PASSED_LEVELS));
				}
				else if (currField.equals ("currentLevel")){
					field.setNodeValue (String.valueOf (GameSystem.CURRENT_LEVEL));
				}
			}
		}
	}
}
