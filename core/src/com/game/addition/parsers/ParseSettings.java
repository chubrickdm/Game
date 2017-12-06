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

import java.net.URLDecoder;

public class ParseSettings extends ParseBasis{
	private static String fromIDEA = "core/assets/xml/settings.xml";
	private static String fromDesktop = "/resource/xml/settings.xml";
	
	
	private static void saveChanges (Document document){
		try{
			Transformer transformer = TransformerFactory.newInstance ().newTransformer ();
			DOMSource source = new DOMSource (document);
			StreamResult result;
			
			URLDecoder decoder = new URLDecoder ();
			StringBuilder path = new StringBuilder (decoder.decode (ParseBasis.class.getProtectionDomain ().getCodeSource ().getLocation ().getPath ()));
			
			if (isFromIDEA){ //если не нашли, значит мы запускаем проект с IDEA
				result = new StreamResult (new File (fromIDEA));
			}
			else{ //если нашли, значит запускаем с .jar архива
				int index = path.lastIndexOf (GameSystem.NAME_JAR_ARCHIVE); //ищем в этом пути имя архива
				path.delete (index, path.length ()); //удаляем все начиная с имя архива
				path.append (fromDesktop);
				result = new StreamResult (new File (path.toString ()));
			}
			
			transformer.transform (source, result);
		}
		catch (TransformerException ex){
			ex.printStackTrace (System.out);
		}
	}
	
	
	public static void parseSettings (){
		String currField;
		Document document = getDocument (fromIDEA, fromDesktop);
		Node root = document.getDocumentElement ();
		
		NodeList fieldList = root.getChildNodes ();
		for (int i = 0; i < fieldList.getLength (); i++){
			Node field = fieldList.item (i);
			
			if (field.getNodeType () != Node.TEXT_NODE){
				currField = field.getAttributes ().item (0).getTextContent ();
				
				switch (currField){
				case "numLevels":
					GameSystem.NUM_LEVELS = Integer.parseInt (field.getTextContent ());
					break;
				case "isFirstGameStart":
					GameSystem.IS_FIRST_GAME_START = Boolean.parseBoolean (field.getTextContent ());
					break;
				case "numPassedLevels":
					GameSystem.NUM_PASSED_LEVELS = Integer.parseInt (field.getTextContent ());
					break;
				case "currentLevel":
					GameSystem.CURRENT_LEVEL = Integer.parseInt (field.getTextContent ());
					break;
				case "gameOver":
					GameSystem.GAME_OVER = Boolean.parseBoolean (field.getTextContent ());
					break;
				}
			}
		}
	}
	
	public static void writeSettings (){
		String currField;
		Document document = getDocument (fromIDEA, fromDesktop);
		Node root = document.getDocumentElement ();
		
		NodeList fieldList = root.getChildNodes ();
		for (int i = 0; i < fieldList.getLength (); i++){
			Node field = fieldList.item (i);
			
			if (field.getNodeType () != Node.TEXT_NODE){
				currField = field.getAttributes ().item (0).getTextContent ();
				
				switch (currField){
				case "numLevels":
					field.setTextContent (String.valueOf (GameSystem.NUM_LEVELS));
					break;
				case "isFirstGameStart":
					field.setTextContent (String.valueOf (GameSystem.IS_FIRST_GAME_START));
					break;
				case "numPassedLevels":
					field.setTextContent (String.valueOf (GameSystem.NUM_PASSED_LEVELS));
					break;
				case "currentLevel":
					field.setTextContent (String.valueOf (GameSystem.CURRENT_LEVEL));
					break;
				case "gameOver":
					field.setTextContent (String.valueOf (GameSystem.GAME_OVER));
					break;
				}
			}
		}
		
		saveChanges (document);
	}
}
