package com.game.addition.parsers;

import com.game.GameSystem;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class ParseSettings extends ParseBasis{
	private static void saveChanges (Document document){
		try{
			Transformer transformer = TransformerFactory.newInstance ().newTransformer ();
			DOMSource source = new DOMSource (document);
			StreamResult result;
			if (isFromIDEA){
				result = new StreamResult (new File (pathFromIDEA));
			}
			else{
				result = new StreamResult (new File (pathFromDesktop));
			}
			transformer.transform (source, result);
		}
		catch (TransformerException ex){
			ex.printStackTrace (System.out);
		}
	}
	
	
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
					field.setTextContent (String.valueOf (GameSystem.NUM_LEVELS));
				}
				else if (currField.equals ("isFirstGameStart")){
					System.out.println (field.getTextContent ());
					field.setTextContent (String.valueOf (GameSystem.IS_FIRST_GAME_START));
					System.out.println (field.getTextContent ());
				}
				else if (currField.equals ("numPassedLevels")){
					field.setTextContent (String.valueOf (GameSystem.NUM_PASSED_LEVELS));
				}
				else if (currField.equals ("currentLevel")){
					field.setTextContent (String.valueOf (GameSystem.CURRENT_LEVEL));
				}
			}
		}
		
		saveChanges (document);
	}
}
